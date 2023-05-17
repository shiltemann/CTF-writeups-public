package org.apache.commons.codec.digest;

import org.apache.commons.codec.Charsets;

public class Crypt {
    public static String crypt(byte[] keyBytes) {
        return crypt(keyBytes, null);
    }

    public static String crypt(byte[] keyBytes, String salt) {
        if (salt == null) {
            return Sha2Crypt.sha512Crypt(keyBytes);
        }
        if (salt.startsWith("$6$")) {
            return Sha2Crypt.sha512Crypt(keyBytes, salt);
        }
        if (salt.startsWith("$5$")) {
            return Sha2Crypt.sha256Crypt(keyBytes, salt);
        }
        if (salt.startsWith("$1$")) {
            return Md5Crypt.md5Crypt(keyBytes, salt);
        }
        return UnixCrypt.crypt(keyBytes, salt);
    }

    public static String crypt(String key) {
        return crypt(key, null);
    }

    public static String crypt(String key, String salt) {
        return crypt(key.getBytes(Charsets.UTF_8), salt);
    }
}
