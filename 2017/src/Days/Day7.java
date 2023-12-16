package Days;

import Utils.IO.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Day7 implements Day {
    HashMap<String, Program> programs = new HashMap<>();

    @Override
    public void init(String input) {
        for (String line : input.split("\\n")) {
            String name = line.split(" ")[0];
            if (programs.get(name) == null) {
                programs.put(name, new Program(name));
            }
            programs.get(name).init(line);

            // System.out.println(programs.get(name));
        }
    }

    @Override
    public String part1() {
        for (Program p : programs.values()) {
            if (p.heldBy.size() == 0 && p.holding.length > 0) {
                return p.name;
            }
        }
        return "ERROR";
    }

    @Override
    public String part2() {
        programs.get(part1()).isBalanced();
        System.out.println(programs.get("apjxafk"));
        return null;
    }

    private class Program {
        private final String name;
        int weight;
        Program[] holding;
        ArrayList<Program> heldBy = new ArrayList<>();
        private int balance = -1;

        public Program(String name) {
            this.name = name;
        }

        public void init(String data) {
            weight = Integer.parseInt(StringUtils.find(data, "\\((\\d+)\\)", 1));

            String[] holdingStrings = StringUtils.findAll(data, "(\\w+)(,|$)", 1);

            holding = Arrays.stream(holdingStrings).map(name -> {
                Program pr = programs.get(name);
                if (pr == null) {
                    pr = new Program(name);
                    programs.put(name, pr);
                }
                pr.heldBy.add(this);
                return pr;
            }).toArray(Program[]::new);
        }

        public int calcBalance() {
            if (balance != -1)
                return balance;
            int balance = weight;
            for (Program p : holding) {
                balance += p.calcBalance();
            }
            this.balance = balance;
            return balance;
        }

        public boolean isBalanced() {
            int goal = -1;
            boolean problem = false;
            for (Program p : holding) {
                p.isBalanced();
                if (goal == -1) {
                    goal = p.calcBalance();
                } else if (goal == p.calcBalance()) {
                    // Everything fine
                } else {
                    problem = true;
                }
            }
            if (problem)
                System.out.println(this);
            return problem;
        }

        @Override
        public String toString() {
            return "Program{" +
                    "name='" + name + '\'' +
                    ", weight=" + weight +
                    ", holding="
                    + Arrays.stream(holding).map(pr -> pr.name + "(" + pr.balance + ")")
                            .collect(Collectors.joining(","))
                    +
                    ", heldBy=" + heldBy.stream().map(pr -> pr.name).collect(Collectors.joining(",")) +
                    '}';
        }
    }
}
