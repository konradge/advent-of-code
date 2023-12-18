package AoC2019;

import Helpers.Day;
import Helpers.Start;

import java.util.HashMap;

public class Start2019 extends Start {
    public static final String YEAR = "2019";
    public static final String DEFAULT_DAY = "20";
    HashMap<String, Day> objects = new HashMap<>();

    public Start2019() {
        objects.put("6", new Day6());
        objects.put("8", new Day8());
        objects.put("20", new Day20());
        objects.put("24", new Day24());
    }
}
