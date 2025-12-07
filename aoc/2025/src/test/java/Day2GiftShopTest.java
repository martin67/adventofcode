import aoc.common.AocFiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("2025: Day 2: Gift Shop")
class Day2GiftShopTest {

    @ParameterizedTest
    @CsvSource({"1227775554, day2-demo1.txt",
            "41294979841, day2.txt"})
    void problem1(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day2GiftShop(inputLines).problem1()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"4174379265, day2-demo1.txt",
            "66500947346, day2.txt"})
    void problem2(long expected, String fileName) throws IOException {
        var inputLines = AocFiles.readAllLines(fileName);
        assertThat(new Day2GiftShop(inputLines).problem2()).isEqualTo(expected);
    }
}