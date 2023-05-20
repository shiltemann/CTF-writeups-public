---
layout: writeup
title: Matrices
level: 
difficulty: 
points: 
categories: []
tags: []
flag: 
---
**Challenge**  
Looks like Zob tried to make his own encryption algorithm again; here's
the program. Here, here, and here are two messages encrypted with the
same key and the plaintext of one of the messages. Convince Zob to stop
trying to make his own ciphers!

    import java.util.Arrays;
    import java.io.*;
    
    public class matrix {
    
        public static final int N = 16;
    
        public static int add(int a, int b){
            return (a+b)%251;
        }
    
        public static int multiply(int a, int b){
            return (a*b)%251;
        }
    
        public static int[][] random(int m, int n) {
            int[][] C = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    C[i][j] = (int) (Math.random()*254+1);
            return C;
        }
    
        public static int dot(int[] x, int[] y) {
            if (x.length != y.length) throw new RuntimeException("Illegal vector dimensions.");
            int sum = 0;
            for (int i = 0; i < x.length; i++)
                sum = add(sum, multiply(x[i], y[i]));
            return sum;
        }
    
        public static int[][] multiply(int[][] A, int[][] B) {
            int mA = A.length;
            int nA = A[0].length;
            int mB = B.length;
            int nB = B[0].length;
            if (nA != mB) throw new RuntimeException("Illegal matrix dimensions.");
            int[][] C = new int[mA][nB];
            for (int i = 0; i < mA; i++)
                for (int j = 0; j < nB; j++)
                    for (int k = 0; k < nA; k++)
                        C[i][j] = add(C[i][j], multiply(A[i][k], B[k][j]));
            return C;
        }
    
        public static int[] multiply(int[][] A, int[] x) {
            int m = A.length;
            int n = A[0].length;
            if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
            int[] y = new int[m];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    y[i] = add(y[i], multiply(A[i][j], x[j]));
            return y;
        }
    
        public static int[] multiply(int[] x, int[][] A) {
            int m = A.length;
            int n = A[0].length;
            if (x.length != m) throw new RuntimeException("Illegal matrix dimensions.");
            int[] y = new int[n];
            for (int j = 0; j < n; j++)
                for (int i = 0; i < m; i++)
                    y[j] = add(y[j], multiply(A[i][j], x[i]));
            return y;
        }
    
        public static void main(String[] args) {
            int [][] key = random(N,N);
            printArr(key);
    
            String message = args[0];
    
            int[] messagebuf = new int[N];
    
            String result = "";
            byte[] resultarr = new byte[(message.length()/N+1)*N];
    
    
            for (int i=0;i<message.length();i+=N){
                for (int j=0;j<N;j++){
                    if (i+j<message.length()){
                        messagebuf[j]=messagebuf[j]^(int) message.charAt(i+j);
                    }else{
                        messagebuf[j]=0;
                    }
                }
                messagebuf = multiply(key,messagebuf);
                for (int j=0;j<N;j++){
                    resultarr[i+j] = (byte) messagebuf[j];
                    result += (char) messagebuf[j];
                }
            }
    
            try (FileOutputStream fos = new FileOutputStream("output1")) {
                fos.write(resultarr);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    
        public static void printArr(int[][] arr){
            for (int i=0;i<arr.length;i++){
                System.out.println(Arrays.toString(arr[i]));
            }
        }
    }
{: .language-java}

message:

    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.

[output1](writeupfiles/output2), [output2](writeupfiles/output2)

*HINT* Technically, all the math you need for this problem you learned
in Algebra 2.

**Solution**  
## Flag

