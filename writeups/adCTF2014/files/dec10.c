#include <stdio.h>
#include <string.h>
#include <cstring>
#include <stdlib.h>


int main() {
  char flag[] = "ADCTF_XXXXXXXXXXXXXXXXXXXX";
  char target[] ="712249146f241d31651a504a1a7372384d173f7f790c2b115f47";
  char alphabet[] = "_-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
  char * answer;
  strtol(target,&answer, 16);
  printf("answer: %s\n",answer);


  int len = strlen(flag);
  int len2 = strlen(alphabet);
  int ta,fl, orig,lastfl,val;
  bool found;
  for (int i = 0; i < len; i++) {
   //ta = (int)(target[i*2]-'0')*16 + (int)(target[i*2+1]-'0');
   if (target[i*2]>='a')
		val=(target[i*2]-'a'+16)*16;
   else 
        val=(target[i*2]-'0')*16;
   ta=val;
   if (target[i*2+1]>='a')
		val=target[i*2+1]-'a'+16;
   else 
        val=target[i*2+1]-'0';
   ta+=val;
   //ta = (int)(target[i*2])*10 + (int)(target[i*2+1]);
   
   //printf("target: %i (%i - %i) \n", ta,(int)(target[i*2]-'0')*10,(int)(target[i*2+1]-'0'));
   
   printf("target: %i, %02x",ta,ta);
   found=false;
   for (int j=0; j< len2;j++){
        fl=alphabet[j];
        orig=fl;
	    if (i > 0) fl ^= lastfl;
		fl ^= fl >> 4;
		fl ^= fl >> 3;
		fl ^= fl >> 2;
		fl ^= fl >> 1;

		//printf("found: %c -> %02x\n", orig,(unsigned char)fl);
		

        if (ta == fl){
			printf("%c\n",orig);
            found=true;
            break;
		}
        //else{
        //    printf("nope, ta: %i, fl: %i",ta,fl);
		//}
       
   }
   //printf("\n%s", target);
   lastfl=fl;

   if(!found){
     printf("notfound :(\n");
   }
  }
  return 0;
}
