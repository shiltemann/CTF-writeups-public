---
layout: writeup
title: 'Day 07: Bad Morals'
level:
difficulty:
points:
categories: []
tags: []
flag: HV20{r3?3rs3_3ng1n33r1ng_m4d3_34sy}
---
## Description

One of the elves recently took a programming 101 course. Trying to be
helpful, he implemented a program for Santa to generate all the flags
for him for this year's HACKvent 2020. The problem is, he can't remember
how to use the program any more and the link to the documentation just
says `404 Not found`. I bet he learned that in the Programming 101 class
as well.

Can you help him get the flag back?

[BadMorals.exe](writeupfiles/BadMorals.exe)

*Hints*

* There are nearly infinite inputs that pass almost all the tests in the
  program
* For the correct flag, the final test has to be successful as well

## Solution

Ok, we have a windows executable, but no Windows, let's see what we can
gather:

    $ file BadMorals.exe
    BadMorals.exe: PE32 executable (console) Intel 80386 Mono/.Net assembly, for MS Windows

We use ILSpy (The [Avalonia ILSpy port][1] for Linux) to decompine the
.Net program and get some readable code:

    public class Program
    {
    	public static void Main(string[] args)
    	{
    		try
    		{
    			Console.Write("Your first input: ");
    			char[] array = Console.ReadLine().ToCharArray();
    			string text = "";
    			string text2 = "";
    			for (int i = 0; i < array.Length; i++)
    			{
    				if (i % 2 == 0 && i + 2 <= array.Length)
    				{
    					text += array[i + 1].ToString();
    				}
    			}
    			if (text == "BumBumWithTheTumTum")
    			{
    				text2 = "SFYyMH" + array[17].ToString() + "yMz" + array[8].GetHashCode() % 10 + "zcnMzXzN" + array[3].ToString() + "ZzF" + array[9].ToString() + "MzNyM" + array[13].ToString() + "5n" + array[14].ToString() + "2";
    				goto IL_0141;
    			}
    			if (!(text == ""))
    			{
    				text2 = text;
    				goto IL_0141;
    			}
    			Console.WriteLine("Your input is not allowed to result in an empty string");
    			goto end_IL_0000;
    			IL_0141:
    			Console.Write("Your second input: ");
    			char[] array2 = Console.ReadLine().ToCharArray();
    			text = "";
    			string text3 = "";
    			Array.Reverse(array2);
    			for (int j = 0; j < array2.Length; j++)
    			{
    				text += array2[j].ToString();
    			}
    			if (text == "BackAndForth")
    			{
    				text3 = "Q1RGX3" + array2[11].ToString() + "sNH" + array2[8].ToString() + "xbm" + array2[5].ToString() + "f";
    				goto IL_021c;
    			}
    			if (!(text == ""))
    			{
    				text3 = text;
    				goto IL_021c;
    			}
    			Console.WriteLine("Your input is not allowed to result in an empty string");
    			goto end_IL_0000;
    			IL_021c:
    			Console.Write("Your third input: ");
    			char[] array3 = Console.ReadLine().ToCharArray();
    			text = "";
    			string text4 = "";
    			byte b = 42;
    			for (int k = 0; k < array3.Length; k++)
    			{
    				char c = (char)(array3[k] ^ b);
    				b = (byte)(b + k - 4);
    				text += c.ToString();
    			}
    			if (text == "DinosAreLit")
    			{
    				text4 = "00ZD" + array3[3].ToString() + "f" + array3[2].ToString() + "zRzeX0=";
    				goto IL_02e9;
    			}
    			if (!(text == ""))
    			{
    				text4 = text;
    				goto IL_02e9;
    			}
    			Console.WriteLine("Your input is not allowed to result in an empty string");
    			goto end_IL_0000;
    			IL_02e9:
    			byte[] array4 = Convert.FromBase64String(text2 + text4);
    			byte[] array5 = Convert.FromBase64String(text3);
    			byte[] array6 = new byte[array4.Length];
    			for (int l = 0; l < array4.Length; l++)
    			{
    				array6[l] = (byte)(array4[l] ^ array5[l % array5.Length]);
    			}
    			byte[] array7 = SHA1.Create().ComputeHash(array6);
    			byte[] array8 = new byte[20]
    			{
    				107,
    				64,
    				119,
    				202,
    				154,
    				218,
    				200,
    				113,
    				63,
    				1,
    				66,
    				148,
    				207,
    				23,
    				254,
    				198,
    				197,
    				79,
    				21,
    				10
    			};
    			for (int m = 0; m < array7.Length; m++)
    			{
    				if (array7[m] != array8[m])
    				{
    					Console.WriteLine("Your inputs do not result in the flag.");
    					return;
    				}
    			}
    			string @string = Encoding.ASCII.GetString(array4);
    			if (@string.StartsWith("HV20{"))
    			{
    				Console.WriteLine("Congratulations! You're now worthy to claim your flag: {0}", @string);
    			}
    			end_IL_0000:;
    		}
    		catch
    		{
    			Console.WriteLine("Please try again.");
    		}
    		finally
    		{
    			Console.WriteLine("Press enter to exit.");
    			Console.ReadLine();
    		}
    	}
    }
{: .language-c}

So looks like we need to find the input that passes all the tests in the
code, and then we can derive the flag from that.

Lets translate the code into what our strings needs to look like:=

1.  Every odd-numbered character has to translate to
    `BumBumWithTheTumTum`
    * input: `.B.u.m.B.u.m.W.i.t.h.T.h.e.T.u.m.T.u.m.`
    * text2: `SFYyMHtyMz?zcnMzXzNuZzFuMzNyMW5n?2` (tho unknown
      characters)

2.  Input must be "BackAndForth" in reverse `htroFdnAkcaB`
    * text3: `Q1RGX3hsNHoxbmnf`

3.  Input must match an algorithm (see snippet below)
    * input: `nOMNSaSFjC[`
    * text4: `00ZDNfMzRzeX0=`
    ^
    
         import string
        
         def m(i):
             arr3 = list(i)
             b = 42
             text = ''
             for k, character in enumerate(arr3):
                 c = ord(arr3[k]) ^ b
                 b = b + k -4
                 text += chr(c)
        
             return text
         want = 'DinosAreLit'
         key = ''
         for idx, k in enumerate(want):
             for i in string.printable:
                 if m(key + i) == want[0:idx + 1]:
                     print(key + i, m(key + i), want[0:idx + 1])
                     key += i
         # prints
         # nOMNSaSFjC[
    {: .language-python}

4.  Combination of inputs must match a specific SHA1 hash

So we know all the pieces, but piece 2 has two unknown characters. The
right ones will lead the fourth test to pass (SHA1 hash). So we just
adjust the C# code to loop over all possibilities, and print our flag:

    using System;
    using System.Security.Cryptography;
    using System.Text;
    
    public class Program{
    	public static void Main(string[] args)	{
    		byte[] array5 = Convert.FromBase64String("Q1RGX3hsNHoxbmnf");
    
    		string b64chars="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+/";
    		for(int x=0;x<=63; x++){
    			for(int y=0;y<=63; y++){
    				string text2= "SFYyMHtyMz" + b64chars[x] + "zcnMzXzNuZzFuMzNyMW5n" + b64chars[y] + "2";
    				byte[] array4 = Convert.FromBase64String(text2 + "00ZDNfMzRzeX0=");
    				byte[] array6 = new byte[array4.Length];
    				for (int l = 0; l < array4.Length; l++){
    					array6[l] = (byte)(array4[l] ^ array5[l % array5.Length]);
    				}
    				byte[] array7 = SHA1.Create().ComputeHash(array6);
    				byte[] array8 = new byte[20]{107,64,119,202,154,218,200,113,63,1,66,148,207,23,254,198,197,79,21,10};
    				bool match=true;
    				for (int m = 0; m < array7.Length; m++){
    					if (array7[m] != array8[m]){
    						match=false;
    					}
    				}
    				if (match){
    					string flag = Encoding.ASCII.GetString(array4);
    					if (flag.StartsWith("HV20{")){
    						Console.WriteLine("Congratulations! You're now worthy to claim your flag: {0}", flag);
    					}
    				}
    			}
    		}
    	}
    }
{: class="language-c#"}

    Congratulations! You're now worthy to claim your flag: HV20{r3?3rs3_3ng1n33r1ng_m4d3_34sy}

Note: below are instructions to compile and run C# on Ubuntu:

    sudo apt-get install mono-runtime mono-mcs
    mcs dec7_solve.cs
    mono dec7_solve.exe

## Flag

    HV20{r3?3rs3_3ng1n33r1ng_m4d3_34sy}



[1]: https://github.com/icsharpcode/AvaloniaILSpy
