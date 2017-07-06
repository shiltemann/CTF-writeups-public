package com.google.zxing.common.reedsolomon;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import org.apache.http.HttpStatus;

public final class GenericGF {
    public static final GenericGF AZTEC_DATA_10;
    public static final GenericGF AZTEC_DATA_12;
    public static final GenericGF AZTEC_DATA_6;
    public static final GenericGF AZTEC_DATA_8;
    public static final GenericGF AZTEC_PARAM;
    public static final GenericGF DATA_MATRIX_FIELD_256;
    private static final int INITIALIZATION_THRESHOLD = 0;
    public static final GenericGF MAXICODE_FIELD_64;
    public static final GenericGF QR_CODE_FIELD_256;
    private int[] expTable;
    private boolean initialized;
    private int[] logTable;
    private GenericGFPoly one;
    private final int primitive;
    private final int size;
    private GenericGFPoly zero;

    static {
        AZTEC_DATA_12 = new GenericGF(4201, AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD);
        AZTEC_DATA_10 = new GenericGF(1033, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        AZTEC_DATA_6 = new GenericGF(67, 64);
        AZTEC_PARAM = new GenericGF(19, 16);
        QR_CODE_FIELD_256 = new GenericGF(285, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
        DATA_MATRIX_FIELD_256 = new GenericGF(HttpStatus.SC_MOVED_PERMANENTLY, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
        AZTEC_DATA_8 = DATA_MATRIX_FIELD_256;
        MAXICODE_FIELD_64 = AZTEC_DATA_6;
    }

    public GenericGF(int primitive, int size) {
        this.initialized = false;
        this.primitive = primitive;
        this.size = size;
        if (size <= 0) {
            initialize();
        }
    }

    private void initialize() {
        int i;
        this.expTable = new int[this.size];
        this.logTable = new int[this.size];
        int x = 1;
        for (i = 0; i < this.size; i++) {
            this.expTable[i] = x;
            x <<= 1;
            if (x >= this.size) {
                x = (x ^ this.primitive) & (this.size - 1);
            }
        }
        for (i = 0; i < this.size - 1; i++) {
            this.logTable[this.expTable[i]] = i;
        }
        this.zero = new GenericGFPoly(this, new int[]{0});
        this.one = new GenericGFPoly(this, new int[]{1});
        this.initialized = true;
    }

    private void checkInit() {
        if (!this.initialized) {
            initialize();
        }
    }

    GenericGFPoly getZero() {
        checkInit();
        return this.zero;
    }

    GenericGFPoly getOne() {
        checkInit();
        return this.one;
    }

    GenericGFPoly buildMonomial(int degree, int coefficient) {
        checkInit();
        if (degree < 0) {
            throw new IllegalArgumentException();
        } else if (coefficient == 0) {
            return this.zero;
        } else {
            int[] coefficients = new int[(degree + 1)];
            coefficients[0] = coefficient;
            return new GenericGFPoly(this, coefficients);
        }
    }

    static int addOrSubtract(int a, int b) {
        return a ^ b;
    }

    int exp(int a) {
        checkInit();
        return this.expTable[a];
    }

    int log(int a) {
        checkInit();
        if (a != 0) {
            return this.logTable[a];
        }
        throw new IllegalArgumentException();
    }

    int inverse(int a) {
        checkInit();
        if (a != 0) {
            return this.expTable[(this.size - this.logTable[a]) - 1];
        }
        throw new ArithmeticException();
    }

    int multiply(int a, int b) {
        checkInit();
        if (a == 0 || b == 0) {
            return 0;
        }
        return this.expTable[(this.logTable[a] + this.logTable[b]) % (this.size - 1)];
    }

    public int getSize() {
        return this.size;
    }
}
