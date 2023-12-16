package Days;

import java.util.Arrays;

public class Day1 implements Day {

    private int[] input;

    @Override
    public void init(String input) {
        this.input = Arrays.stream(input.replaceAll("\\n", "").split("")).mapToInt(Integer::parseInt).toArray();
    }

    @Override
    public String part1() {
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == input[(i + 1) % input.length]) {
                sum += input[i];
            }
        }
        return sum + "";
    }

    @Override
    public String part2() {
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == input[(i + input.length / 2) % input.length]) {
                sum += input[i];
            }
        }
        return sum + "";
    }
}
