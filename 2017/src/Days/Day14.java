package Days;

import java.math.BigInteger;
import java.util.Arrays;

public class Day14 implements Day {
    String[] hashInputs = new String[128];
    int[][] grid = new int[128][128];

    @Override
    public void init(String input) {
        Arrays.setAll(hashInputs, i -> input.replaceAll("\\n", "") + "-" + i);
    }

    @Override
    public String part1() {
        int usedCount = 0;
        for (int i = 0; i < hashInputs.length; i++) {
            String hash = Day10.calcKnotHash(hashInputs[i]);
            String binary = new BigInteger(hash, 16).toString(2);
            usedCount += binary.length() - binary.replaceAll("1", "").length();
            for (int j = 0; j < binary.length(); j++) {
                grid[i][j] = (binary.charAt(j) == '0' ? 0 : -1);
            }
        }
        return usedCount + "";
    }

    @Override
    public String part2() {
        int groupNumber = 1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == -1) {
                    if (i > 0 && grid[i - 1][j] > 0) {
                        grid[i][j] = grid[i - 1][j];
                    } else if (j > 0 && grid[i][j - 1] > 0) {
                        grid[i][j] = grid[i][j - 1];
                    } else {
                        grid[i][j] = groupNumber++;
                    }
                }
            }
        }
        return groupNumber + "";
    }
}
