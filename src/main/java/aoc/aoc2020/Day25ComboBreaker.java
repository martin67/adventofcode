package aoc.aoc2020;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Day25ComboBreaker {
    private final int cardPublicKey;
    private final int doorPublicKey;

    public Day25ComboBreaker(List<String> inputLines) {
        cardPublicKey = Integer.parseInt(inputLines.get(0));
        doorPublicKey = Integer.parseInt(inputLines.get(1));
    }

    public int problem1() {
        int cardLoopSize = transform(7, cardPublicKey);
        int doorLoopSize = transform(7, doorPublicKey);

        log.info("Card public key: {}, loop size: {}", cardPublicKey, cardLoopSize);
        log.info("Door public key: {}, loop size: {}", doorPublicKey, doorLoopSize);

        int cardEncryptionKey = encryptionKey(doorPublicKey, cardLoopSize);
        int doorEncryptionKey = encryptionKey(cardPublicKey, doorLoopSize);

        log.info("Card encryption key: {}", cardEncryptionKey);
        log.info("Door encryption key: {}", doorEncryptionKey);

        return cardEncryptionKey;
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

}
