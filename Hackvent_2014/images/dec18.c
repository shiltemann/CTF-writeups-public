#include <stdio.h>

int main(){
  
	int r0,r1,r2,r3,r4,r5,r6,r7=0;
	int var_4,var_8;
	
	r3=0;					//MOV             R3, #0
	r0=0xde;				//MOV             R0, #0xDE
	r1=0xba;				//MOV             R1, #0xBA
	if(r0==0){				//CMP             R0, #0
		r1=0xffffffe0;		//MOVEQ           R1, #0xFFFFFFE0
	}
	r0=r0<<8;				//MOV             R0, R0,LSL#8
	r1=r1<<8;				//MOV             R1, R1,LSL#8
	r0=r0|0xad;				//ORR             R0, R0, #0xAD
							//BICEQ           R4, R5, #7   // r4=r5 & !7
	r1=r1|0xbe;				//ORR             R1, R1, #0xBE
	r0=r0<<8;				//MOV             R0, R0,LSL#8
	r5=r0*r1+r2;			//MLA             R5, R0, R1, R2
	r1=r1<<8;				//MOV             R1, R1,LSL#8
							//RSB             R5, R4, R4
	r0=r0|0xc0;				//ORR             R0, R0, #0xC0
	r5=r1+r1<<2;			//ADDS            R5, R1, R1,LSL#2
	r1=r1|0xf0;				//ORR             R1, R1, #0xF0
	r0=r0<<8;				//MOV             R0, R0,LSL#8
	r0=r0|0xde;				//ORR             R0, R0, #0xDE
	r1=r1<<8;				//MOV             R1, R1,LSL#8
	r1=r1|0xd;				//ORR             R1, R1, #0xD
							//TEQ             R5, R4,LSL#3
	r1=r1|0xd;				//ORR             R1, R1, #0xD
	if(r1==0xff){			//CMP             R1, #0xFF
		r0=r1-r0>>31;		//SUBEQ           R0, R1, R0,ASR#31
	}
	r3=r1^r0;				//EOR             R3, R1, R0
	r3=r3>>1;				//MOV             R3, R3,ASR#1
	var_4=r3;				//STR             R3, [SP,#8+var_4]
	r2=r3;					//LDR             R2, [SP,#8+var_8]
	var_8=r3;
	r3=var_4;				//LDR             R3, [SP,#8+var_4]
	if(r2==r3){				//CMP             R2, R3
							//BNE             loc_231C
							
		r3=var_4;			//LDR             R3, [SP,#8+var_4]
		r3=r3>>2;			//MOV             R3, R3,ASR#2
		r3=r3<<16;			//MOV             R3, R3,LSL#16
		r3=r3>>16;			//MOV             R3, R3,LSR#16
		var_4=r3;			//STR             R3, [SP,#8+var_4]
		r3=var_8;			//LDR             R3, [SP,#8+var_8]
		r3=r3>>3;			//MOV             R3, R3,ASR#3
		r3=r3<<16;			//MOV             R3, R3,LSL#16
		r3=r3>>16;			//MOV             R3, R3,LSR#16
		var_8=r3;			//STR             R3, [SP,#8+var_8]
		r2=var_8;			//LDR             R2, [SP,#8+var_8]
							//LDR             R3, =(aCongratsYourKe - 0x2310)
							//ADD             R3, PC, R3 ; "congrats, your key is XMAS-%x-%x-FUN\n"
							//MOV             R0, R3  ; char *
		r1=var_4;			//LDR             R1, [SP,#8+var_4]
							//BL              _printf
	}

	printf("congrats, your key is XMAS-%x-%x-FUN\n",r1,r2);
	//printf("%x %x %x %x %x",r0,r1,r2,r3,r4);	
	
  return 0;
}
