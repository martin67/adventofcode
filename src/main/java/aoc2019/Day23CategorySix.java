package aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
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
                if (destination == 255) {
                    log.info("Found it: {}", y);
                    return y.intValue();
                }
                computers.get(destination).getInputQueue().put(x);
                computers.get(destination).getInputQueue().put(y);
            }
        }
    }

    final ExecutorService executorService;
    private final List<String> opcodes;
    final List<IntcodeComputer> computers = new ArrayList<>();
    final List<Router> routers = new ArrayList<>();

    public Day23CategorySix(List<String> inputLines) {
        executorService = Executors.newCachedThreadPool();
        opcodes = Stream.of(inputLines.get(0).split(","))
                .collect(Collectors.toList());
    }

    int yValue() {
        log.info("Starting...");
        List<Future<Integer>> futureSums = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            IntcodeComputer ic = new IntcodeComputer(new ArrayList<>(opcodes));
            Router router = new Router(i);
            ic.getInputQueue().add(new BigInteger(String.valueOf(i)));
            router.setInputQueue(ic.getOutputQueue());
            computers.add(ic);
            routers.add(router);
            //executorService.submit(router);
            futureSums.add(executorService.submit(router));

            executorService.submit(ic);
            ic.getInputQueue().add(new BigInteger("-1"));

        }

        //executorService.invokeAll(routers);
        //executorService.invokeAll(computers);
        //Future<Integer> futureSum = executorService.;


        log.info("Done");
        return 0;
    }

    int secondYValue() {
        log.info("Starting...");
        List<Future<Integer>> futureSums = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            IntcodeComputer ic = new IntcodeComputer(new ArrayList<>(opcodes));
            Router router = new Router(i);
            ic.getInputQueue().add(new BigInteger(String.valueOf(i)));
            router.setInputQueue(ic.getOutputQueue());
            computers.add(ic);
            routers.add(router);
            //executorService.submit(router);
            futureSums.add(executorService.submit(router));

            executorService.submit(ic);
            ic.getInputQueue().add(new BigInteger("-1"));

        }

        //executorService.invokeAll(routers);
        //executorService.invokeAll(computers);
        //Future<Integer> futureSum = executorService.;


        log.info("Done");
        return 0;
    }
}
