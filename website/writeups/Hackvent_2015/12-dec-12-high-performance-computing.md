---
layout: writeup
title: 'Dec 12: High Performance Computing'
level:
difficulty:
points:
categories: []
tags: []
flag:  HV15-mHPC-067e-751e-f50e-17e3

---

## Challenge

We were given this little piece of code to run.
Unfortunately it takes a bit too long to terminate on our systems and
even gcc's -O3 does not seem to help.
Maybe you want to run it on your new peta-flop CPU for a couple of
years?

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
{: .language-c}

## Solution

This code would take forever to terminate, so we must rewrite the code
to optimize it for speed.

We notice that most of the functions actually perform very simple
operations, but go out of their way to do it very slowly:

    foo(a)        --  a++
    bar(a)        --  a--
    baz(a,b)      --  a+b
    spam(a,b)     --  a-b
    eggs(a,b)     --  a*b
    merry(a,b)    --  a/b
    xmas(a,b)     --  a - (a/b)*b
    hackvent(a,b) --  b^a

We factor out most of these functions. The `hackvent` function cannot
simply be replaced by `pow(b,a)` because we are using 64 bit integers,

but we can use a little trick to speed up the integer power function
using the knowledge that:

    a^n  --> a^(n/2) * a^(n/2)          for even n
             a * a^(n/2) * a^(n/2)      for odd n

So we can write a recursive function to calculate the power which will
be much quicker.

After applying all optimisations, our code looks like this:

    #include <stdio.h>
    #include <stdint.h>

    uint64_t mypow(uint64_t base, uint64_t exp){
    	if (exp==0)
    		return 1

    	uint64_t x = mypow(base,exp/2);

    	if(exp % 2==0)
    		return x*x;
    	else
    		return base*x*x;
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
{: .language-c}

And gives us our nugget within a second.

*NOTE: The above power function only works for positive exponents, a
more general integer power algorithm is:*

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
{: .language-c}



