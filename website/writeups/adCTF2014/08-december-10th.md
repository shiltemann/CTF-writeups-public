---
layout: writeup
title: December 10th
level:
difficulty:
points:
categories: []
tags: []
flag: ADCTF_51mpl3_X0R_R3v3r51n6
---
## Category

## Challenge

## Solution

    #include <stdio.h>
    #include <string.h>
    #include <stdlib.h>

    int main() {
      const char * flag = "712249146f241d31651a504a1a7372384d173f7f790c2b115f47";
      char alphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
      char *a;
      char an[]="";
      int len,alen,t, fl, lastfl,orig;

      len=(int)(strlen(flag)/2);
      alen=strlen(alphabet);

      printf("reversing input..\n");
      for(int i=0; i<len; i++){
        strncpy(an,flag+i*2,2);
        t=strtol(an,&a,16);

        for(int j=0; j<alen; j++){
          fl=alphabet[j];
            orig=fl;
    	    if (i > 0) fl ^= lastfl;
    		fl ^= fl >> 4;
    		fl ^= fl >> 3;
    		fl ^= fl >> 2;
    		fl ^= fl >> 1;

            if (t == fl){
    	    printf("%c",orig);
                break;
    	}
        }
        lastfl=fl;
      }

      printf("\ndone.\n");
      return 0;
    }

