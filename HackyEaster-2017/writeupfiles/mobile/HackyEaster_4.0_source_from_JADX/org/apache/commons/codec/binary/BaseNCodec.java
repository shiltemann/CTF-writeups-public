package org.apache.commons.codec.binary;

import java.util.Arrays;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.http.protocol.HTTP;

public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder {
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    static final int EOF = -1;
    protected static final int MASK_8BITS = 255;
    public static final int MIME_CHUNK_SIZE = 76;
    protected static final byte PAD_DEFAULT = (byte) 61;
    public static final int PEM_CHUNK_SIZE = 64;
    protected final byte PAD;
    private final int chunkSeparatorLength;
    private final int encodedBlockSize;
    protected final int lineLength;
    private final int unencodedBlockSize;

    static class Context {
        byte[] buffer;
        int currentLinePos;
        boolean eof;
        int ibitWorkArea;
        long lbitWorkArea;
        int modulus;
        int pos;
        int readPos;

        Context() {
        }

        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", new Object[]{getClass().getSimpleName(), Arrays.toString(this.buffer), Integer.valueOf(this.currentLinePos), Boolean.valueOf(this.eof), Integer.valueOf(this.ibitWorkArea), Long.valueOf(this.lbitWorkArea), Integer.valueOf(this.modulus), Integer.valueOf(this.pos), Integer.valueOf(this.readPos)});
        }
    }

    abstract void decode(byte[] bArr, int i, int i2, Context context);

    abstract void encode(byte[] bArr, int i, int i2, Context context);

    protected abstract boolean isInAlphabet(byte b);

    protected BaseNCodec(int unencodedBlockSize, int encodedBlockSize, int lineLength, int chunkSeparatorLength) {
        boolean useChunking;
        int i = 0;
        this.PAD = PAD_DEFAULT;
        this.unencodedBlockSize = unencodedBlockSize;
        this.encodedBlockSize = encodedBlockSize;
        if (lineLength <= 0 || chunkSeparatorLength <= 0) {
            useChunking = false;
        } else {
            useChunking = true;
        }
        if (useChunking) {
            i = (lineLength / encodedBlockSize) * encodedBlockSize;
        }
        this.lineLength = i;
        this.chunkSeparatorLength = chunkSeparatorLength;
    }

    boolean hasData(Context context) {
        return context.buffer != null;
    }

    int available(Context context) {
        return context.buffer != null ? context.pos - context.readPos : 0;
    }

    protected int getDefaultBufferSize() {
        return DEFAULT_BUFFER_SIZE;
    }

    private byte[] resizeBuffer(Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[getDefaultBufferSize()];
            context.pos = 0;
            context.readPos = 0;
        } else {
            byte[] b = new byte[(context.buffer.length * DEFAULT_BUFFER_RESIZE_FACTOR)];
            System.arraycopy(context.buffer, 0, b, 0, context.buffer.length);
            context.buffer = b;
        }
        return context.buffer;
    }

    protected byte[] ensureBufferSize(int size, Context context) {
        if (context.buffer == null || context.buffer.length < context.pos + size) {
            return resizeBuffer(context);
        }
        return context.buffer;
    }

    int readResults(byte[] b, int bPos, int bAvail, Context context) {
        if (context.buffer != null) {
            int len = Math.min(available(context), bAvail);
            System.arraycopy(context.buffer, context.readPos, b, bPos, len);
            context.readPos += len;
            if (context.readPos < context.pos) {
                return len;
            }
            context.buffer = null;
            return len;
        }
        return context.eof ? EOF : 0;
    }

    protected static boolean isWhiteSpace(byte byteToCheck) {
        switch (byteToCheck) {
            case HTTP.HT /*9*/:
            case HTTP.LF /*10*/:
            case HTTP.CR /*13*/:
            case HTTP.SP /*32*/:
                return true;
            default:
                return false;
        }
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
    }

    public String encodeToString(byte[] pArray) {
        return StringUtils.newStringUtf8(encode(pArray));
    }

    public String encodeAsString(byte[] pArray) {
        return StringUtils.newStringUtf8(encode(pArray));
    }

    public Object decode(Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    public byte[] decode(String pArray) {
        return decode(StringUtils.getBytesUtf8(pArray));
    }

    public byte[] decode(byte[] pArray) {
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        Context context = new Context();
        decode(pArray, 0, pArray.length, context);
        decode(pArray, 0, EOF, context);
        byte[] result = new byte[context.pos];
        readResults(result, 0, result.length, context);
        return result;
    }

    public byte[] encode(byte[] pArray) {
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        Context context = new Context();
        encode(pArray, 0, pArray.length, context);
        encode(pArray, 0, EOF, context);
        byte[] buf = new byte[(context.pos - context.readPos)];
        readResults(buf, 0, buf.length, context);
        return buf;
    }

    public boolean isInAlphabet(byte[] arrayOctet, boolean allowWSPad) {
        int i = 0;
        while (i < arrayOctet.length) {
            if (!isInAlphabet(arrayOctet[i]) && (!allowWSPad || (arrayOctet[i] != 61 && !isWhiteSpace(arrayOctet[i])))) {
                return false;
            }
            i++;
        }
        return true;
    }

    public boolean isInAlphabet(String basen) {
        return isInAlphabet(StringUtils.getBytesUtf8(basen), true);
    }

    protected boolean containsAlphabetOrPad(byte[] arrayOctet) {
        if (arrayOctet == null) {
            return false;
        }
        for (byte element : arrayOctet) {
            if (61 == element || isInAlphabet(element)) {
                return true;
            }
        }
        return false;
    }

    public long getEncodedLength(byte[] pArray) {
        long len = ((long) (((pArray.length + this.unencodedBlockSize) + EOF) / this.unencodedBlockSize)) * ((long) this.encodedBlockSize);
        if (this.lineLength > 0) {
            return len + ((((((long) this.lineLength) + len) - 1) / ((long) this.lineLength)) * ((long) this.chunkSeparatorLength));
        }
        return len;
    }
}
