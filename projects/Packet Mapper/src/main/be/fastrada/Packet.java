package be.fastrada;

import java.math.BigInteger;

public class Packet {

    private String content;

    public Packet(String packetString) {
        content = packetString.replace(" ", "");
    }

    @Override
    public String toString() {
        return  content;
    }

    public BigInteger toBigInt() {
        return new BigInteger(content, 16);
    }

    public BigInteger applyMask(BigInteger mask) {
        BigInteger tempResult = toBigInt();
        BigInteger result = tempResult.and(mask);
        return result.shiftRight(32);
    }
}
