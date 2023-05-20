package org.apache.commons.codec.binary;

import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.math.BigInteger;
import org.apache.http.conn.params.ConnPerRouteBean;

public class Base64 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 6;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    static final byte[] CHUNK_SEPARATOR;
    private static final byte[] DECODE_TABLE;
    private static final int MASK_6BITS = 63;
    private static final byte[] STANDARD_ENCODE_TABLE;
    private static final byte[] URL_SAFE_ENCODE_TABLE;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    static {
        CHUNK_SEPARATOR = new byte[]{(byte) 13, (byte) 10};
        STANDARD_ENCODE_TABLE = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
        URL_SAFE_ENCODE_TABLE = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 45, (byte) 95};
        DECODE_TABLE = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 62, (byte) -1, (byte) 62, (byte) -1, (byte) 63, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 60, (byte) 61, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 63, (byte) -1, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) 32, (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 38, (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44, (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 49, (byte) 50, (byte) 51};
    }

    public Base64() {
        this(0);
    }

    public Base64(boolean urlSafe) {
        this(76, CHUNK_SEPARATOR, urlSafe);
    }

    public Base64(int lineLength) {
        this(lineLength, CHUNK_SEPARATOR);
    }

    public Base64(int lineLength, byte[] lineSeparator) {
        this(lineLength, lineSeparator, false);
    }

    public Base64(int lineLength, byte[] lineSeparator, boolean urlSafe) {
        int i;
        if (lineSeparator == null) {
            i = 0;
        } else {
            i = lineSeparator.length;
        }
        super(BYTES_PER_UNENCODED_BLOCK, BYTES_PER_ENCODED_BLOCK, lineLength, i);
        this.decodeTable = DECODE_TABLE;
        if (lineSeparator == null) {
            this.encodeSize = BYTES_PER_ENCODED_BLOCK;
            this.lineSeparator = null;
        } else if (containsAlphabetOrPad(lineSeparator)) {
            throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + StringUtils.newStringUtf8(lineSeparator) + "]");
        } else if (lineLength > 0) {
            this.encodeSize = lineSeparator.length + BYTES_PER_ENCODED_BLOCK;
            this.lineSeparator = new byte[lineSeparator.length];
            System.arraycopy(lineSeparator, 0, this.lineSeparator, 0, lineSeparator.length);
        } else {
            this.encodeSize = BYTES_PER_ENCODED_BLOCK;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
        this.encodeTable = urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    }

    public boolean isUrlSafe() {
        return this.encodeTable == URL_SAFE_ENCODE_TABLE;
    }

    void encode(byte[] in, int inPos, int inAvail, Context context) {
        if (!context.eof) {
            byte[] buffer;
            int i;
            if (inAvail < 0) {
                context.eof = true;
                if (context.modulus != 0 || this.lineLength != 0) {
                    buffer = ensureBufferSize(this.encodeSize, context);
                    int savedPos = context.pos;
                    switch (context.modulus) {
                        case WearableExtender.SIZE_DEFAULT /*0*/:
                            break;
                        case WearableExtender.SIZE_XSMALL /*1*/:
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[(context.ibitWorkArea >> 2) & MASK_6BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[(context.ibitWorkArea << BYTES_PER_ENCODED_BLOCK) & MASK_6BITS];
                            if (this.encodeTable == STANDARD_ENCODE_TABLE) {
                                i = context.pos;
                                context.pos = i + 1;
                                buffer[i] = (byte) 61;
                                i = context.pos;
                                context.pos = i + 1;
                                buffer[i] = (byte) 61;
                                break;
                            }
                            break;
                        case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[(context.ibitWorkArea >> 10) & MASK_6BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[(context.ibitWorkArea >> BYTES_PER_ENCODED_BLOCK) & MASK_6BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[(context.ibitWorkArea << 2) & MASK_6BITS];
                            if (this.encodeTable == STANDARD_ENCODE_TABLE) {
                                i = context.pos;
                                context.pos = i + 1;
                                buffer[i] = (byte) 61;
                                break;
                            }
                            break;
                        default:
                            throw new IllegalStateException("Impossible modulus " + context.modulus);
                    }
                    context.currentLinePos += context.pos - savedPos;
                    if (this.lineLength > 0 && context.currentLinePos > 0) {
                        System.arraycopy(this.lineSeparator, 0, buffer, context.pos, this.lineSeparator.length);
                        context.pos += this.lineSeparator.length;
                        return;
                    }
                    return;
                }
                return;
            }
            int i2 = 0;
            int inPos2 = inPos;
            while (i2 < inAvail) {
                buffer = ensureBufferSize(this.encodeSize, context);
                context.modulus = (context.modulus + 1) % BYTES_PER_UNENCODED_BLOCK;
                inPos = inPos2 + 1;
                int b = in[inPos2];
                if (b < 0) {
                    b += AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
                }
                context.ibitWorkArea = (context.ibitWorkArea << 8) + b;
                if (context.modulus == 0) {
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[(context.ibitWorkArea >> 18) & MASK_6BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[(context.ibitWorkArea >> 12) & MASK_6BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[(context.ibitWorkArea >> BITS_PER_ENCODED_BYTE) & MASK_6BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[context.ibitWorkArea & MASK_6BITS];
                    context.currentLinePos += BYTES_PER_ENCODED_BLOCK;
                    if (this.lineLength > 0 && this.lineLength <= context.currentLinePos) {
                        System.arraycopy(this.lineSeparator, 0, buffer, context.pos, this.lineSeparator.length);
                        context.pos += this.lineSeparator.length;
                        context.currentLinePos = 0;
                    }
                }
                i2++;
                inPos2 = inPos;
            }
            inPos = inPos2;
        }
    }

    void decode(byte[] in, int inPos, int inAvail, Context context) {
        if (!context.eof) {
            byte[] buffer;
            int i;
            if (inAvail < 0) {
                context.eof = true;
            }
            int i2 = 0;
            int inPos2 = inPos;
            while (i2 < inAvail) {
                buffer = ensureBufferSize(this.decodeSize, context);
                inPos = inPos2 + 1;
                byte b = in[inPos2];
                if (b == 61) {
                    context.eof = true;
                    break;
                }
                if (b >= null && b < DECODE_TABLE.length) {
                    int result = DECODE_TABLE[b];
                    if (result >= 0) {
                        context.modulus = (context.modulus + 1) % BYTES_PER_ENCODED_BLOCK;
                        context.ibitWorkArea = (context.ibitWorkArea << BITS_PER_ENCODED_BYTE) + result;
                        if (context.modulus == 0) {
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) ((context.ibitWorkArea >> 16) & MotionEventCompat.ACTION_MASK);
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) ((context.ibitWorkArea >> 8) & MotionEventCompat.ACTION_MASK);
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) (context.ibitWorkArea & MotionEventCompat.ACTION_MASK);
                        }
                    }
                }
                i2++;
                inPos2 = inPos;
            }
            if (context.eof && context.modulus != 0) {
                buffer = ensureBufferSize(this.decodeSize, context);
                switch (context.modulus) {
                    case WearableExtender.SIZE_XSMALL /*1*/:
                    case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                        context.ibitWorkArea >>= BYTES_PER_ENCODED_BLOCK;
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) (context.ibitWorkArea & MotionEventCompat.ACTION_MASK);
                    case BYTES_PER_UNENCODED_BLOCK /*3*/:
                        context.ibitWorkArea >>= 2;
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((context.ibitWorkArea >> 8) & MotionEventCompat.ACTION_MASK);
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) (context.ibitWorkArea & MotionEventCompat.ACTION_MASK);
                    default:
                        throw new IllegalStateException("Impossible modulus " + context.modulus);
                }
            }
        }
    }

    @Deprecated
    public static boolean isArrayByteBase64(byte[] arrayOctet) {
        return isBase64(arrayOctet);
    }

    public static boolean isBase64(byte octet) {
        return octet == 61 || (octet >= null && octet < DECODE_TABLE.length && DECODE_TABLE[octet] != -1);
    }

    public static boolean isBase64(String base64) {
        return isBase64(StringUtils.getBytesUtf8(base64));
    }

    public static boolean isBase64(byte[] arrayOctet) {
        int i = 0;
        while (i < arrayOctet.length) {
            if (!isBase64(arrayOctet[i]) && !BaseNCodec.isWhiteSpace(arrayOctet[i])) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static byte[] encodeBase64(byte[] binaryData) {
        return encodeBase64(binaryData, false);
    }

    public static String encodeBase64String(byte[] binaryData) {
        return StringUtils.newStringUtf8(encodeBase64(binaryData, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] binaryData) {
        return encodeBase64(binaryData, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] binaryData) {
        return StringUtils.newStringUtf8(encodeBase64(binaryData, false, true));
    }

    public static byte[] encodeBase64Chunked(byte[] binaryData) {
        return encodeBase64(binaryData, true);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked) {
        return encodeBase64(binaryData, isChunked, false);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe) {
        return encodeBase64(binaryData, isChunked, urlSafe, Integer.MAX_VALUE);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize) {
        if (binaryData == null || binaryData.length == 0) {
            return binaryData;
        }
        Base64 b64 = isChunked ? new Base64(urlSafe) : new Base64(0, CHUNK_SEPARATOR, urlSafe);
        long len = b64.getEncodedLength(binaryData);
        if (len <= ((long) maxResultSize)) {
            return b64.encode(binaryData);
        }
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + len + ") than the specified maximum size of " + maxResultSize);
    }

    public static byte[] decodeBase64(String base64String) {
        return new Base64().decode(base64String);
    }

    public static byte[] decodeBase64(byte[] base64Data) {
        return new Base64().decode(base64Data);
    }

    public static BigInteger decodeInteger(byte[] pArray) {
        return new BigInteger(1, decodeBase64(pArray));
    }

    public static byte[] encodeInteger(BigInteger bigInt) {
        if (bigInt != null) {
            return encodeBase64(toIntegerBytes(bigInt), false);
        }
        throw new NullPointerException("encodeInteger called with null parameter");
    }

    static byte[] toIntegerBytes(BigInteger bigInt) {
        int bitlen = ((bigInt.bitLength() + 7) >> BYTES_PER_UNENCODED_BLOCK) << BYTES_PER_UNENCODED_BLOCK;
        byte[] bigBytes = bigInt.toByteArray();
        if (bigInt.bitLength() % 8 != 0 && (bigInt.bitLength() / 8) + 1 == bitlen / 8) {
            return bigBytes;
        }
        int startSrc = 0;
        int len = bigBytes.length;
        if (bigInt.bitLength() % 8 == 0) {
            startSrc = 1;
            len--;
        }
        byte[] resizedBytes = new byte[(bitlen / 8)];
        System.arraycopy(bigBytes, startSrc, resizedBytes, (bitlen / 8) - len, len);
        return resizedBytes;
    }

    protected boolean isInAlphabet(byte octet) {
        return octet >= null && octet < this.decodeTable.length && this.decodeTable[octet] != -1;
    }
}
