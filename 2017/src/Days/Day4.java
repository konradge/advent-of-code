package Days;

import java.util.Arrays;

public class Day4 implements Day {
    String[] passwords;

    @Override
    public void init(String input) {
        passwords = input.split("\n");
    }

    @Override
    public String part1() {
        int valid = 0;
        passwords: for (String pwd : passwords) {
            String[] words = pwd.split(" ");
            for (int i = 0; i < words.length - 1; i++) {
                for (int j = i + 1; j < words.length; j++) {
                    if (words[i].equals(words[j])) {
                        continue passwords;
                    }
                }
            }
            valid++;
        }
        return "" + valid;
    }

    @Override
    public String part2() {
        int valid = 0;
        passwords: for (String pwd : passwords) {
            String[] words = pwd.split(" ");
            for (int i = 0; i < words.length - 1; i++) {
                for (int j = i + 1; j < words.length; j++) {
                    String[] pass1 = words[i].split("");
                    Arrays.sort(pass1);
                    String[] pass2 = words[j].split("");
                    Arrays.sort(pass2);
                    if (String.join("", pass1).equals(String.join("", pass2))) {
                        continue passwords;
                    }
                }
            }
            valid++;
        }
        return "" + valid;
    }
}
