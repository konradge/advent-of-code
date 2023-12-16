package Days;

import java.util.HashMap;

public class Day8 implements Day {
    RegisterMachine machine;

    @Override
    public void init(String input) {
        machine = new RegisterMachine(input);
    }

    @Override
    public String part1() {
        machine.run();
        int max = Integer.MIN_VALUE;
        for (int val : machine.register.values()) {
            if (val > max) {
                max = val;
            }
        }
        return "" + max;
    }

    @Override
    public String part2() {
        return machine.allTimeMax + "";
    }
}

class RegisterMachine {
    public HashMap<String, Integer> register = new HashMap<>();
    final String[] instructions;
    public int allTimeMax = Integer.MIN_VALUE;

    public RegisterMachine(String instructions) {
        this.instructions = instructions.split("\\n");
    }

    public void run() {
        for (String instr : instructions) {
            String[] instruction = instr.split(" ");
            String reg = instruction[0];
            String command = instruction[1];
            int number = Integer.parseInt(instruction[2]);
            String condition = instr.split(" if ")[1];
            if (evaluateCondition(condition)) {
                switch (command) {
                    case "dec" -> register.put(reg, register.getOrDefault(reg, 0) - number);
                    case "inc" -> register.put(reg, register.getOrDefault(reg, 0) + number);
                }
                if (register.get(reg) > allTimeMax) {
                    allTimeMax = register.get(reg);
                }
            }
        }
    }

    private boolean evaluateCondition(String condition) {
        String reg = condition.split(" ")[0];
        String comparison = condition.split(" ")[1];
        int value = Integer.parseInt(condition.split(" ")[2]);
        int regValue = register.getOrDefault(reg, 0);
        return switch (comparison) {
            case ">" -> regValue > value;
            case ">=" -> regValue >= value;
            case "<" -> regValue < value;
            case "<=" -> regValue <= value;
            case "==" -> regValue == value;
            case "!=" -> regValue != value;
            default -> false;
        };
    }
}