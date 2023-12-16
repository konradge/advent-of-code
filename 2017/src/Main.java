import Days.*;
import Utils.IO.FileUtils;
import Utils.IO.StringUtils;

import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static Day getDayInstance(int day) {
        switch (day) {
            case 1:
                return new Day1();
            case 2:
                return new Day2();
            case 3:
                return new Day3();
            case 4:
                return (Day) new Day4();
            case 5:
                return (Day) new Day5();
            case 6:
                return (Day) new Day6();
            case 7:
                return new Day7();
            case 8:
                return new Day8();
            case 9:
                return new Day9();
            case 10:
                return new Day10();
            case 11:
                return new Day11();
            case 12:
                return new Day12();
            case 13:
                return new Day13();
            case 14:
                return new Day14();
            case 15:
                return new Day15();
            case 16:
                return new Day16();
            case 17:
                return new Day17();
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        if (args.length == 0)
            throw new RuntimeException("Day not defined");
        int day;
        try {
            day = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Parameter " + args[0] + " is no int!");
        }
        Day dayInstance = Main.getDayInstance(day);
        if (dayInstance == null) {
            throw new RuntimeException("Day number " + day + " has not been implemented yet!");
        }
        String input = FileUtils.readFile("./src/Inputs/day" + day + ".txt").replaceAll("\\r", "");
        dayInstance.init(input);
        System.out.println("+++++++++Day " + day + "+++++++++++");
        System.out.println("--------Part 1-----------");
        System.out.println(dayInstance.part1());
        System.out.println("--------Part 2-----------");
        System.out.println(dayInstance.part2());
    }
}
