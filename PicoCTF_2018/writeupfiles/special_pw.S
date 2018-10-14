.intel_syntax noprefix
.bits 32

.global main	; int main(int argc, char **argv)

main:
	;push   ebp          ; prologue
	;mov    ebp,esp
	;sub    esp,0x10
	mov    DWORD PTR [ebp-0xc],0x0 ; 0x0
	mov    eax,DWORD PTR [ebp+0xc] ; eax = 0x0
	mov    eax,DWORD PTR [eax+0x4] ;
	mov    DWORD PTR [ebp-0x4],eax ;
	jmp    part_b
part_a:
	add    DWORD PTR [ebp-0xc],0x1
	add    DWORD PTR [ebp-0x4],0x1
part_b:
	mov    eax,DWORD PTR [ebp-0x4]
	movzx  eax,BYTE PTR [eax]
	test   al,al
	jne    part_a
	mov    DWORD PTR [ebp-0x8],0x0
	jmp    part_d
part_c:
	mov    eax,DWORD PTR [ebp+0xc]
	add    eax,0x4
	mov    edx,DWORD PTR [eax]
	mov    eax,DWORD PTR [ebp-0x8]
	add    eax,edx
	mov    DWORD PTR [ebp-0x4],eax
	mov    eax,DWORD PTR [ebp-0x4]
	movzx  eax,BYTE PTR [eax]
	xor    eax,0x4c
	mov    edx,eax
	mov    eax,DWORD PTR [ebp-0x4]
	mov    BYTE PTR [eax],dl
	mov    eax,DWORD PTR [ebp-0x4]
	movzx  eax,WORD PTR [eax]
	ror    ax,0x9
	mov    edx,eax
	mov    eax,DWORD PTR [ebp-0x4]
	mov    WORD PTR [eax],dx
	mov    eax,DWORD PTR [ebp-0x4]
	mov    eax,DWORD PTR [eax]
	rol    eax,0x7
	mov    edx,eax
	mov    eax,DWORD PTR [ebp-0x4]
	mov    DWORD PTR [eax],edx
	add    DWORD PTR [ebp-0x8],0x1
part_d:
	mov    eax,DWORD PTR [ebp-0xc]
	sub    eax,0x3
	cmp    eax,DWORD PTR [ebp-0x8]
	jg     part_c
	mov    eax,DWORD PTR [ebp+0xc]
	mov    eax,DWORD PTR [eax+0x4]
	mov    DWORD PTR [ebp-0x4],eax
	mov    DWORD PTR [ebp-0x10],0x3c7fc74
	jmp    part_f
part_e:
	mov    eax,DWORD PTR [ebp-0x4]
	movzx  edx,BYTE PTR [eax]
	mov    eax,DWORD PTR [ebp-0x10]
	movzx  eax,BYTE PTR [eax]
	cmp    dl,al
	je     part_k
	mov    eax,0x0
	jmp    part_h
part_k:
	add    DWORD PTR [ebp-0x4],0x1
	add    DWORD PTR [ebp-0x10],0x1
part_f:
	mov    eax,DWORD PTR [ebp-0x10]
	movzx  eax,BYTE PTR [eax]
	test   al,al
	jne    part_e
	mov    eax,DWORD PTR [ebp+0xc]
	add    eax,0x4
	mov    eax,DWORD PTR [eax]      ; move value of eax into eax.
	mov    edx,DWORD PTR [ebp-0x10] ; move value of ebp-0x10 into edx
	;mov    ecx,0x3c7fc74
	;sub    edx,ecx ; edx -= 0x3c7fc74
	add    eax,edx                  ; eax += (edx - 0x3c7fc74)
									; eax = 0x03c7fc99 ; This should be last 4 bytes since that's only location with 0?
	movzx  eax,BYTE PTR [eax]       ; moves last byte into eax + zeros the rest. So should be 0?
	test   al,al                    ; al == 0 ?
	je     part_g
	mov    eax,0x0			; LOGIN_FAILED
	jmp    part_h
part_g:
	mov    eax,0x1			; LOGIN_SUCCESS
part_h:
	leave
	ret


03C7FC74:  37 a1 2a a3 bd 33 22 ba  2f 3c 98 aa b9 2f 39 a4
03C7FC84:  18 33 aa 9a 2f 39 18 b3  24 3a af 18 99 1c 1c b1
03C7FC94:  19 b1 98 1b 3e 59 ae a6  00
