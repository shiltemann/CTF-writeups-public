# December 15th: Execute the inevitable

**URL**

[http://hackvent.hacking-lab.com/challenge.php?day=15](http://hackvent.hacking-lab.com/challenge.php?day=15)


**Challenge**


```
QzgwODAwMDBDNjQ1Rjg3N0IwMTMzNDdFODg0NUY5NkE2MTU5OEQ0NUY4ODNDMDAyODgwOEVCMDEzRjJCQzA2QTM5OEIwN
DI0ODNDNDA0MDNDMDQwODNFMTAxODNDOTAxOEQ3REZCRjNBQTgzRTAwMTgzQzg1NTgzRjAzMzhENERGQzUxNUE4ODAyRU
IwMTc1RTgwMDAwMDAwMDVCOEE1QkZBOEFDMzg2Qzg4ODRERkQ5MENDMzNDMDA1RDEwMDAwMDA4M0U4NjM1MDgzQzQwNDh
BNUMyNEZDODg1REZFRkU0NUY4QzlDMw==
```


**Solution**

We base64 decode the string

```
C8080000C645F877B013347E8845F96A61598D45F883C0028808EB013F2BC06A398B042483C40403C04083
E10183C9018D7DFBF3AA83E00183C85583F0338D4DFC515A8802EB0175E8000000005B8A5BFA8AC386C888
4DFD90CC33C005D100000083E8635083C4048A5C24FC885DFEFE45F8C9C3
```


Given the title we suspect that this is machine code. We can disassemble this:

```
.data:0x00000000	c8080000        enter 0x8,0x0
.data:0x00000004	c645f877        mov BYTE PTR [ebp-0x8],0x77     ; w  --> later changed to x
.data:0x00000008	b013            mov al,0x13
.data:0x0000000a	347e            xor al,0x7e			
.data:0x0000000c	8845f9          mov BYTE PTR [ebp-0x7],al       ; m  
.data:0x0000000f	6a61            push 0x61			
.data:0x00000011	59              pop ecx
.data:0x00000012	8d45f8          lea eax,[ebp-0x8]
.data:0x00000015	83c002          add eax,0x2
.data:0x00000018	8808            mov BYTE PTR [eax],cl           ; a  
.data:0x0000001a	eb01            jmp loc_0000001d
.data:0x0000001c	3f              aas
.data:0x0000001d		
.data:0x0000001d                        loc_0000001d:
.data:0x0000001d	2bc0            sub eax,eax
.data:0x0000001f	6a39            push 0x39
.data:0x00000021	8b0424          mov eax,DWORD PTR [esp]
.data:0x00000024	83c404          add esp,0x4
.data:0x00000027	03c0            add eax,eax
.data:0x00000029	40              inc eax
.data:0x0000002a	83e101          and ecx,0x1
.data:0x0000002d	83c901          or ecx,0x1                      ; s  
.data:0x00000030	8d7dfb          lea edi,[ebp-0x5]
.data:0x00000033	f3aa            rep stos BYTE PTR es:[edi],al
.data:0x00000035	83e001          and eax,0x1
.data:0x00000038	83c855          or eax,0x55
.data:0x0000003b	83f033          xor eax,0x33
.data:0x0000003e	8d4dfc          lea ecx,[ebp-0x4]               ; f  
.data:0x00000041	51              push ecx
.data:0x00000042	5a              pop edx
.data:0x00000043	8802            mov BYTE PTR [edx],al
.data:0x00000045	eb01            jmp 0x00000048
.data:0x00000047	75e8            jne 0x00000031
.data:0x00000049	0000            add BYTE PTR [eax],al
.data:0x0000004b	0000            add BYTE PTR [eax],al
.data:0x0000004d	5b              pop ebx
.data:0x0000004e	8a5bfa          mov bl,BYTE PTR [ebx-0x6]
.data:0x00000051	8ac3            mov al,bl
.data:0x00000053	86c8            xchg al,cl
.data:0x00000055	884dfd          mov BYTE PTR [ebp-0x3],cl       ; u  
.data:0x00000058	90              nop
.data:0x00000059	cc              int3
.data:0x0000005a	33c0            xor eax,eax
.data:0x0000005c	05d1000000      add eax,0xd1
.data:0x00000061	83e863          sub eax,0x63                    ; n 
.data:0x00000064	50              push eax
.data:0x00000065	83c404          add esp,0x4
.data:0x00000068	8a5c24fc        mov bl,BYTE PTR [esp-0x4]
.data:0x0000006c	885dfe          mov BYTE PTR [ebp-0x2],bl		
.data:0x0000006f	fe45f8          inc BYTE PTR [ebp-0x8]          ; increment first value (w becomes x)
.data:0x00000072	c9              leave
.data:0x00000073	c3              ret
```
  
Since it is quite a short program, we evaluate it by hand (see comments above), we see that a string is written to memory:

```
xmasfun
```

We put this in the ball-o-matic to get today's bauble:

![](images/Tytjey8qkSzgol3MY9rz.png)

**Flag**

```
HV14-SmnR-LyBL-BQRn-8Yjg-LPLy
```

