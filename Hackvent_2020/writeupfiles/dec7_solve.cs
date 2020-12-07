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

