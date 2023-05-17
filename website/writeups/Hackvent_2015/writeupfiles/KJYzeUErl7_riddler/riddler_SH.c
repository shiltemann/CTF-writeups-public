#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>
#include <sys/types.h>
#include <signal.h>
#include <unistd.h>

int fib(int n) {
	double phi = (1.0+sqrt(5.0))/2.0;
	return floor(pow(phi,n)/sqrt(5.0) + 0.5);
}

void riddle(int sockid) {
	int debug_mode = 0;
	FILE *sock = fdopen(sockid, "r+b");

	fprintf(sock,"Riddle me this, riddle me that. What about solving a bunch of riddles for a present or two?\n");
	
	int cnt = 0;
	fprintf(sock,"%d", fib(0));
	for (int i=1; i<5; i++) {
		fprintf(sock,", %d", fib(i));
	}
	fprintf(sock," ... ?\n");
	
	while (cnt < 10) {
		char input[32];
		fgets(input, 31, sock);
		input[31] = 0;
		int value = atoi(input);
		if (value == 0) {
			fprintf(sock,"parse error: ");
			fprintf(sock,input);
                        fprintf(sock,"count: %i, debug: %i",cnt,debug_mode);
		} else if (value == fib(cnt+5)) {
			cnt++;
			fprintf(sock,"ACK, go ahead...\n");
		} else {
			fprintf(sock,"RST. Go ask Leonardo for help ...\n");
                        fprintf(sock,"count: %i, debug: %i",cnt,debug_mode);
			fclose(sock);
			return;
		}
	}
	if (debug_mode) {
		FILE *f = fopen("ball.txt", "r");
		char ball[128];
		fgets(ball, 127, f);
		fclose(f);
		fprintf(sock,"Debug mode: %d riddles solved ==> '%s'\n", cnt, ball);
	} else {
		fprintf(sock,"You really thought I would give away my precious stuff?\n"
			"  That was a joke. HAHA. FAT CHANCE.\n"
			"(if you want to prank your friends, find this little code at http://hackvent.hacking-lab.com/KJYzeUErl7_riddler.tar.gz)\n\n");		
	}
	fclose(sock);
}

int main() {
	struct sockaddr_in myaddr ,clientaddr;
	int sockid,newsockid;
	signal(SIGCHLD, SIG_IGN);
	sockid=socket(AF_INET,SOCK_STREAM,0);
	memset(&myaddr,'\0',sizeof(myaddr));
	myaddr.sin_family=AF_INET;
	myaddr.sin_port=htons(8888);
	myaddr.sin_addr.s_addr=inet_addr("0.0.0.0");
	socklen_t len=sizeof(myaddr);
	bind(sockid,(struct sockaddr*)&myaddr,len);
	listen(sockid,10);
	int pid,new;
	for(;;) {
		new = accept(sockid,(struct sockaddr *)&clientaddr,&len);
		pid = fork();
		if(pid==0){
			close(sockid);
			riddle(new);
			close(new);
			exit(0);
		} else {
			close(new);
			printf("spawned new child, pid %d\n", pid);
		}
	}
	close(sockid);
	return 0;
}

