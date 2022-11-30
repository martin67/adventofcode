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
                int previousY = 0;
                log.info("Starting nat listener");
                while (true) {
                    try {
                        x = inputQueue.take();
                        y = inputQueue.take();
                        //log.info("Nat received {} {}", x, y);
                        if (y.intValue() == previousY) {
                            log.info("*******************' Found second y value: {}", y.intValue());
                            return;
                        } else {
                            previousY = y.intValue();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.submit(input);

            log.info("Starting nat idle checker");
            while (true) {
                Thread.sleep(500);
//                log.info("Checking queues");
                boolean queuesEmpty = true;
                for (int i = 0; i < 50; i++) {
                    if (computers.get(i).getInputQueue().isEmpty()) {
                        computers.get(i).getInputQueue().put(new BigInteger("-1"));
                    } else {
                        //log.info("queue nr {} is not empty. contains {} items", i, )
                        queuesEmpty = false;
                    }
                }
                //log.info("queues empty: {}", queuesEmpty);
                if (queuesEmpty && x != null && y != null) {
                    log.info("Sending {},{} to computer 0", x, y);
                    computers.get(0).getInputQueue().put(x);
                    computers.get(0).getInputQueue().put(y);
                    //log.info("computer 0 queue size; {}", computers.get(0).getInputQueue().size());
                    x = null;
                    y = null;
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
                } else {
                    computers.get(destination).getInputQueue().put(x);
                    computers.get(destination).getInputQueue().put(y);
                }
            }
        }
    }

    @Data
    static
    class NatRouter implements Callable<Integer> {
        ExecutorService executorService;
        List<BlockingQueue<BigInteger>> inputQueues = new ArrayList<>();
        List<IntcodeComputer> computers;

        public NatRouter(ExecutorService executorService, List<IntcodeComputer> computers) {
            this.computers = computers;
            for (IntcodeComputer ic : computers) {
                BlockingQueue<BigInteger> bq = new LinkedBlockingQueue<>();
                inputQueues.add(bq);
                ic.setOutputQueue(bq);
            }
            for (IntcodeComputer ic : computers) {
                executorService.submit(ic);
            }
        }

        @Override
        public Integer call() throws Exception {
            log.info("Starting NatRouter");
            BigInteger natX = null;
            BigInteger natY = null;
            BigInteger previousValue = new BigInteger("0");
            boolean idle;

            while (true) {
                idle = true;
                for (int i = 0; i < 50; i++) {
                    BlockingQueue<BigInteger> inputQueue = inputQueues.get(i);
                    if (inputQueue.isEmpty()) {
                        computers.get(i).getInputQueue().put(new BigInteger("-1"));
                    } else if (inputQueue.size() >= 3) {
                        idle = false;
                        int destination = inputQueue.take().intValue();
                        BigInteger x = inputQueue.take();
                        BigInteger y = inputQueue.take();
                        log.debug("Router: Routing from {} values {},{} to {}", i, x, y, destination);
                        if (destination == 255) {
                            log.debug("Router: Routing from {} values {},{} to NAT", i, x, y);
                            natX = x;
                            natY = y;
                        } else {
                            computers.get(destination).getInputQueue().put(x);
                            computers.get(destination).getInputQueue().put(y);
                        }
                    } else {
                        idle = false;
                    }

                    Thread.sleep(1);
                    if (idle) {
                        //log.info("idle");
                        if (natY != null && natY.intValue() != -1) {
                            log.debug("Router: Routing from NAT values {},{} to 0", natX, natY);
                            if (natY.equals(previousValue)) {
                                log.info("found it: {}", natY);
                                return natY.intValue();
                            } else {
                                previousValue = natY;
                            }

                            computers.get(0).getInputQueue().put(natX);
                            computers.get(0).getInputQueue().put(natY);
                            natX = null;
                            natY = null;
                        }
                    }
                }
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

    int repeatedYValue() {
        log.info("Starting...");
        for (int i = 0; i < 50; i++) {
            IntcodeComputer ic = new IntcodeComputer(new ArrayList<>(opcodes));
            ic.getInputQueue().add(new BigInteger(String.valueOf(i)));
            computers.add(ic);
        }

        NatRouter natRouter = new NatRouter(executorService, computers);
        Future<Integer> future = executorService.submit(natRouter);
        Integer result = null;

        log.info("Done");
        try {
            result = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

