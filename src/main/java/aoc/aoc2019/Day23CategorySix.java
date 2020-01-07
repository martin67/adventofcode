package aoc.aoc2019;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day23CategorySix {

    @Data
    class Nat implements Callable<Integer> {
        ExecutorService executorService;
        BlockingQueue<BigInteger> inputQueue;
        BlockingQueue<BigInteger> outputQueue;
        BigInteger x;
        BigInteger y;

        public Nat(ExecutorService executorService) {
            this.executorService = executorService;
            this.inputQueue = new LinkedBlockingDeque<>();
        }

        @Override
        public Integer call() throws Exception {
            log.info("Starting nat");
            Runnable input = () -> {
                Set<BigInteger> yValues = new HashSet<>();
                log.info("Starting nat listener");
                while (true) {
                    try {
                        x = inputQueue.take();
                        y = inputQueue.take();
                        log.info("Nat received {} {}", x, y);
                        if (yValues.contains(y)) {
                            log.info("Found second y value: {}", y.intValue());
                            return;
                        } else {
                            yValues.add(y);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.submit(input);

            log.info("Starting nat idle checker");
            while (true) {
                Thread.sleep(1000);
                log.info("Checking queues");
                boolean queuesEmpty = true;
                for (int i = 0; i < 50; i++) {
                    if (!computers.get(i).getInputQueue().isEmpty()) {
                        //log.info("queue nr {} is not empty. contains {} items", i, )
                        queuesEmpty = false;
                        break;
                    }
                }
                log.info("queues empty: {}", queuesEmpty);
                if (queuesEmpty) {
                    computers.get(0).getInputQueue().put(x);
                    computers.get(0).getInputQueue().put(y);
                    log.info("computer 0 queue size; {}", computers.get(0).getInputQueue().size());
                }
            }
        }
    }

    @Data
    class Router implements Callable<Integer> {
        BlockingQueue<BigInteger> inputQueue;
        BlockingQueue<BigInteger> outputQueue;
        Nat nat;
        int id;

        public Router(int id, Nat nat) {
            this.id = id;
            this.nat = nat;
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
                    if (nat == null) {
                        log.info("Found it: {}", y);
                        return y.intValue();
                    } else {
                        log.info("Router {}: sending {},{} to nat", id, x, y);
                        nat.getInputQueue().put(x);
                        nat.getInputQueue().put(y);
                    }
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

    int yValue() throws InterruptedException, ExecutionException {
        log.info("Starting...");
        for (int i = 0; i < 50; i++) {
            IntcodeComputer ic = new IntcodeComputer(new ArrayList<>(opcodes));
            Router router = new Router(i, null);
            ic.getInputQueue().add(new BigInteger(String.valueOf(i)));
            router.setInputQueue(ic.getOutputQueue());
            computers.add(ic);
            routers.add(router);
            executorService.submit(ic);
            ic.getInputQueue().add(new BigInteger("-1"));
        }

        Integer result = executorService.invokeAny(routers);
        log.info("Done");
        return result;
    }

    int secondYValue() throws ExecutionException, InterruptedException {
        log.info("Starting...");
        Nat nat = new Nat(executorService);
        executorService.submit(nat);
        for (int i = 0; i < 50; i++) {
            IntcodeComputer ic = new IntcodeComputer(new ArrayList<>(opcodes));
            Router router = new Router(i, nat);
            ic.getInputQueue().add(new BigInteger(String.valueOf(i)));
            router.setInputQueue(ic.getOutputQueue());
            computers.add(ic);
            routers.add(router);
            executorService.submit(ic);
            ic.getInputQueue().add(new BigInteger("-1"));
        }

        Integer result = executorService.invokeAny(routers);
        log.info("Done");
        return result;
    }
}
