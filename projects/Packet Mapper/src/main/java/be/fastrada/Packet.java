package be.fastrada;

import java.math.BigInteger;

public class Packet {

    private String hexContent;

    public Packet(String packetString) {
        hexContent = packetString.replace(" ", "");
    }

    @Override
    public String toString() {
        return hexContent;
    }

    public BigInteger toBigInt() {
        return new BigInteger(hexContent, 16);
    }

    public BigInteger applyMask(BigInteger mask) {
        BigInteger tempResult = toBigInt();
        BigInteger result = tempResult.and(mask);
        return result.shiftRight(32);
    }

    public int readId() {

        return 0;
    }

    public String getHexString() {
        return hexContent;
    }
}
