---
layout: writeup
title: 'Reverse Engineering 50: Java Madness'
level:
difficulty:
points:
categories: []
tags: []
flag: ABCTF{ftc tselooc eht si ftcba}
---
**Challenge**  
Hey if you can get this to pass some tests you could probably have the
flag.

    public class what_the_hack {
        public static void main(String[] args) {
            String check = "";
            if(args.length != 5){
                System.out.println("Almost! (;");
            }
            else {
                for(int i = args.length - 1; i >= 0; i--){
                    System.out.println(i);
                    for(int j = args[i].length() - 1; j >= 0; j--){
                        check += args[i].charAt(j);
                        System.out.println(args[i].charAt(j));
                    }
                }
                if(check.equals("abctf is the coolest ctf")){
                    System.out.println("Flag: " + "ABCTF{" + args[0] + args[1] + args[2] +args[3] + args[4] + "}");
                }
                else{
                    System.out.println(check);
                }
            }
        }
    }
{: .language-java}

**Solution**  
The associated java file used a couple loops to reverse the five
arguments. The  
key was encoded in the file as "abctfs is the coolest ctf". After
understanding  
what the loop did I `echo 'abctf is the coolest ctf' | rev` to get the
key

