import java.io.IOException;

public class Day21ChronalConversion {
    private Processor processor;

    public Day21ChronalConversion(String fileName) throws IOException {
        processor = new Processor(6);
        processor.readInstructions(fileName);
    }

    int lowestInteger() {
        processor.printPseudoCode();
        return 0;
    }

}
