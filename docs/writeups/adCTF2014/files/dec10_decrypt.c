#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main() {
  const char * flag = "712249146f241d31651a504a1a7372384d173f7f790c2b115f47";
  char alphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-"; 
  char *a, *an;
  int len,alen,t, fl, lastfl,orig,prev;
  
  len=(int)(strlen(flag));
  alen=strlen(alphabet);

   for(int i=0; i<len; i+=2){
    //strncpy(an,flag+len-i-2,2);
    strncpy(an,flag+i,2);
    t=strtol(an,&a,16);
   

    printf("looking for %02x\n",t);

    orig=t;
    
    t ^= t >> 4;
    t ^= t >> 3;
    t ^= t >> 2;
    t ^= t >> 1;
    if(i>0) t ^ prev;

    prev=t;
    printf("answer is %02x, %c\n",t,(unsigned char)t);

  }

/*
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
  
*/
  return 0;
}
