package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.List;

@Slf4j
public class Day25ComboBreaker {
    int cardPublicKey;
    int doorPublicKey;

    public Day25ComboBreaker(List<String> inputLines) {
        cardPublicKey = Integer.parseInt(inputLines.get(0));
        doorPublicKey = Integer.parseInt(inputLines.get(1));
    }

    public static BigInteger lcm(BigInteger a, BigInteger b) {
        if (a.signum() == 0 || b.signum() == 0)
            return BigInteger.ZERO;
        return a.divide(a.gcd(b)).multiply(b).abs();
    }

    int problem1() {
        int cardLoopSize = transform(7, cardPublicKey);
        int doorLoopSize = transform(7, doorPublicKey);

        log.info("Card public key: {}, loop size: {}", cardPublicKey, cardLoopSize);
        log.info("Door public key: {}, loop size: {}", doorPublicKey, doorLoopSize);

        BigInteger na = key(cardPublicKey, cardLoopSize);
        BigInteger nb = key(doorPublicKey, doorLoopSize);
        BigInteger prime = new BigInteger("20201227");
        log.info("N^A: {}", na);
        log.info("N^B: {}", nb);
        log.info("J: {}", na.mod(prime));
        log.info("K: {}", nb.mod(prime));
        log.info("LCM(A,B): {}", lcm(new BigInteger(Integer.toString(cardPublicKey)), new BigInteger(Integer.toString(doorPublicKey))));
        log.info("LCM(J,K): {}", lcm(na.mod(prime), nb.mod(prime)));


        //int key = encryptionKey(cardPublicKey, doorLoopSize);
        int key = encryptionKey2(doorPublicKey, cardLoopSize);
        return key;
    }

    BigInteger key(int n, int e) {
        return new BigInteger(Integer.toString(n)).pow(e);
    }

    int transform(int subjectNumber, int key) {
        int value = 1;
        int loopSize = 0;
        while (value != key) {
            value = value * subjectNumber;
            value = value % 20201227;
            loopSize++;
        }
        return loopSize;
    }

    int encryptionKey(int publicKey, int loopNumber) {
        long key = 1;
        for (int i = 0; i < loopNumber; i++) {
            key = key * publicKey;
            key = key % 20201227;
        }
        return (int) key;
    }

    int encryptionKey2(int publicKey, int loopNumber) {
        double key = Math.pow(publicKey % 20201227, loopNumber) % 20201227D;
        return (int) key;
    }

}
