package com.google.zxing.oned.rss;

public final class RSSUtils {
    private RSSUtils() {
    }

    static int[] getRSSwidths(int val, int n, int elements, int maxWidth, boolean noNarrow) {
        int[] widths = new int[elements];
        int narrowMask = 0;
        int bar = 0;
        while (bar < elements - 1) {
            int subVal;
            narrowMask |= 1 << bar;
            int elmWidth = 1;
            while (true) {
                subVal = combins((n - elmWidth) - 1, (elements - bar) - 2);
                if (noNarrow && narrowMask == 0 && (n - elmWidth) - ((elements - bar) - 1) >= (elements - bar) - 1) {
                    subVal -= combins((n - elmWidth) - (elements - bar), (elements - bar) - 2);
                }
                if ((elements - bar) - 1 > 1) {
                    int lessVal = 0;
                    for (int mxwElement = (n - elmWidth) - ((elements - bar) - 2); mxwElement > maxWidth; mxwElement--) {
                        lessVal += combins(((n - elmWidth) - mxwElement) - 1, (elements - bar) - 3);
                    }
                    subVal -= ((elements - 1) - bar) * lessVal;
                } else if (n - elmWidth > maxWidth) {
                    subVal--;
                }
                val -= subVal;
                if (val < 0) {
                    break;
                }
                elmWidth++;
                narrowMask &= (1 << bar) ^ -1;
            }
            val += subVal;
            n -= elmWidth;
            widths[bar] = elmWidth;
            bar++;
        }
        widths[bar] = n;
        return widths;
    }

    public static int getRSSvalue(int[] widths, int maxWidth, boolean noNarrow) {
        int elements = widths.length;
        int n = 0;
        for (int width : widths) {
            n += width;
        }
        int val = 0;
        int narrowMask = 0;
        int bar = 0;
        while (bar < elements - 1) {
            int elmWidth = 1;
            narrowMask |= 1 << bar;
            while (elmWidth < widths[bar]) {
                int subVal = combins((n - elmWidth) - 1, (elements - bar) - 2);
                if (noNarrow && narrowMask == 0 && (n - elmWidth) - ((elements - bar) - 1) >= (elements - bar) - 1) {
                    subVal -= combins((n - elmWidth) - (elements - bar), (elements - bar) - 2);
                }
                if ((elements - bar) - 1 > 1) {
                    int lessVal = 0;
                    for (int mxwElement = (n - elmWidth) - ((elements - bar) - 2); mxwElement > maxWidth; mxwElement--) {
                        lessVal += combins(((n - elmWidth) - mxwElement) - 1, (elements - bar) - 3);
                    }
                    subVal -= ((elements - 1) - bar) * lessVal;
                } else if (n - elmWidth > maxWidth) {
                    subVal--;
                }
                val += subVal;
                elmWidth++;
                narrowMask &= (1 << bar) ^ -1;
            }
            n -= elmWidth;
            bar++;
        }
        return val;
    }

    private static int combins(int n, int r) {
        int minDenom;
        int maxDenom;
        if (n - r > r) {
            minDenom = r;
            maxDenom = n - r;
        } else {
            minDenom = n - r;
            maxDenom = r;
        }
        int val = 1;
        int j = 1;
        for (int i = n; i > maxDenom; i--) {
            val *= i;
            if (j <= minDenom) {
                val /= j;
                j++;
            }
        }
        while (j <= minDenom) {
            val /= j;
            j++;
        }
        return val;
    }

    static int[] elements(int[] eDist, int N, int K) {
        int i;
        int[] widths = new int[(eDist.length + 2)];
        int twoK = K << 1;
        widths[0] = 1;
        int minEven = 10;
        int barSum = 1;
        for (i = 1; i < twoK - 2; i += 2) {
            widths[i] = eDist[i - 1] - widths[i - 1];
            widths[i + 1] = eDist[i] - widths[i];
            barSum += widths[i] + widths[i + 1];
            if (widths[i] < minEven) {
                minEven = widths[i];
            }
        }
        widths[twoK - 1] = N - barSum;
        if (widths[twoK - 1] < minEven) {
            minEven = widths[twoK - 1];
        }
        if (minEven > 1) {
            for (i = 0; i < twoK; i += 2) {
                widths[i] = widths[i] + (minEven - 1);
                int i2 = i + 1;
                widths[i2] = widths[i2] - (minEven - 1);
            }
        }
        return widths;
    }
}
