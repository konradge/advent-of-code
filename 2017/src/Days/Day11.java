package Days;

import java.util.ArrayList;

public class Day11 implements Day {
    String totalWay;
    String currentWay;
    final Replacement[] replacements = new Replacement[] {
            new Replacement("n", "s"), new Replacement("ne", "sw"), new Replacement("nw", "se"),
            new Replacement("ne", "nw", "n"), new Replacement("se", "sw", "s"),
            new Replacement("ne", "s", "se"), new Replacement("nw", "s", "sw"),
            new Replacement("se", "n", "ne"), new Replacement("sw", "n", "nw")
    };

    @Override
    public void init(String input) {
        this.totalWay = "," + input.replaceAll("\\n", ",");
    }

    @Override
    public String part1() {
        return getDistance(totalWay) + "";
    }

    public int getDistance(String way) {
        if (way.length() % 100 == 0)
            System.out.println(way.length() + "/" + totalWay.length());
        this.currentWay = "" + way;
        simplify(replacements);
        currentWay = this.currentWay.replaceAll(",+", ",");
        currentWay = currentWay.replaceAll("^,+", "");
        currentWay = currentWay.replaceAll(",+$", "");
        return currentWay.split(",").length;
    }

    public void simplify(Replacement[] replacements) {
        for (Replacement replacement : replacements) {
            replacement.replaceString(this);
        }
    }

    public ArrayList<String> replace(String str, Replacement[] replacements) {
        ArrayList<String> res = new ArrayList<>();
        for (Replacement replacement : replacements) {
            StringBuilder builder = new StringBuilder();
            if (replacement.str1.equals(str))
                builder.append(replacement.str2);
            if (replacement.str2.equals(str))
                builder.append(replacement.str1);
            if (replacement.repl != null)
                builder.append("#").append(replacement.repl);
            if (builder.length() >= 1)
                res.add(builder.toString());
        }
        return res;
    }

    @Override
    public String part2() {
        return null;
    }
}

class Replacement {
    final String str1;
    final String str2;
    String repl;

    public Replacement(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    public Replacement(String str1, String str2, String repl) {
        this(str1, str2);
        this.repl = repl;
    }

    public boolean replaceString(Day11 day11) {
        while (day11.currentWay.contains("," + str1 + ",") && day11.currentWay.contains("," + str2 + ",")) {
            day11.currentWay = day11.currentWay.replaceFirst("," + str1 + ",", ",");
            day11.currentWay = day11.currentWay.replaceFirst("," + str2 + ",", ",");
            day11.currentWay = day11.currentWay.replaceAll(",+", ",");
            if (repl != null)
                day11.currentWay += (repl + ",");
        }
        return true;
    }
}
