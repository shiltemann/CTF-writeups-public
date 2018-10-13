# How the stack works

When entering/exiting a function.

You can use

```
set disassembly-flavor [intel|att]
```

to switch disassembly formats. (Or to include that in your `~/.gdbinit` to set permanently)

## C Code

```
int blah(int a, int b, int c){
	int d = a * b * c;
	return d;
}


int main(){
	blah(3, 4, 5);
	return 0;
}
```

This can be compiled to x32 with:

```
gcc -m32 -pedantic-errors -std=c89 -Wall tmp.c
```

you may need:

```
sudo apt install gcc-multilib lib32gcc-5-dev
```

## Disassembly

```
(gdb) disas main        # Intel Syntax             # ATT Syntax
Dump of assembler code for function main:
   0x080483f4 <+0>:     push   ebp                 push   %ebp
   0x080483f5 <+1>:     mov    ebp,esp             mov    %esp,%ebp
   0x080483f7 <+3>:     push   0x5                 push   $0x5
   0x080483f9 <+5>:     push   0x4                 push   $0x4
   0x080483fb <+7>:     push   0x3                 push   $0x3
   0x080483fd <+9>:     call   0x80483db <blah>    call   0x80483db <blah>
   0x08048402 <+14>:    add    esp,0xc             add    $0xc,%esp
   0x08048405 <+17>:    mov    eax,0x0             mov    $0x0,%eax
   0x0804840a <+22>:    leave                      leave
   0x0804840b <+23>:    ret                        ret

(gdb) disas blah        # Intel Syntax                   # ATT Syntax
Dump of assembler code for function blah:
   0x080483db <+0>:     push   ebp                        push   %ebp
   0x080483dc <+1>:     mov    ebp,esp                    mov    %esp,%ebp
   0x080483de <+3>:     sub    esp,0x10                   sub    $0x10,%esp
   0x080483e1 <+6>:     mov    eax,DWORD PTR [ebp+0x8]    mov    0x8(%ebp),%eax
   0x080483e4 <+9>:     imul   eax,DWORD PTR [ebp+0xc]    imul   0xc(%ebp),%eax
   0x080483e8 <+13>:    imul   eax,DWORD PTR [ebp+0x10]   imul   0x10(%ebp),%eax
   0x080483ec <+17>:    mov    DWORD PTR [ebp-0x4],eax    mov    %eax,-0x4(%ebp)
   0x080483ef <+20>:    mov    eax,DWORD PTR [ebp-0x4]    mov    -0x4(%ebp),%eax
   0x080483f2 <+23>:    leave                             leave
   0x080483f3 <+24>:    ret                               ret
```

## Step -1 - Before entering

In the disassembly we push numbers before calling, in reverse order.

```
push 0x5
push 0x4
push 0x3
```

So our stack pointer should have decreased by 12 before/after. Stepping, it does.

```
(gdb) p/x (int)$esp
$161 = 0xffffcf8c
(gdb) p/x *(int)$esp
$162 = 0x3
```

## Step 0 - Entering function

So we enter the function

Our registers are:

```
eax            0xf7fa2dbc       -134599236
ecx            0xd86ebd1        226946001
edx            0xffffcfc4       -12348
ebx            0x0      0

esp            0xffffcf88       0xffffcf88
ebp            0xffffcf98       0xffffcf98
esi            0xf7fa1000       -134606848
edi            0xf7fa1000       -134606848
eip            0x80483db        0x80483db <blah>
```

esp now should point to return address:

```
(gdb) p/x (int)$esp
$163 = 0xffffcf88
(gdb) p/x *(int)$esp
$164 = 0x08048402
```

Which is exactly after` call` in the disassembly.

And our stack should look like

addr        | addr         | contents    | use
----------- | ------------ | ----------- | -----
`ebp`       | `0xffffcf98` | `0`         | arg1? argv count?
`esp + 0xc` | `0xffffcf94` | `5`         | arg 3
`esp + 0x8` | `0xffffcf90` | `4`         | arg 2
`esp + 0x4` | `0xffffcf8c` | `3`         | arg 1
`esp`       | `0xffffcf88` | `0x8048402` | esp

`ebp` is still set to some old value (So accessing args from there will be ebp - 0x4/8/c)

## Step 1

```
push ebp
```

esp automatically decreases by 4.

Stack becomes:

addr         | addr         | contents     | use
---------    | -----        | -----        | -----
`ebp`        | `0xffffcf98` | `0`          |
`esp + 0x10` | `0xffffcf94` | `5`          | arg 3
`esp + 0xc`  | `0xffffcf90` | `4`          | arg 2
`esp + 0x8`  | `0xffffcf8c` | `3`          | arg 1
`esp + 0x4`  | `0xffffcf88` | `0x08048402` | return
`esp`        | `0xffffcf84` | `0xffffcf98` | previous ebp

ebp is still unchanged, esp is down 4.

## Step 2

```
mov ebp, esp
```

This should set ebp to value of esp


addr         | addr         | contents     | use
------------ | ------------ | ------------ | -----
`ebp + 0x14` | `0xffffcf98` | `0`          |
`ebp + 0x10` | `0xffffcf94` | `5`          | arg 3
`ebp + 0xc`  | `0xffffcf90` | `4`          | arg 2
`ebp + 0x8`  | `0xffffcf8c` | `3`          | arg 1
`ebp + 0x4`  | `0xffffcf88` | `0x08048402` | return
`ebp = esp`  | `0xffffcf84` | `0xffffcf98` | previous ebp; esp


## Step 3

```
sub esp,0x10
```

Pushes stack pointer down 16 bytes.


addr          | addr         | contents     | use
------------- | ------------ | ------------ | -----
`ebp + 0x14`  | `0xffffcf98` | `0`          |
`ebp + 0x10`  | `0xffffcf94` | `5`          | arg 3
`ebp + 0xc`   | `0xffffcf90` | `4`          | arg 2
`ebp + 0x8`   | `0xffffcf8c` | `3`          | arg 1
`ebp + 0x4`   | `0xffffcf88` | `0x08048402` | return
`ebp`         | `0xffffcf84` | `0xffffcf98` |
`ebp - 0x4`   | `0xffffcf80` | garbage?     |
`ebp - 0x8`   | `0xffffcf7c` | garbage?     |
`ebp - 0xc`   | `0xffffcf78` | garbage?     |
`ebp - 0x10`  | `0xffffcf74` | garbage?     | esp

If we want to see multiple values we can do something like:

```
(gdb) x/3d ((int)$ebp + 0x8)
0xffffcf8c:     3       4       5
```

which prints next three values up, as decimals. Or we can view them as integers:


```
(gdb) x/3t ((int)$ebp + 0x8)
0xffffcf8c:     00000000000000000000000000000011        00000000000000000000000000000100        00000000000000000000000000000101
```

The formats available are:

specifier | type
--------- | -----
`o`       | octal
`x`       | hex
`d`       | decimal
`u`       | unsigned decimal
`t`       | binary
`f`       | float
`a`       | address
`i`       | instruction
`c`       | char
`s`       | string
`T`       | OStype

If you accidentally use `i` expecting `integer`, instead it will treat those as
instructions that appear nowhere in the disassembly and can be a bit confusing.

```
(gdb) x/3i ((int)$ebp + 0x8)
0xffffcf8c:  add    eax,DWORD PTR [eax]
0xffffcf8e:  add    BYTE PTR [eax],al
0xffffcf90:  add    al,0x0
```
