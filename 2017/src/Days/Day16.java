package Days;

import java.util.ArrayList;
import java.util.Arrays;

public class Day16 implements Day {
    Move[] moves;
    ArrayList<String> config = new ArrayList<>();

    @Override
    public void init(String input) {
        moves = Arrays.stream(input.replace("\n", "").split(",")).map(Move::new).toArray(Move[]::new);
        for (char i = 'a'; i <= 'p'; i++) {
            config.add("" + i);
        }
    }

    @Override
    public String part1() {
        doTheDance();

        return arrayToString(config);
    }

    public void doTheDance() {
        for (Move m : moves) {
            m.dance(config);
        }
    }

    public String arrayToString(ArrayList<String> arr) {
        StringBuilder res = new StringBuilder();
        for (String s : arr) {
            res.append(s);
        }
        return res.toString();
    }

    @Override
    public String part2() {
        int iterations = 1;
        while (!arrayToString(config).equals("abcdefghijklmnop")) {
            doTheDance();
            iterations++;
        }
        for (int i = 0; i < 1000000000 % iterations; i++) {
            doTheDance();
        }
        return arrayToString(config);
    }
}

class Move {
    String[] params;
    char type;

    public Move(String descriptor) {
        type = descriptor.charAt(0);
        params = descriptor.replaceAll("^[sxp]", "").split("/");
    }

    void dance(ArrayList<String> progs) {
        if (type == 's') {
            for (int i = 0; i < Integer.parseInt(params[0]); i++) {
                progs.add(0, progs.remove(progs.size() - 1));
            }
        } else {
            int a, b;
            if (type == 'x') {
                a = Integer.parseInt(params[0]);
                b = Integer.parseInt(params[1]);
            } else {
                a = progs.indexOf(params[0]);
                b = progs.indexOf(params[1]);
            }
            String temp = progs.get(a);
            progs.set(a, progs.get(b));
            progs.set(b, temp);
        }
    }
}
