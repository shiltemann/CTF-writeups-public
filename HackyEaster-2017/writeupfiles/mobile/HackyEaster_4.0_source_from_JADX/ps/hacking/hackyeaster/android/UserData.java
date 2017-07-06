package ps.hacking.hackyeaster.android;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserData {
    private static final String FILE_NAME = "oASvFghocVkqeb84UwUb";
    private static String ticket;
    private static String userName;

    public static boolean load(Context context) {
        synchronized (UserData.class) {
            try {
                userName = null;
                ticket = null;
                if (context.getFileStreamPath(FILE_NAME).exists()) {
                    String[] parts = readFromFile(context).split("\\|");
                    if (parts != null && parts.length == 2) {
                        userName = parts[0];
                        ticket = parts[1];
                    }
                    return true;
                }
                return false;
            } catch (IOException e) {
                return false;
            }
        }
    }

    public static boolean store(Context context, String pUserName, String pTicket) {
        boolean z;
        synchronized (UserData.class) {
            try {
                writeToFile(context, pUserName + "|" + pTicket);
                userName = pUserName;
                ticket = pTicket;
                z = true;
            } catch (IOException e) {
                z = false;
            }
        }
        return z;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getTicket() {
        return ticket;
    }

    public static String toJsonString() {
        return "{\"userName\": \"" + (userName == null ? BuildConfig.FLAVOR : userName) + "\", \"ticket\": \"" + (ticket == null ? BuildConfig.FLAVOR : ticket) + "\"}";
    }

    private static String readFromFile(Context context) throws IOException {
        return new BufferedReader(new InputStreamReader(context.openFileInput(FILE_NAME))).readLine();
    }

    private static void writeToFile(Context context, String newId) throws IOException {
        FileOutputStream out = context.openFileOutput(FILE_NAME, 0);
        out.write(newId.getBytes());
        out.flush();
        out.close();
    }
}
