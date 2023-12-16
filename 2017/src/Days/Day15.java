package Days;

public class Day15 implements Day {
    String input;
    long valA, valB;
    long multA = 16807, multB = 48271;
    long mod = 2147483647;
    int million = 1000000;

    @Override
    public void init(String input) {
        this.input = input;
        String[] lines = input.split("\n");
        valA = Long.parseLong(lines[0]);
        valB = Long.parseLong(lines[1]);
    }

    @Override
    public String part1() {
        int matches = 0;
        for (int i = 0; i < 40 * million; i++) {
            calcNextValues();
            if (valA % Math.pow(2, 16) == valB % Math.pow(2, 16)) {
                matches++;
            }
        }
        return "" + matches;
    }

    @Override
    public String part2() {
        init(this.input);
        int matches = 0;
        for (int i = 0; i < 5 * million; i++) {
            do {
                valA *= multA;
                valA %= mod;
            } while (valA % 4 != 0);

            do {
                valB *= multB;
                valB %= mod;
            } while (valB % 8 != 0);

            if (valA % Math.pow(2, 16) == valB % Math.pow(2, 16)) {
                matches++;
            }
        }
        return "" + matches;
    }

    public void calcNextValues() {
        valA *= multA;
        valA %= mod;
        valB *= multB;
        valB %= mod;
    }
}
