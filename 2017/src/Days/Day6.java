package Days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Day6 implements Day {
    int[] banks;
    ArrayList<Seen> seen = new ArrayList<>();
    int iterations = 0;

    @Override
    public void init(String input) {
        banks = Arrays.stream(input.replaceAll("(\\t|\\n)", " ").split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    public String part1() {
        while (!seen.contains(new Seen(encodeBank(banks), -1))) {
            iterations++;
            seen.add(new Seen(encodeBank(banks), iterations));
            int maxIndex = 0;
            for (int i = 0; i < banks.length; i++) {
                if (banks[i] > banks[maxIndex]) maxIndex = i;
            }
            int toSpread = banks[maxIndex];
            int currentIndex = (maxIndex + 1) % banks.length;
            banks[maxIndex] = 0;
            while (toSpread > 0) {
                banks[currentIndex++ % banks.length]++;
                toSpread--;
            }
        }
        return "" + iterations;
    }

    @Override
    public String part2() {
        return "" + (Math.abs((seen.get(seen.indexOf(new Seen(encodeBank(banks), -1))).timeStamp - iterations)) + 1);
    }

    String encodeBank(int[] bank) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < bank.length - 1; i++) {
            b.append(bank[i]).append(".");
        }
        return b.append(bank[bank.length - 1]).toString();
    }

    class Seen {
        String encoding;
        int timeStamp;

        Seen(String encoding, int timeStamp) {
            this.encoding = encoding;
            this.timeStamp = timeStamp;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Seen)) return false;
            return ((Seen) o).encoding.equals(this.encoding);
        }

        @Override
        public int hashCode() {
            return Objects.hash(encoding, timeStamp);
        }

        @Override
        public String toString() {
            return "Seen{" +
                    "encoding='" + encoding + '\'' +
                    ", timeStamp=" + timeStamp +
                    '}';
        }
    }
}
