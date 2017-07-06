package org.apache.commons.codec.digest;

import android.support.v4.view.MotionEventCompat;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.Charsets;

public class Sha2Crypt {
    private static final int ROUNDS_DEFAULT = 5000;
    private static final int ROUNDS_MAX = 999999999;
    private static final int ROUNDS_MIN = 1000;
    private static final String ROUNDS_PREFIX = "rounds=";
    private static final Pattern SALT_PATTERN;
    private static final int SHA256_BLOCKSIZE = 32;
    static final String SHA256_PREFIX = "$5$";
    private static final int SHA512_BLOCKSIZE = 64;
    static final String SHA512_PREFIX = "$6$";

    static {
        SALT_PATTERN = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");
    }

    public static String sha256Crypt(byte[] keyBytes) {
        return sha256Crypt(keyBytes, null);
    }

    public static String sha256Crypt(byte[] keyBytes, String salt) {
        if (salt == null) {
            salt = SHA256_PREFIX + B64.getRandomSalt(8);
        }
        return sha2Crypt(keyBytes, salt, SHA256_PREFIX, SHA256_BLOCKSIZE, MessageDigestAlgorithms.SHA_256);
    }

    private static String sha2Crypt(byte[] keyBytes, String salt, String saltPrefix, int blocksize, String algorithm) {
        int keyLen = keyBytes.length;
        int rounds = ROUNDS_DEFAULT;
        boolean roundsCustom = false;
        if (salt == null) {
            throw new IllegalArgumentException("Salt must not be null");
        }
        Matcher m = SALT_PATTERN.matcher(salt);
        if (m == null || !m.find()) {
            throw new IllegalArgumentException("Invalid salt value: " + salt);
        }
        int i;
        if (m.group(3) != null) {
            rounds = Math.max(ROUNDS_MIN, Math.min(ROUNDS_MAX, Integer.parseInt(m.group(3))));
            roundsCustom = true;
        }
        String saltString = m.group(4);
        byte[] saltBytes = saltString.getBytes(Charsets.UTF_8);
        int saltLen = saltBytes.length;
        MessageDigest ctx = DigestUtils.getDigest(algorithm);
        ctx.update(keyBytes);
        ctx.update(saltBytes);
        MessageDigest altCtx = DigestUtils.getDigest(algorithm);
        altCtx.update(keyBytes);
        altCtx.update(saltBytes);
        altCtx.update(keyBytes);
        byte[] altResult = altCtx.digest();
        int cnt = keyBytes.length;
        while (cnt > blocksize) {
            ctx.update(altResult, 0, blocksize);
            cnt -= blocksize;
        }
        ctx.update(altResult, 0, cnt);
        for (cnt = keyBytes.length; cnt > 0; cnt >>= 1) {
            if ((cnt & 1) != 0) {
                ctx.update(altResult, 0, blocksize);
            } else {
                ctx.update(keyBytes);
            }
        }
        altResult = ctx.digest();
        altCtx = DigestUtils.getDigest(algorithm);
        for (i = 1; i <= keyLen; i++) {
            altCtx.update(keyBytes);
        }
        Object tempResult = altCtx.digest();
        byte[] pBytes = new byte[keyLen];
        int cp = 0;
        while (cp < keyLen - blocksize) {
            System.arraycopy(tempResult, 0, pBytes, cp, blocksize);
            cp += blocksize;
        }
        System.arraycopy(tempResult, 0, pBytes, cp, keyLen - cp);
        altCtx = DigestUtils.getDigest(algorithm);
        i = 1;
        while (true) {
            if (i > (altResult[0] & MotionEventCompat.ACTION_MASK) + 16) {
                break;
            }
            altCtx.update(saltBytes);
            i++;
        }
        tempResult = altCtx.digest();
        Object sBytes = new byte[saltLen];
        cp = 0;
        while (cp < saltLen - blocksize) {
            System.arraycopy(tempResult, 0, sBytes, cp, blocksize);
            cp += blocksize;
        }
        System.arraycopy(tempResult, 0, sBytes, cp, saltLen - cp);
        for (i = 0; i <= rounds - 1; i++) {
            ctx = DigestUtils.getDigest(algorithm);
            if ((i & 1) != 0) {
                ctx.update(pBytes, 0, keyLen);
            } else {
                ctx.update(altResult, 0, blocksize);
            }
            if (i % 3 != 0) {
                ctx.update(sBytes, 0, saltLen);
            }
            if (i % 7 != 0) {
                ctx.update(pBytes, 0, keyLen);
            }
            if ((i & 1) != 0) {
                ctx.update(altResult, 0, blocksize);
            } else {
                ctx.update(pBytes, 0, keyLen);
            }
            altResult = ctx.digest();
        }
        StringBuilder buffer = new StringBuilder(saltPrefix);
        if (roundsCustom) {
            buffer.append(ROUNDS_PREFIX);
            buffer.append(rounds);
            buffer.append("$");
        }
        buffer.append(saltString);
        buffer.append("$");
        if (blocksize == SHA256_BLOCKSIZE) {
            B64.b64from24bit(altResult[0], altResult[10], altResult[20], 4, buffer);
            B64.b64from24bit(altResult[21], altResult[1], altResult[11], 4, buffer);
            B64.b64from24bit(altResult[12], altResult[22], altResult[2], 4, buffer);
            B64.b64from24bit(altResult[3], altResult[13], altResult[23], 4, buffer);
            B64.b64from24bit(altResult[24], altResult[4], altResult[14], 4, buffer);
            B64.b64from24bit(altResult[15], altResult[25], altResult[5], 4, buffer);
            B64.b64from24bit(altResult[6], altResult[16], altResult[26], 4, buffer);
            B64.b64from24bit(altResult[27], altResult[7], altResult[17], 4, buffer);
            B64.b64from24bit(altResult[18], altResult[28], altResult[8], 4, buffer);
            B64.b64from24bit(altResult[9], altResult[19], altResult[29], 4, buffer);
            B64.b64from24bit((byte) 0, altResult[31], altResult[30], 3, buffer);
        } else {
            B64.b64from24bit(altResult[0], altResult[21], altResult[42], 4, buffer);
            B64.b64from24bit(altResult[22], altResult[43], altResult[1], 4, buffer);
            B64.b64from24bit(altResult[44], altResult[2], altResult[23], 4, buffer);
            B64.b64from24bit(altResult[3], altResult[24], altResult[45], 4, buffer);
            B64.b64from24bit(altResult[25], altResult[46], altResult[4], 4, buffer);
            B64.b64from24bit(altResult[47], altResult[5], altResult[26], 4, buffer);
            B64.b64from24bit(altResult[6], altResult[27], altResult[48], 4, buffer);
            B64.b64from24bit(altResult[28], altResult[49], altResult[7], 4, buffer);
            B64.b64from24bit(altResult[50], altResult[8], altResult[29], 4, buffer);
            B64.b64from24bit(altResult[9], altResult[30], altResult[51], 4, buffer);
            B64.b64from24bit(altResult[31], altResult[52], altResult[10], 4, buffer);
            B64.b64from24bit(altResult[53], altResult[11], altResult[SHA256_BLOCKSIZE], 4, buffer);
            B64.b64from24bit(altResult[12], altResult[33], altResult[54], 4, buffer);
            B64.b64from24bit(altResult[34], altResult[55], altResult[13], 4, buffer);
            B64.b64from24bit(altResult[56], altResult[14], altResult[35], 4, buffer);
            B64.b64from24bit(altResult[15], altResult[36], altResult[57], 4, buffer);
            B64.b64from24bit(altResult[37], altResult[58], altResult[16], 4, buffer);
            B64.b64from24bit(altResult[59], altResult[17], altResult[38], 4, buffer);
            B64.b64from24bit(altResult[18], altResult[39], altResult[60], 4, buffer);
            B64.b64from24bit(altResult[40], altResult[61], altResult[19], 4, buffer);
            B64.b64from24bit(altResult[62], altResult[20], altResult[41], 4, buffer);
            B64.b64from24bit((byte) 0, (byte) 0, altResult[63], 2, buffer);
        }
        Arrays.fill(tempResult, (byte) 0);
        Arrays.fill(pBytes, (byte) 0);
        Arrays.fill(sBytes, (byte) 0);
        ctx.reset();
        altCtx.reset();
        Arrays.fill(keyBytes, (byte) 0);
        Arrays.fill(saltBytes, (byte) 0);
        return buffer.toString();
    }

    public static String sha512Crypt(byte[] keyBytes) {
        return sha512Crypt(keyBytes, null);
    }

    public static String sha512Crypt(byte[] keyBytes, String salt) {
        if (salt == null) {
            salt = SHA512_PREFIX + B64.getRandomSalt(8);
        }
        return sha2Crypt(keyBytes, salt, SHA512_PREFIX, SHA512_BLOCKSIZE, MessageDigestAlgorithms.SHA_512);
    }
}
