package Days;

import Utils.IO.ArrayUtils;

import java.util.*;
import java.util.stream.IntStream;

public class Day10 implements Day {
    private static int currentPosition = 0;
    private static int skipSize = 0;
    private String input;

    @Override
    public void init(String input) {

        this.input = input.replaceAll("\\n", "");
    }

    @Override
    public String part1() {
        int[] lengths = Arrays.stream(input.replaceAll("\\n", "").split(",")).mapToInt(Integer::parseInt).toArray();
        int[] list = IntStream.range(0, 256).toArray();
        list = runRound(lengths, list);
        return "" + (list[0] * list[1]);
    }

    @Override
    public String part2() {
        return calcKnotHash(this.input);
    }

    public static String getHash(int[] list) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int hash = list[16 * i];
            for (int j = 1; j < 16; j++) {
                hash = hash ^ list[16 * i + j];
            }

            String hex = Integer.toHexString(hash);
            if (hex.length() == 1) hex = "0" + hex;
            res.append(hex);
        }
        return res.toString();
    }

    public static int[] runRound(int[] lengths, int[] list) {
        for (int len : lengths) {
            int[] newList = Arrays.copyOf(list, list.length);
            for (int i = 0; i < len; i++) {
                newList[(currentPosition + len - 1 - i) % list.length] = list[(currentPosition + i) % list.length];
            }
            currentPosition += (len + skipSize);
            list = newList;
            skipSize++;
        }
        return list;
    }

    public static String calcKnotHash(String input) {
        currentPosition = 0;
        skipSize = 0;
        int[] lengths = ArrayUtils.concat(Arrays.stream(input.split("")).mapToInt(x -> (int) x.charAt(0)).toArray(), new int[]{17, 31, 73, 47, 23});
        int[] list = IntStream.range(0, 256).toArray();

        for (int i = 0; i < 64; i++) {
            list = runRound(lengths, list);
        }

        return getHash(list);
    }
}
