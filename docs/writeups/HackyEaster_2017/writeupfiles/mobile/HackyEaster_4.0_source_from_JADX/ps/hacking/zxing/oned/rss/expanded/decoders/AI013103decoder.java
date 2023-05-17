package ps.hacking.zxing.oned.rss.expanded.decoders;

import ps.hacking.zxing.common.BitArray;

final class AI013103decoder extends AI013x0xDecoder {
    AI013103decoder(BitArray information) {
        super(information);
    }

    protected void addWeightCode(StringBuilder buf, int weight) {
        buf.append("(3103)");
    }

    protected int checkWeight(int weight) {
        return weight;
    }
}
