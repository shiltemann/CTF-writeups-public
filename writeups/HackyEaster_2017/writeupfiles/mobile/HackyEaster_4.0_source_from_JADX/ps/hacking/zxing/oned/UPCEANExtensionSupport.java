package ps.hacking.zxing.oned;

import ps.hacking.zxing.NotFoundException;
import ps.hacking.zxing.ReaderException;
import ps.hacking.zxing.Result;
import ps.hacking.zxing.common.BitArray;

final class UPCEANExtensionSupport {
    private static final int[] EXTENSION_START_PATTERN;
    private final UPCEANExtension5Support fiveSupport;
    private final UPCEANExtension2Support twoSupport;

    UPCEANExtensionSupport() {
        this.twoSupport = new UPCEANExtension2Support();
        this.fiveSupport = new UPCEANExtension5Support();
    }

    static {
        EXTENSION_START_PATTERN = new int[]{1, 1, 2};
    }

    Result decodeRow(int rowNumber, BitArray row, int rowOffset) throws NotFoundException {
        int[] extensionStartRange = UPCEANReader.findGuardPattern(row, rowOffset, false, EXTENSION_START_PATTERN);
        try {
            return this.fiveSupport.decodeRow(rowNumber, row, extensionStartRange);
        } catch (ReaderException e) {
            return this.twoSupport.decodeRow(rowNumber, row, extensionStartRange);
        }
    }
}
