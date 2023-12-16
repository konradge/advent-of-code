package Days;

import java.util.ArrayList;

public class Day9 implements Day {
    Group outerGroup;

    @Override
    public void init(String input) {
        outerGroup = new Group(input, 1, 1);
    }

    @Override
    public String part1() {
        return "%d".formatted(outerGroup.calcScore());
    }

    @Override
    public String part2() {
        return "%d".formatted(outerGroup.calcGarbage());
    }
}

class Group {
    int score, endIndex, containedGarbageCount = 0;
    ArrayList<Group> containedGroups = new ArrayList<>();

    public Group(String description, int startIndex, int score) {
        boolean garbageFollowing = false;
        this.score = score;
        char[] charArray = description.toCharArray();
        for (int i = startIndex; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '!') {
                i++;
            } else if (garbageFollowing) {
                if (c == '>') {
                    garbageFollowing = false;
                } else {
                    containedGarbageCount++;
                }
            } else if (c == '<') {
                garbageFollowing = true;
            } else if (c == '{') {
                containedGroups.add(new Group(description, i + 1, score + 1));
                i = containedGroups.get(containedGroups.size() - 1).endIndex;
            } else if (c == '}') {
                endIndex = i;
                break;
            }
        }
    }

    public int calcScore() {
        int score = this.score;
        for (Group g : containedGroups) {
            score += g.calcScore();
        }
        return score;
    }

    public int calcGarbage() {
        int garbage = this.containedGarbageCount;
        for (Group g : containedGroups) {
            garbage += g.calcGarbage();
        }
        return garbage;
    }
}
