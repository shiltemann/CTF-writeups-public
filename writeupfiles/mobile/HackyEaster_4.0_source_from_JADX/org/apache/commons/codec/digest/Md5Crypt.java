package org.apache.commons.codec.digest;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.Charsets;

public class Md5Crypt {
    static final String APR1_PREFIX = "$apr1$";
    private static final int BLOCKSIZE = 16;
    static final String MD5_PREFIX = "$1$";
    private static final int ROUNDS = 1000;

    public static String apr1Crypt(byte[] keyBytes) {
        return apr1Crypt(keyBytes, APR1_PREFIX + B64.getRandomSalt(8));
    }

    public static String apr1Crypt(byte[] keyBytes, String salt) {
        if (!(salt == null || salt.startsWith(APR1_PREFIX))) {
            salt = APR1_PREFIX + salt;
        }
        return md5Crypt(keyBytes, salt, APR1_PREFIX);
    }

    public static String apr1Crypt(String keyBytes) {
        return apr1Crypt(keyBytes.getBytes(Charsets.UTF_8));
    }

    public static String apr1Crypt(String keyBytes, String salt) {
        return apr1Crypt(keyBytes.getBytes(Charsets.UTF_8), salt);
    }

    public static String md5Crypt(byte[] keyBytes) {
        return md5Crypt(keyBytes, MD5_PREFIX + B64.getRandomSalt(8));
    }

    public static String md5Crypt(byte[] keyBytes, String salt) {
        return md5Crypt(keyBytes, salt, MD5_PREFIX);
    }

    public static String md5Crypt(byte[] keyBytes, String salt, String prefix) {
        String saltString;
        int ii;
        int keyLen = keyBytes.length;
        if (salt == null) {
            saltString = B64.getRandomSalt(8);
        } else {
            Matcher m = Pattern.compile("^" + prefix.replace("$", "\\$") + "([\\.\\/a-zA-Z0-9]{1,8}).*").matcher(salt);
            if (m == null || !m.find()) {
                throw new IllegalArgumentException("Invalid salt value: " + salt);
            }
            saltString = m.group(1);
        }
        byte[] saltBytes = saltString.getBytes(Charsets.UTF_8);
        MessageDigest ctx = DigestUtils.getMd5Digest();
        ctx.update(keyBytes);
        ctx.update(prefix.getBytes(Charsets.UTF_8));
        ctx.update(saltBytes);
        MessageDigest ctx1 = DigestUtils.getMd5Digest();
        ctx1.update(keyBytes);
        ctx1.update(saltBytes);
        ctx1.update(keyBytes);
        byte[] finalb = ctx1.digest();
        for (ii = keyLen; ii > 0; ii -= 16) {
            int i;
            if (ii > BLOCKSIZE) {
                i = BLOCKSIZE;
            } else {
                i = ii;
            }
            ctx.update(finalb, 0, i);
        }
        Arrays.fill(finalb, (byte) 0);
        for (ii = keyLen; ii > 0; ii >>= 1) {
            if ((ii & 1) == 1) {
                ctx.update(finalb[0]);
            } else {
                ctx.update(keyBytes[0]);
            }
        }
        StringBuilder passwd = new StringBuilder(prefix + saltString + "$");
        finalb = ctx.digest();
        for (int i2 = 0; i2 < ROUNDS; i2++) {
            ctx1 = DigestUtils.getMd5Digest();
            if ((i2 & 1) != 0) {
                ctx1.update(keyBytes);
            } else {
                ctx1.update(finalb, 0, BLOCKSIZE);
            }
            if (i2 % 3 != 0) {
                ctx1.update(saltBytes);
            }
            if (i2 % 7 != 0) {
                ctx1.update(keyBytes);
            }
            if ((i2 & 1) != 0) {
                ctx1.update(finalb, 0, BLOCKSIZE);
            } else {
                ctx1.update(keyBytes);
            }
            finalb = ctx1.digest();
        }
        B64.b64from24bit(finalb[0], finalb[6], finalb[12], 4, passwd);
        B64.b64from24bit(finalb[1], finalb[7], finalb[13], 4, passwd);
        B64.b64from24bit(finalb[2], finalb[8], finalb[14], 4, passwd);
        B64.b64from24bit(finalb[3], finalb[9], finalb[15], 4, passwd);
        B64.b64from24bit(finalb[4], finalb[10], finalb[5], 4, passwd);
        B64.b64from24bit((byte) 0, (byte) 0, finalb[11], 2, passwd);
        ctx.reset();
        ctx1.reset();
        Arrays.fill(keyBytes, (byte) 0);
        Arrays.fill(saltBytes, (byte) 0);
        Arrays.fill(finalb, (byte) 0);
        return passwd.toString();
    }
}
