#include <stdio.h>
#include <stdint.h>

uint64_t mypow(uint64_t base, uint64_t exp){
	if (exp==0) 
		return 1;
	uint64_t x = mypow(base,exp/2);

	if(exp % 2==0) 
		return x*x;
	else 
		return base*x*x;

}

uint64_t mypow2(uint64_t base, uint64_t exp){
	uint64_t result = 1;
    	while (exp){
        	if (exp & 1)
        	    result *= base;
        	exp >>= 1;
        	base *= base;
    	}
    	return result;
}

uint64_t xmas(uint64_t a, uint64_t b) {
	return a - (a/b)*b;
}

int main() {
	uint64_t val=0;
	for(uint64_t i=0; i<0xC0DE42; i++) {
                val = xmas( (mypow( 3,i/42) + val) * i ,0x42DEADBABEC0FFEE);
	}
	printf("HV15-mHPC-%04llx-%04llx-%04llx-%04llx\n",
		val>>48,(val&0x0000FFFF00000000)>>32,
		(val&0x00000000FFFF0000)>>16, (val&0x000000000000FFFF));
	return 0;
}
