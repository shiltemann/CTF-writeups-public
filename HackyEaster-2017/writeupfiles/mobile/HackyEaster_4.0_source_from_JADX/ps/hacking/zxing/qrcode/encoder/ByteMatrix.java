package ps.hacking.zxing.qrcode.encoder;

import android.support.v4.app.NotificationCompat.WearableExtender;
import java.lang.reflect.Array;

public final class ByteMatrix {
    private final byte[][] bytes;
    private final int height;
    private final int width;

    public ByteMatrix(int width, int height) {
        this.bytes = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{height, width});
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public byte get(int x, int y) {
        return this.bytes[y][x];
    }

    public byte[][] getArray() {
        return this.bytes;
    }

    public void set(int x, int y, byte value) {
        this.bytes[y][x] = value;
    }

    public void set(int x, int y, int value) {
        this.bytes[y][x] = (byte) value;
    }

    public void set(int x, int y, boolean value) {
        this.bytes[y][x] = (byte) (value ? 1 : 0);
    }

    public void clear(byte value) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.bytes[y][x] = value;
            }
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder(((this.width * 2) * this.height) + 2);
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                switch (this.bytes[y][x]) {
                    case WearableExtender.SIZE_DEFAULT /*0*/:
                        result.append(" 0");
                        break;
                    case WearableExtender.SIZE_XSMALL /*1*/:
                        result.append(" 1");
                        break;
                    default:
                        result.append("  ");
                        break;
                }
            }
            result.append('\n');
        }
        return result.toString();
    }
}
