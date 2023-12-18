package AoC2019;

import Helpers.Day;
import Helpers.IO;
import Helpers.Utils;

import java.util.ArrayList;

public class Day24 implements Day {
    static ArrayList<Layout> allStates = new ArrayList<>();

    public String partOne(String input) {
        String[][] startingState = IO.get2DStringArray(input, 5, 5);
        allStates.add(new Layout(startingState));
        nextState(startingState);
        return "";
    }

    @Override
    public String partTwo(String input) {
        return null;
    }

    static void nextState(String[][] previous) {
        String[][] nextState = Utils.clone(previous);
        for (int i = 0; i < previous.length; i++) {
            for (int j = 0; j < previous[i].length; j++) {
                int adjacentBugs = 0;
                if (i > 0 && previous[i - 1][j].equals("#")) {
                    adjacentBugs++;
                }
                if (i < previous.length - 1 && previous[i + 1][j].equals("#")) {
                    adjacentBugs++;
                }
                if (j > 0 && previous[i][j - 1].equals("#")) {
                    adjacentBugs++;
                }
                if (j < previous[i].length - 1 && previous[i][j + 1].equals("#")) {
                    adjacentBugs++;
                }
                if (previous[i][j].equals("#") && adjacentBugs != 1) {
                    nextState[i][j] = ".";
                } else if (previous[i][j].equals(".") && (adjacentBugs == 1 || adjacentBugs == 2)) {
                    nextState[i][j] = "#";
                }
            }
        }
        allStates.add(new Layout(nextState));
        int j = allStates.size() - 1;
        for (int i = 0; i < allStates.size(); i++) {
            for (j = allStates.size() - 1; j > i; j--) {
                if (allStates.get(i).cellString.equals(allStates.get(j).cellString)) {
                    System.out.println("Found it");
                    System.out.println(allStates.get(i).biodiversity);
                    allStates.get(i).calcBiodiversity();
                    IO.print(allStates.get(i).cells);
                    return;
                }
            }
        }
        //IO.print(nextState);
        //System.out.println("-------" + (allStates.size() - 1) + "-----");
        nextState(Utils.clone(nextState));
    }
}

class Layout {
    String[][] cells;
    String cellString = "";
    int biodiversity = 0;

    public Layout(String[][] cells) {
        this.cells = cells;
        calcBiodiversity();
    }

    void calcBiodiversity() {
        biodiversity = 0;
        int counter = 0;
        for (String[] r : cells) {
            for (String s : r) {
                cellString += s;
                if (s.equals("#")) {
                    //System.out.println((int) Math.pow(2, counter));
                    biodiversity = biodiversity + (int) Math.pow(2, counter);
                }
                counter++;
            }
        }
    }
}
