package org.apache.commons.codec.binary;

import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import org.apache.http.conn.params.ConnPerRouteBean;
import ps.hacking.hackyeaster.android.BuildConfig;

public class Base32 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 5;
    private static final int BYTES_PER_ENCODED_BLOCK = 8;
    private static final int BYTES_PER_UNENCODED_BLOCK = 5;
    private static final byte[] CHUNK_SEPARATOR;
    private static final byte[] DECODE_TABLE;
    private static final byte[] ENCODE_TABLE;
    private static final byte[] HEX_DECODE_TABLE;
    private static final byte[] HEX_ENCODE_TABLE;
    private static final int MASK_5BITS = 31;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    static {
        CHUNK_SEPARATOR = new byte[]{(byte) 13, (byte) 10};
        DECODE_TABLE = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25};
        ENCODE_TABLE = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55};
        HEX_DECODE_TABLE = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) 32};
        HEX_ENCODE_TABLE = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86};
    }

    public Base32() {
        this(false);
    }

    public Base32(boolean useHex) {
        this(0, null, useHex);
    }

    public Base32(int lineLength) {
        this(lineLength, CHUNK_SEPARATOR);
    }

    public Base32(int lineLength, byte[] lineSeparator) {
        this(lineLength, lineSeparator, false);
    }

    public Base32(int lineLength, byte[] lineSeparator, boolean useHex) {
        super(BYTES_PER_UNENCODED_BLOCK, BYTES_PER_ENCODED_BLOCK, lineLength, lineSeparator == null ? 0 : lineSeparator.length);
        if (useHex) {
            this.encodeTable = HEX_ENCODE_TABLE;
            this.decodeTable = HEX_DECODE_TABLE;
        } else {
            this.encodeTable = ENCODE_TABLE;
            this.decodeTable = DECODE_TABLE;
        }
        if (lineLength <= 0) {
            this.encodeSize = BYTES_PER_ENCODED_BLOCK;
            this.lineSeparator = null;
        } else if (lineSeparator == null) {
            throw new IllegalArgumentException("lineLength " + lineLength + " > 0, but lineSeparator is null");
        } else if (containsAlphabetOrPad(lineSeparator)) {
            throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + StringUtils.newStringUtf8(lineSeparator) + "]");
        } else {
            this.encodeSize = lineSeparator.length + BYTES_PER_ENCODED_BLOCK;
            this.lineSeparator = new byte[lineSeparator.length];
            System.arraycopy(lineSeparator, 0, this.lineSeparator, 0, lineSeparator.length);
        }
        this.decodeSize = this.encodeSize - 1;
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
                inPos = inPos2 + 1;
                byte b = in[inPos2];
                if (b == 61) {
                    context.eof = true;
                    break;
                }
                buffer = ensureBufferSize(this.decodeSize, context);
                if (b >= null && b < this.decodeTable.length) {
                    int result = this.decodeTable[b];
                    if (result >= 0) {
                        context.modulus = (context.modulus + 1) % BYTES_PER_ENCODED_BLOCK;
                        context.lbitWorkArea = (context.lbitWorkArea << BYTES_PER_UNENCODED_BLOCK) + ((long) result);
                        if (context.modulus == 0) {
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) ((int) ((context.lbitWorkArea >> 32) & 255));
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) ((int) ((context.lbitWorkArea >> 24) & 255));
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) ((int) ((context.lbitWorkArea >> 16) & 255));
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) ((int) ((context.lbitWorkArea >> BYTES_PER_ENCODED_BLOCK) & 255));
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) ((int) (context.lbitWorkArea & 255));
                        }
                    }
                }
                i2++;
                inPos2 = inPos;
            }
            if (context.eof && context.modulus >= 2) {
                buffer = ensureBufferSize(this.decodeSize, context);
                switch (context.modulus) {
                    case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> 2) & 255));
                    case WearableExtender.SIZE_MEDIUM /*3*/:
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> 7) & 255));
                    case WearableExtender.SIZE_LARGE /*4*/:
                        context.lbitWorkArea >>= 4;
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> BYTES_PER_ENCODED_BLOCK) & 255));
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) (context.lbitWorkArea & 255));
                    case BYTES_PER_UNENCODED_BLOCK /*5*/:
                        context.lbitWorkArea >>= 1;
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> 16) & 255));
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> BYTES_PER_ENCODED_BLOCK) & 255));
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) (context.lbitWorkArea & 255));
                    case MotionEventCompat.AXIS_TOOL_MAJOR /*6*/:
                        context.lbitWorkArea >>= 6;
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> 16) & 255));
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> BYTES_PER_ENCODED_BLOCK) & 255));
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) (context.lbitWorkArea & 255));
                    case BuildConfig.VERSION_CODE /*7*/:
                        context.lbitWorkArea >>= 3;
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> 24) & 255));
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> 16) & 255));
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) ((context.lbitWorkArea >> BYTES_PER_ENCODED_BLOCK) & 255));
                        i = context.pos;
                        context.pos = i + 1;
                        buffer[i] = (byte) ((int) (context.lbitWorkArea & 255));
                    default:
                        throw new IllegalStateException("Impossible modulus " + context.modulus);
                }
            }
        }
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
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 3)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea << 2)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            break;
                        case ConnPerRouteBean.DEFAULT_MAX_CONNECTIONS_PER_ROUTE /*2*/:
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 11)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 6)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 1)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea << 4)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            break;
                        case WearableExtender.SIZE_MEDIUM /*3*/:
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 19)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 14)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 9)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 4)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea << 1)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
                            break;
                        case WearableExtender.SIZE_LARGE /*4*/:
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 27)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 22)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 17)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 12)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 7)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 2)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = this.encodeTable[((int) (context.lbitWorkArea << 3)) & MASK_5BITS];
                            i = context.pos;
                            context.pos = i + 1;
                            buffer[i] = (byte) 61;
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
                context.lbitWorkArea = (context.lbitWorkArea << BYTES_PER_ENCODED_BLOCK) + ((long) b);
                if (context.modulus == 0) {
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 35)) & MASK_5BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 30)) & MASK_5BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 25)) & MASK_5BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 20)) & MASK_5BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 15)) & MASK_5BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> 10)) & MASK_5BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[((int) (context.lbitWorkArea >> BYTES_PER_UNENCODED_BLOCK)) & MASK_5BITS];
                    i = context.pos;
                    context.pos = i + 1;
                    buffer[i] = this.encodeTable[((int) context.lbitWorkArea) & MASK_5BITS];
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

    public boolean isInAlphabet(byte octet) {
        return octet >= null && octet < this.decodeTable.length && this.decodeTable[octet] != -1;
    }
}
