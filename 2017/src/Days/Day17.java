package Days;

import java.util.ArrayList;

public class Day17 implements Day {
    ArrayList<Integer> spinlock;
    int steps;

    @Override
    public void init(String input) {
        steps = Integer.parseInt(input.replace("\n", ""));
    }

    @Override
    public String part1() {
        return "" + iterate(2017);
    }

    @Override
    public String part2() {
        int currentPos = 0;
        int rightToZero = 0;
        for (int len = 1; len <= 50000000; len++) {
            currentPos = (currentPos + steps) % len;
            if (currentPos == 0) {
                rightToZero = len;
            }
            currentPos = (currentPos + 1) % (len + 1);
        }
        return "" + rightToZero;
    }

    public int iterate(int count) {
        spinlock = new ArrayList<>();
        spinlock.add(0);
        int currentPos = 0;
        for (int val = 1; val <= count; val++) {
            currentPos = (currentPos + steps) % spinlock.size();
            spinlock.add((currentPos + 1), val);
            currentPos = (currentPos + 1) % spinlock.size();
        }
        // System.out.println(Arrays.toString(spinlock.toArray()));
        return spinlock.get((currentPos + 1) % spinlock.size());
    }
}
