import javax.sound.sampled.*;
import java.io.*;

public class AudioEnc
{
    public static void main(String args[]) {
        try {
            FileOutputStream out = new FileOutputStream(new File("sounds_strange.aiff"));
            encryptAudio("-- redacted --", out);
            out.close();
            decryptAudio(new File("sounds_strange.aiff"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Was unable to encrypt to audio");
        }
        catch (UnsupportedAudioFileException e)
        {
            e.printStackTrace();
        }
    }

    private static void encryptAudio(String toEncrypt, FileOutputStream out) throws IOException
    {
        byte[] bytes = toEncrypt.getBytes();
        AudioInputStream data = new AudioInputStream(new ByteArrayInputStream(bytes), new AudioFormat(2000f, 8, 1, false, false), bytes.length);
        AudioSystem.write(data, AudioFileFormat.Type.AIFF, out);
    }

    private static void decryptAudio(File in) throws IOException, UnsupportedAudioFileException
    {
		//-- redacted --
    }




}
