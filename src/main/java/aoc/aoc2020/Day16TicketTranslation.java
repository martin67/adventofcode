package aoc.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16TicketTranslation {
    List<TicketField> ticketFields = new ArrayList<>();
    List<Ticket> tickets = new ArrayList<>();
    Ticket myTicket;

    public Day16TicketTranslation(List<String> inputLines) {
        Pattern fieldPattern = Pattern.compile("^(.*): (\\d+)-(\\d+) or (\\d+)-(\\d+)$");
        Pattern ticketPattern = Pattern.compile("^((\\d+)(,\\d+)*)$");
        Matcher matcher;

        for (String line : inputLines) {
            matcher = fieldPattern.matcher(line);
            if (matcher.find()) {
                TicketField ticketField = new TicketField(matcher.group(1));
                for (int i = 0; i < 2; i++) {
                    List<Integer> interval = new ArrayList<>();
                    interval.add(Integer.parseInt(matcher.group((i * 2) + 2)));
                    interval.add(Integer.parseInt(matcher.group((i * 2) + 3)));
                    ticketField.intervals.add(interval);
                }
                ticketFields.add(ticketField);
            }
            matcher = ticketPattern.matcher(line);
            if (matcher.find()) {
                String s = matcher.group(1);
                List<Integer> numbers = new ArrayList<>();
                for (String s2 : s.split(",")) {
                    numbers.add(Integer.parseInt(s2));
                }
                if (myTicket == null) {
                    myTicket = new Ticket(numbers);
                } else {
                    tickets.add(new Ticket(numbers));
                }
            }
        }
    }

    int problem1() {
        int ticketErrorSum = 0;
        for (Ticket ticket : tickets) {
            for (int number : ticket.numbers) {
                boolean numberIsOk = false;
                for (TicketField ticketField : ticketFields) {
                    if (ticketField.validNumber(number)) {
                        numberIsOk = true;
                        break;
                    }
                }
                if (!numberIsOk) {
                    ticketErrorSum += number;
                }
            }
        }
        return ticketErrorSum;
    }

    long problem2() {
        List<Ticket> validTickets = new ArrayList<>();

        for (Ticket ticket : tickets) {
            boolean allNumbersOk = true;
            for (int number : ticket.numbers) {
                boolean numberIsOk = false;
                for (TicketField ticketField : ticketFields) {
                    if (ticketField.validNumber(number)) {
                        numberIsOk = true;
                        break;
                    }
                }
                if (!numberIsOk) {
                    allNumbersOk = false;
                }
            }
            if (allNumbersOk) {
                validTickets.add(ticket);
            }
        }

        // Map to positions
        List<List<Integer>> positions = new ArrayList<>();
        for (int i = 0; i < ticketFields.size(); i++) {
            positions.add(new ArrayList<>());
        }
        for (Ticket ticket : validTickets) {
            int i = 0;
            for (int number : ticket.numbers) {
                positions.get(i).add(number);
                i++;
            }
        }


        Map<String, Integer> result = new HashMap<>();
        List<List<Integer>> positionsInitialCopy = new ArrayList<>(positions);
        boolean done = false;

        while (!done) {

            TicketField finalTicketfield = null;
            List<Integer> finalPosition = null;

            // Loopa ett varv på positionerna
            for (List<Integer> position : positions) {
                // Kolla om det är någon position som bara kan vara på ett ticketfield. dvs
                // det finns bara ett ticketfield som är ok för alla numbers i position.
                List<List<Integer>> positionsWithAllNumbers = new ArrayList<>();
                TicketField fullTicketField = null;
                for (TicketField ticketField : ticketFields) {
                    int numberOfOkNumbers = 0;
                    for (int number : position) {
                        if (ticketField.validNumber(number)) {
                            numberOfOkNumbers++;
                        }
                    }
                    if (numberOfOkNumbers == position.size()) {
                        positionsWithAllNumbers.add(position);
                        fullTicketField = ticketField;
                    }
                }
                if (positionsWithAllNumbers.size() == 1) {
                    finalTicketfield = fullTicketField;
                    finalPosition = position;
                }
            }

            result.put(finalTicketfield.name, positionsInitialCopy.indexOf(finalPosition));

            // Ta bort de upphittade posterna
            ticketFields.remove(finalTicketfield);
            positions.remove(finalPosition);

            if (positions.size() == 0) {
                done = true;
            }
        }

        // Kolla nu mot ursprungsvärdena
        long number = 1;
        for (String name : result.keySet()) {
            if (name.matches("departure.*")) {
                int index = result.get(name);
                number *= myTicket.numbers.get(index);
            }
        }
        return number;
    }

    static class Ticket {
        List<Integer> numbers;

        public Ticket(List<Integer> numbers) {
            this.numbers = numbers;
        }
    }

    static class TicketField {
        String name;
        List<List<Integer>> intervals = new ArrayList<>();

        public TicketField(String name) {
            this.name = name;
        }

        boolean validNumber(int number) {
            for (List<Integer> interval : intervals) {
                if (number >= interval.get(0) && number <= interval.get(1)) {
                    return true;
                }
            }
            return false;
        }
    }
}
