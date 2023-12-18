package AoC2019;

import Helpers.Day;
import Helpers.IO;
import Helpers.Queue;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Day20 implements Day {
    public String partOne(String input) {
        //Get Input as String[][]
        String[][] arr = IO.get2DStringArraySplitByNewline(input);

        //Create maze
        Maze m = new Maze(arr);
        return "";
    }

    public String partTwo(String input) {
        return "";
    }
}

class Maze {
    //All vertices of the maze
    Tile[][] tiles;

    //Strings from the input (for calculating portals)
    String[][] fieldDescriptions;

    //Starting vertex of the maze
    Tile start;

    public Maze(String[][] fields) {
        this.fieldDescriptions = fields;
        tiles = new Tile[fields.length][fields[0].length];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                if (fields[i][j].equals(".")) {
                    //Create vertices for all traversable fields
                    tiles[i][j] = new Tile(this, i, j);
                }
            }
        }

        //Calculate edges of each vertex
        calcNeighbours();

        //Start BFS
        retraverse(solve(start));
    }

    public void calcNeighbours() {
        for (int i = 0; i < tiles.length; i++) {
            j:
            for (int j = 0; j < tiles[i].length; j++) {
                Tile tile = tiles[i][j];
                if (tile == null) {
                    //Tile is not traversable (either # or Letter or ' ' in input)
                    continue;
                }

                //Calcualte adjacent neighbours
                tile.calcNeigbours();

                //Calculate edges via portals
                Tile partner;
                if (tile.portal != null) {
                    if (tile.portal.equals("AA")) {
                        start = tile;
                        continue;
                    }

                    //Go through all edges of the graph, to find the one with the same portal
                    for (int k = 0; k < tiles.length; k++) {
                        for (int l = 0; l < tiles[k].length; l++) {
                            if (tiles[k][l] == null || tiles[k][l].portal == null) {
                                //Tile is not traversable or has no portal
                                continue;
                            }
                            if (tiles[k][l].portal.equals(tile.portal)) {
                                //Found partner-portal -> add edges to both
                                partner = tiles[k][l];
                                partner.neighbours.add(tile);
                                tile.neighbours.add(partner);
                                continue j;
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * Starting at the end of the maze, always get the Vertex, the current vertex was visited by
     * and make this the new current vertex, until the start is reached
     */
    public void retraverse(Tile end) {
        System.out.println("retraverse");
        int counter = 0;
        Tile lastTile = end;
        while (lastTile.visitedBy != null && (lastTile.portal == null || !lastTile.portal.equals("AA"))) {
            //for drawing
            lastTile.isOnShortestPath = true;

            //Go back one step in the maze
            lastTile = lastTile.visitedBy;

            //Count how many steps have been gone back
            counter++;

            //for drawing
            printImage();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Print total number of steps
        System.out.println(counter + " steps");
    }

    public Tile solve(Tile start) {
        System.out.println("solve");
        Queue<Tile> q = new Queue();
        q.enqueue(start);
        Tile current;
        while (!q.isEmpty()) {
            //Get first Tile in queue
            current = q.dequeue();
            if (current.isEnd) {
                //End reached
                return current;
            }
            for (Tile n : current.neighbours) {
                //Go through all edges(neighbours) and add them to the queue
                if (n != null && n.visitedBy == null && (n.portal == null || !n.portal.equals("AA"))) {
                    n.visitedBy = current;
                    q.enqueue(n);
                }
            }

            //For drawing
            printImage();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Solve finished");
        return null;
    }

    //For drawing
    //...
    BufferedImage image;

    public void printImage() {
        Color[][] colors = new Color[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != null) {
                    colors[i][j] = Color.WHITE;
                    if (tiles[i][j].portal != null) {
                        colors[i][j] = Color.RED;
                        if (tiles[i][j].isEnd) {
                            colors[i][j] = Color.GREEN;
                        } else if (tiles[i][j].portal.equals("AA")) {
                            colors[i][j] = Color.BLUE;
                        }
                    }
                    if (tiles[i][j].visitedBy != null) {
                        colors[i][j] = Color.BLUE;
                    }
                    if (tiles[i][j].isOnShortestPath) {
                        colors[i][j] = Color.RED;
                    }
                } else {
                    colors[i][j] = Color.BLACK;
                }
            }
        }
        IO.redrawImage(colors, 700);
    }
}

class Tile {
    Maze maze;
    ArrayList<Tile> neighbours = new ArrayList<>();
    int[] pos;
    String portal = null;
    Tile visitedBy;
    boolean isEnd;

    //for drawing
    boolean isOnShortestPath;

    public Tile(Maze maze, int i, int j) {
        //Save position of Tile and maze
        pos = new int[]{i, j};
        this.maze = maze;

        calcPortal();
    }

    public void calcNeigbours() {
        int i = pos[0], j = pos[1];
        //Go through all adjacent tiles and add them as neighbours
        if (i > 0) {
            neighbours.add(maze.tiles[i - 1][j]);
        }
        if (j > 0) {
            neighbours.add(maze.tiles[i][j - 1]);
        }
        if (i < maze.tiles.length - 1) {
            neighbours.add(maze.tiles[i + 1][j]);
        }
        if (j < maze.tiles[i].length - 1) {
            neighbours.add(maze.tiles[i][j + 1]);
        }
    }

    public void calcPortal() {
        int i = pos[0];
        int j = pos[1];

        //Get first/last letter of eventually adjacent portals
        char up = maze.fieldDescriptions[i - 1][j].charAt(0);
        char down = maze.fieldDescriptions[i + 1][j].charAt(0);
        char left = maze.fieldDescriptions[i][j - 1].charAt(0);
        char right = maze.fieldDescriptions[i][j + 1].charAt(0);
        if (isAToZ(up)) {
            portal = maze.fieldDescriptions[i - 2][j] + up;
        } else if (isAToZ(down)) {
            portal = down + maze.fieldDescriptions[i + 2][j];
        } else if (isAToZ(left)) {
            portal = maze.fieldDescriptions[i][j - 2] + left;
        } else if (isAToZ(right)) {
            portal = right + maze.fieldDescriptions[i][j + 2];
        }
        if (portal != null && portal.equals("ZZ")) {
            this.isEnd = true;
        }
    }

    public String toString() {
        String n = "{";
        for (Tile t : neighbours) {
            if (t != null) {
                n += ("(" + t.pos[0] + ", " + t.pos[1] + "); ");
            }
        }
        return "(" + pos[0] + ", " + pos[1] + ")->" + n + "}*" + portal + "*";
    }

    private static boolean isAToZ(char c) {
        return c >= 65 && c <= 90;
    }
}
