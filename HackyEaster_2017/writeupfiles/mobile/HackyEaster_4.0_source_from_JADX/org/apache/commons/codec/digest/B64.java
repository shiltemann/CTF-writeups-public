package org.apache.commons.codec.digest;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import java.util.Random;

class B64 {
    static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    B64() {
    }

    static void b64from24bit(byte b2, byte b1, byte b0, int outLen, StringBuilder buffer) {
        int w = (((b2 << 16) & ViewCompat.MEASURED_SIZE_MASK) | ((b1 << 8) & SupportMenu.USER_MASK)) | (b0 & MotionEventCompat.ACTION_MASK);
        int n = outLen;
        while (true) {
            int n2 = n - 1;
            if (n > 0) {
                buffer.append(B64T.charAt(w & 63));
                w >>= 6;
                n = n2;
            } else {
                return;
            }
        }
    }

    static String getRandomSalt(int num) {
        StringBuilder saltString = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            saltString.append(B64T.charAt(new Random().nextInt(B64T.length())));
        }
        return saltString.toString();
    }
}
