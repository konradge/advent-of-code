package Days;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 implements Day {
    Node[] nodes;
    Set<Integer> toVisit;

    @Override
    public void init(String input) {
        String[] lines = input.split("\\n");
        nodes = new Node[lines.length];
        for (int i = 0; i < lines.length; i++) {
            nodes[i] = new Node(lines[i]);
        }
    }

    @Override
    public String part1() {
        return visitReachable(0).size() + "";
    }

    public Set<Integer> visitReachable(int first) {
        Set<Integer> reachable = new HashSet<>();
        Stack<Integer> reachableStack = new Stack<>();
        reachableStack.push(first);
        reachable.add(first);
        while (!reachableStack.isEmpty()) {
            int next = reachableStack.pop();
            if (nodes[next].visited)
                continue;
            nodes[next].visited = true;
            reachableStack.addAll(nodes[next].neighbours);
            reachable.addAll(nodes[next].neighbours);
        }
        for (int i : reachable) {
            nodes[i].visited = false;
        }
        return reachable;
    }

    @Override
    public String part2() {
        int count = 0;
        toVisit = IntStream.range(0, nodes.length).boxed().collect(Collectors.toSet());
        while (!toVisit.isEmpty()) {
            toVisit.removeAll(visitReachable(toVisit.stream().findAny().orElse(0)));
            count++;
        }
        return count + "";
    }
}

class Node {
    int id;
    List<Integer> neighbours;
    boolean visited = false;

    public Node(String descriptor) {
        this.id = Integer.parseInt(descriptor.split(" <-> ")[0]);
        this.neighbours = Arrays.stream(descriptor.split(" <-> ")[1].split(", ")).map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
