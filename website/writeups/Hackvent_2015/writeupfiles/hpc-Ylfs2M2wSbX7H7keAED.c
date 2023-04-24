#include <stdio.h>
#include <stdint.h>

uint64_t foo(uint64_t a) {
	uint64_t s = a - 42;
	s += 23*2-3;
	return s;
}

uint64_t bar(uint64_t a) {
	uint64_t s = a + 42;
	s -= 23*2-3;
	return s;
}

uint64_t baz(uint64_t a, uint64_t b) {
	uint64_t r = 0;
	for(uint64_t i=0; i<a; i++)
		r = foo(r);
	for(uint64_t i=0; i<b; i++)
		r = foo(r);
	
	return r;
}

uint64_t spam(uint64_t a, uint64_t b) {
	uint64_t r = baz(0,a);
	for(uint64_t i=0; i<b; i++)
		r = bar(r);
	
	return r;
}

uint64_t eggs(uint64_t a, uint64_t b) {
	uint64_t r = 0;
	for(uint64_t i=0; i<a; i++)
		r = baz(r, b);
	return r;
}

uint64_t merry(uint64_t a, uint64_t b) {
	uint64_t i;
	for(i=0; a>=b; i++)
		a = spam(a, b);
	return i;
}

uint64_t xmas(uint64_t a, uint64_t b) {
	return spam(a, eggs(merry(a,b),b));
}

uint64_t hackvent(uint64_t a, uint64_t b) {
	uint64_t r = 1;
	for(uint64_t i=0; i<a; i++)
		r = eggs(r, b);
	return r;	
}


int main() {
	uint64_t val=0;
	for(uint64_t i=0; i<0xC0DE42; i++) {
		val = xmas(eggs(baz(hackvent(merry(i,42),3),val),i),0x42DEADBABEC0FFEE);
	}
	printf("HV15-mHPC-%04llx-%04llx-%04llx-%04llx\n",
		val>>48,(val&0x0000FFFF00000000)>>32,
		(val&0x00000000FFFF0000)>>16, (val&0x000000000000FFFF));

	return 0;
}