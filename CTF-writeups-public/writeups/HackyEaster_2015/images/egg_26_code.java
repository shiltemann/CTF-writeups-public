import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

class HE2015Challenge26
{
	private static String crypt(final String PIN)
	{
		try
		{
			SecretKeySpec localSecretKeySpec =
				new SecretKeySpec(hash(PIN, "ovaederecumsale", 10000), "AES");
			Cipher localCipher = Cipher.getInstance("AES");
			localCipher.init(2, localSecretKeySpec);

			String encodedString = "8QeNdEdkspV6+1I77SEEEF4aWs5dl/auahJ46MMufkg=";
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] dec64 = decoder.decode(encodedString);
			String str = new String(localCipher.doFinal(dec64));
			return str;
		}
		catch (Exception localException)
		{
			return null;
		}
	}

	public static byte[] hash(String paramString1, String paramString2, int paramInt) throws NoSuchAlgorithmException
	{
		MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
		byte[] arrayOfByte1 = (paramString2 + paramString1).getBytes();
		for(int n = 0;; n++)
		{
			if(n >= paramInt)
			{
				byte[] arrayOfByte2 = new byte[16];
				System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, 15);
				return arrayOfByte2;
			}
			arrayOfByte1 = localMessageDigest.digest(arrayOfByte1);
		}
	}

	public static void main(String[] args)
	{
		// 7113: wirestarter54321
		for(int i=0; i<9999; i++)
		{
			String PIN = String.format("%04d", i);
			String ciphertxt = crypt(PIN);
			if (ciphertxt != null
			&& ciphertxt.matches("^\\p{ASCII}*$"))
			{
				System.out.println(PIN+": "+ciphertxt);
				break;
			}
		}
	}
}