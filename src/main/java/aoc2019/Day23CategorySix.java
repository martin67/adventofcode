package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day23CategorySix {

    @Data
    class Router implements Callable<Integer> {
        BlockingQueue<BigInteger> inputQueue;
        BlockingQueue<BigInteger> outputQueue;
        int id;

        public Router(int id) {
            this.id = id;
        }

        @Override
        public Integer call() throws Exception {
            log.info("Starting router {}", id);
            while (true) {
                int destination = inputQueue.take().intValue();
                BigInteger x = inputQueue.take();
                BigInteger y = inputQueue.take();
                log.info("Router {}: Routing {},{} to {}", id, x.intValue(), y.intValue(), destination);
                computers.get(destination).getInputQueue().put(x);
                computers.get(destination).getInputQueue().put(y);
            }
        }
    }

    ExecutorService executorService;
    private final List<String> opcodes;
    List<IntcodeComputer> computers = new ArrayList<>();
    List<Router> routers = new ArrayList<>();

    public Day23CategorySix(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
    }

    int yValue() throws InterruptedException {
        log.info("Starting...");
        for (int i = 0; i < 50; i++) {
            IntcodeComputer ic = new IntcodeComputer(new ArrayList<>(opcodes));
            Router router = new Router(i);
            ic.getInputQueue().add(new BigInteger(String.valueOf(i)));
            router.setInputQueue(ic.getOutputQueue());
            computers.add(ic);
            routers.add(router);
            executorService.submit(router);
            executorService.submit(ic);
        }




        //executorService.invokeAll(routers);
        //executorService.invokeAll(computers);

        log.info("Done");
        return 0;
    }
}
