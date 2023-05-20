---
layout: writeup
title: 'Dec 14: Happy Cryptmas'
level:
difficulty:
points:
categories: []
tags: []
flag: HV17-5BMu-mgD0-G7Su-EYsp-Mg0b

---

## Challenge

todays gift was encrypted with the attached program. try to unbox your
xmas present.

    Flag: 7A9FDCA5BB061D0D638BE1442586F3488B536399BA05A14FCAE3F0A2E5F268F2F3142D1956769497AE677A12E4D44EC727E255B391005B9ADCF53B4A74FFC34C

[Executable](writeupfiles/hackvent)

## Solution

This is a Mach-O executable which we disassemble the file with Hopper:

    ================ B E G I N N I N G   O F   P R O C E D U R E ================



                                           ;
                                           ; Section __text
                                           ;
                                           ; Range 0x100000cc0 - 0x100000e66 (422 bytes)
                                           ; File offset 3264 (422 bytes)
                                           ; Flags : 0x80000400
                                           ;
    0000000100000cc1         mov        rbp, rsp
    0000000100000cc4         sub        rsp, 0xc0
    0000000100000ccb         mov        rax, qword [ds:imp___got____stack_chk_guard] ; imp___got____stack_chk_guard
    0000000100000cd2         mov        rax, qword [ds:rax]
    0000000100000cd5         mov        qword [ss:rbp+var_8], rax
    0000000100000cd9         mov        dword [ss:rbp+var_64], 0x0
    0000000100000ce0         mov        dword [ss:rbp+var_68], edi
    0000000100000ce3         mov        qword [ss:rbp+var_70], rsi
    0000000100000ce7         cmp        dword [ss:rbp+var_68], 0x1
    0000000100000ceb         jne        0x100000cfd

    0000000100000cf1         mov        dword [ss:rbp+var_64], 0x0
    0000000100000cf8         jmp        0x100000e32

    0000000100000cfd         lea        rdi, qword [ss:rbp+var_50]                  ; XREF=_main+43
    0000000100000d01         call       imp___stubs____gmpz_init
    0000000100000d06         lea        rdi, qword [ss:rbp+var_60]
    0000000100000d0a         call       imp___stubs____gmpz_init
    0000000100000d0f         lea        rsi, qword [ds:0x100000f18]                 ; "F66EB887F2B8A620FD03C7D0633791CB4804739CE7FE001C81E6E02783737CA21DB2A0D8AF2D10B200006D10737A0872C667AD142F90407132EFABF8E5D6BD51"
    0000000100000d16         mov        edx, 0x10
    0000000100000d1b         lea        rdi, qword [ss:rbp+var_20]
    0000000100000d1f         call       imp___stubs____gmpz_init_set_str
    0000000100000d24         lea        rsi, qword [ds:0x100000f99]                 ; "65537"
    0000000100000d2b         mov        edx, 0xa
    0000000100000d30         lea        rdi, qword [ss:rbp+var_40]
    0000000100000d34         mov        dword [ss:rbp+var_74], eax
    0000000100000d37         call       imp___stubs____gmpz_init_set_str
    0000000100000d3c         mov        edx, 0x1
    0000000100000d41         mov        ecx, 0x1
    0000000100000d46         xor        r8d, r8d
    0000000100000d49         xor        r9d, r9d
    0000000100000d4c         lea        rdi, qword [ss:rbp+var_50]
    0000000100000d50         mov        rsi, qword [ss:rbp+var_70]
    0000000100000d54         mov        rsi, qword [ds:rsi+0x8]
    0000000100000d58         mov        qword [ss:rbp+var_80], rdi
    0000000100000d5c         mov        rdi, rsi                                    ; argument "s" for method imp___stubs__strlen
    0000000100000d5f         mov        dword [ss:rbp+var_84], eax
    0000000100000d65         mov        dword [ss:rbp+var_88], edx
    0000000100000d6b         mov        qword [ss:rbp+var_90], rcx
    0000000100000d72         mov        dword [ss:rbp+var_94], r8d
    0000000100000d79         mov        qword [ss:rbp+var_A0], r9
    0000000100000d80         call       imp___stubs__strlen
    0000000100000d85         mov        rcx, qword [ss:rbp+var_70]
    0000000100000d89         mov        rcx, qword [ds:rcx+0x8]
    0000000100000d8d         mov        rdi, qword [ss:rbp+var_80]
    0000000100000d91         mov        rsi, rax
    0000000100000d94         mov        edx, dword [ss:rbp+var_88]
    0000000100000d9a         mov        rax, qword [ss:rbp+var_90]
    0000000100000da1         mov        qword [ss:rbp+var_A8], rcx
    0000000100000da8         mov        rcx, rax
    0000000100000dab         mov        r8d, dword [ss:rbp+var_94]
    0000000100000db2         mov        r9, qword [ss:rbp+var_A0]
    0000000100000db9         mov        r10, qword [ss:rbp+var_A8]
    0000000100000dc0         mov        qword [ss:rsp], r10
    0000000100000dc4         call       imp___stubs____gmpz_import
    0000000100000dc9         lea        rsi, qword [ss:rbp+var_20]
    0000000100000dcd         lea        rdi, qword [ss:rbp+var_50]
    0000000100000dd1         call       imp___stubs____gmpz_cmp
    0000000100000dd6         cmp        eax, 0x0
    0000000100000dd9         jle        0x100000de4

    0000000100000ddf         call       imp___stubs__abort

    0000000100000de4         lea        rcx, qword [ss:rbp+var_20]                  ; XREF=_main+281
    0000000100000de8         lea        rdx, qword [ss:rbp+var_40]
    0000000100000dec         lea        rsi, qword [ss:rbp+var_50]
    0000000100000df0         lea        rdi, qword [ss:rbp+var_60]
    0000000100000df4         call       imp___stubs____gmpz_powm
    0000000100000df9         lea        rdi, qword [ds:0x100000f9f]                 ; "Crypted: %ZX\\n"
    0000000100000e00         lea        rsi, qword [ss:rbp+var_60]
    0000000100000e04         mov        al, 0x0
    0000000100000e06         call       imp___stubs____gmp_printf
    0000000100000e0b         xor        r8d, r8d
    0000000100000e0e         lea        rcx, qword [ss:rbp+var_40]
    0000000100000e12         lea        rdx, qword [ss:rbp+var_20]
    0000000100000e16         lea        rsi, qword [ss:rbp+var_60]
    0000000100000e1a         lea        rdi, qword [ss:rbp+var_50]
    0000000100000e1e         mov        dword [ss:rbp+var_AC], eax
    0000000100000e24         mov        al, 0x0
    0000000100000e26         call       imp___stubs____gmpz_clears
    0000000100000e2b         mov        dword [ss:rbp+var_64], 0x0

    0000000100000e32         mov        eax, dword [ss:rbp+var_64]                  ; XREF=_main+56
    0000000100000e35         mov        rcx, qword [ds:imp___got____stack_chk_guard] ; imp___got____stack_chk_guard
    0000000100000e3c         mov        rcx, qword [ds:rcx]
    0000000100000e3f         mov        rdx, qword [ss:rbp+var_8]
    0000000100000e43         cmp        rcx, rdx
    0000000100000e46         mov        dword [ss:rbp+var_B0], eax
    0000000100000e4c         jne        0x100000e61

    0000000100000e52         mov        eax, dword [ss:rbp+var_B0]
    0000000100000e58         add        rsp, 0xc0
    0000000100000e5f         pop        rbp
    0000000100000e60         ret

    0000000100000e61         call       imp___stubs____stack_chk_fail               ; XREF=_main+396
                            ; endp

pseudocode generated by Hopper disassembler:

    function _main {
        var_8 = **__stack_chk_guard;
        var_64 = 0x0;
        var_68 = LODWORD(arg0);
        var_70 = arg1;
        if (var_68 != 0x1) goto loc_100000cfd;
        goto loc_100000cf1;

    loc_100000cfd:
        __gmpz_init();
        __gmpz_init();
        rax = __gmpz_init_set_str();
        var_74 = LODWORD(rax);
        rax = __gmpz_init_set_str();
        rsi = *(var_70 + 0x8);
        var_80 = var_50;
        var_84 = LODWORD(rax);
        var_88 = LODWORD(0x1);
        var_90 = 0x1;
        var_94 = LODWORD(0x0);
        var_A0 = 0x0;
        rax = strlen(rsi);
        rcx = *(var_70 + 0x8);
        var_A8 = rcx;
        __gmpz_import();
        if (LODWORD(__gmpz_cmp()) <= 0x0) goto loc_100000de4;
        goto loc_100000ddf;

    loc_100000de4:
        __gmpz_powm();
        rax = __gmp_printf();
        var_AC = LODWORD(rax);
        __gmpz_clears();
        var_64 = 0x0;

    loc_100000e32:
        rcx = *__stack_chk_guard;
        rcx = *rcx;
        var_B0 = LODWORD(var_64);
        if (rcx == var_8) {
                LODWORD(rax) = var_B0;
                return rax;
        }
        else {
                rax = __stack_chk_fail();
        }
        return rax;

    loc_100000ddf:
        rax = abort();

    loc_100000cf1:
        var_64 = 0x0;
        goto loc_100000e32;
    }
{: .language-js}

or, more clearly:

    int _main(int arg0, int arg1) {
        var_8 = **___stack_chk_guard;
        var_70 = arg1;
        if (arg0 != 0x1) goto loc_100000cfd;

    loc_100000e32:
        var_B0 = 0x0;
        if (**___stack_chk_guard == var_8) {
                rax = var_B0;
        }
        else {
                rax = __stack_chk_fail();
        }
        return rax;

    loc_100000cfd:
        __gmpz_init(&var_50);
        __gmpz_init(&var_60);
        __gmpz_init_set_str(&var_20, "F66EB887F2B8A620FD03C7D0633791CB4804739CE7FE001C81E6E02783737CA21DB2A0D8AF2D10B200006D10737A0872C667AD142F90407132EFABF8E5D6BD51", 0x10);
        __gmpz_init_set_str(&var_40, "65537", 0xa);
        __gmpz_import(&var_50, strlen(*(var_70 + 0x8)), 0x1, 0x1, 0x0, 0x0, *(var_70 + 0x8));
        if (__gmpz_cmp(&var_50, &var_20) <= 0x0) goto loc_100000de4;

    loc_100000ddf:
        rax = abort();
        return rax;

    loc_100000de4:
        __gmpz_powm(&var_60, &var_50, &var_40, &var_20);
        __gmp_printf("Crypted: %ZX\n", &var_60);
        __gmpz_clears(&var_50, &var_60, &var_20, &var_40, 0x0);
        goto loc_100000e32;
    }
{: .language-js}

This looks like RSA, with the commonly used value for exponent `e=65537`
and
`N=0xF66EB887F2B8A620FD03C7D0633791CB4804739CE7FE001C81E6E02783737CA21DB2A0D8AF2D10B200006D10737A0872C667AD142F90407132EFABF8E5D6BD51`

We get the prime factors of `N` (`p` and `q`) using [factordb][1]

    #!/usr/bin/python3
    from Crypto.PublicKey import RSA
    import gmpy2

    def int2Text(number, size):
        text = "".join([chr((number >> j) & 0xff) for j in reversed(range(0, size << 3, 8))])
        return text.lstrip("\x00")

    N = 0xF66EB887F2B8A620FD03C7D0633791CB4804739CE7FE001C81E6E02783737CA21DB2A0D8AF2D10B200006D10737A0872C667AD142F90407132EFABF8E5D6BD51
    C = 0x7A9FDCA5BB061D0D638BE1442586F3488B536399BA05A14FCAE3F0A2E5F268F2F3142D1956769497AE677A12E4D44EC727E255B391005B9ADCF53B4A74FFC34C
    p = 18132985757038135691
    q = 711781150511215724435363874088486910075853913118425049972912826148221297483065007967192431613422409694054064755658564243721555532535827
    e = 65537
    r = (p-1)*(q-1)
    d = int(gmpy2.divm(1, e, r))
    rsa = RSA.construct((N,e,d,p,q))
    pt = rsa.decrypt(C)

    print(int2Text(pt,1000))
{: .language-python}

this prints our flag.



[1]: http://factordb.com
