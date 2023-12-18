package AoC2019;

import Helpers.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day6 implements Day {
    HashMap<String, Planet> space = new HashMap<>();

    public String partOne(String input) {
        //System.out.println(input);
        String[] rules = input.split("\n");
        //System.out.println(rules.length);
        for (String rule : rules) {
            String[] orbit = rule.split("\\)");
            Planet inner = space.get(orbit[0]);
            Planet outter = space.get(orbit[1]);

            if (inner == null) {
                inner = new Planet(orbit[0]);
                space.put(orbit[0], inner);
            }

            if (outter == null) {
                outter = new Planet(orbit[1]);
                space.put(orbit[1], outter);
            }

            inner.isOrbittedBy.add(outter);
            outter.orbits = inner;
        }


        Planet start = space.get("COM");
        start.countOfOrbits = 0;
        start.calcOrbitsOfIsOrbittedBy();
        return "" + sumOrbitCount(start);
    }

    public String partTwo(String input) {
        return "";
    }

    public int sumOrbitCount(Planet p) {
        int sum = p.countOfOrbits;
        for (Planet orbittedBy : p.isOrbittedBy) {
            sum += sumOrbitCount(orbittedBy);
        }
        return sum;
    }
}


class Planet {
    ArrayList<Planet> isOrbittedBy = new ArrayList<>();
    Planet orbits;
    String name;
    int countOfOrbits = 0;

    public Planet(String name) {
        this.name = name;
    }

    public void calcOrbitsOfIsOrbittedBy() {
        if (orbits != null) {
            countOfOrbits = 1 + orbits.countOfOrbits;
        }
        for (Planet p : isOrbittedBy) {
            p.calcOrbitsOfIsOrbittedBy();
        }
    }

    public String toString() {
        return name + " orbited by " + Arrays.toString(isOrbittedBy.toArray());
    }
}