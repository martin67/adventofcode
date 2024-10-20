package aoc.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4PassportProcessing {
    final List<Passport> passports = new ArrayList<>();

    Day4PassportProcessing(List<String> inputLines) {
        Map<String, String> fields = new HashMap<>();
        for (String line : inputLines) {
            if (line.isEmpty()) {
                savePassport(fields);
            } else {
                for (String part : line.split(" ")) {
                    String[] keyValues = part.split(":");
                    fields.put(keyValues[0], keyValues[1]);
                }
            }
        }
        savePassport(fields);
    }

    void savePassport(Map<String, String> fields) {
        var passport = new Passport();
        for (String key : fields.keySet()) {
            String value = fields.get(key);
            switch (key) {
                case "byr" -> passport.byr = value;
                case "iyr" -> passport.iyr = value;
                case "eyr" -> passport.eyr = value;
                case "hgt" -> passport.hgt = value;
                case "hcl" -> passport.hcl = value;
                case "ecl" -> passport.ecl = value;
                case "pid" -> passport.pid = value;
                case "cid" -> passport.cid = value;
            }
        }
        passports.add(passport);
        fields.clear();
    }

    long problem1() {
        return passports.stream().filter(Passport::valid).count();
    }

    long problem2() {
        return passports.stream().filter(Passport::presentAndValid).count();
    }

    static class Passport {
        String byr;
        String iyr;
        String eyr;
        String hgt;
        String hcl;
        String ecl;
        String pid;
        String cid;

        boolean valid() {
            return (byr != null && iyr != null && eyr != null && hgt != null && hcl != null && ecl != null && pid != null);
        }

        boolean presentAndValid() {
            if (!valid()) {
                return false;
            }

            if (!byr.matches("\\d{4}") || !iyr.matches("\\d{4}") || !eyr.matches("\\d{4}")) {
                return false;
            }

            if (Integer.parseInt(byr) < 1920 || Integer.parseInt(byr) > 2002 ||
                    Integer.parseInt(iyr) < 2010 || Integer.parseInt(iyr) > 2020 ||
                    Integer.parseInt(eyr) < 2020 || Integer.parseInt(eyr) > 2030) {
                return false;
            }

            if (!hgt.matches("\\d+(in|cm)")) {
                return false;
            }
            int number = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
            String unit = hgt.substring(hgt.length() - 2);
            if ((unit.equals("cm") && (number < 150 || number > 193)) ||
                    (unit.equals("in") && (number < 59 || number > 76))) {
                return false;
            }

            if (!hcl.matches("#[0-9a-f]{6}")) {
                return false;
            }

            if (!ecl.matches("(amb|blu|brn|gry|grn|hzl|oth)")) {
                return false;
            }

            if (!pid.matches("\\d{9}")) {
                return false;
            }

            return true;
        }
    }
}
