package AoC2019;

import Helpers.Day;
import Helpers.IO;

import java.awt.*;
import java.util.Arrays;

public class Day8 implements Day {
    
    public String partOne(String input) {
        calcImage(parseInput(input));
        return "";
    }

    public String partTwo(String input) {
        return "";
    }

    static int[] parseInput(String input) {
        String[] characters = input.split("");
        int[] res = new int[characters.length];
        for (int i = 0; i < characters.length; i++) {
            res[i] = Integer.parseInt(characters[i]);
        }
        return res;
    }

    static void calcImage(int[] digits) {
        int width = 25;
        int height = 6;
        int currentIndex = 0;
        Layer[] layers = new Layer[digits.length / (width * height)];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(width, height, Arrays.copyOfRange(digits, currentIndex, currentIndex + (width * height)));
            if (i != 0) {
                layers[i - 1].nextLayer = layers[i];
            }
            currentIndex += (width * height);
        }

        Layer layerWithLeastZeros = layers[0];
        for (Layer layer : layers) {
            if (layerWithLeastZeros.getNumberOfDigits(0) > layer.getNumberOfDigits(0)) {
                layerWithLeastZeros = layer;
            }
        }

        System.out.println(layerWithLeastZeros.getNumberOfDigits(1) * layerWithLeastZeros.getNumberOfDigits(2));

        //Aufgabe 2
        IO.printImage(layers[0].getImage(), 200);

    }
}

class Layer {
    int[][] pixels;
    Layer nextLayer;

    public Layer(int width, int height, int[] p) {
        pixels = new int[height][width];
        int currentIndex = 0;
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                pixels[i][j] = p[currentIndex++];
            }
        }
    }

    public int getNumberOfDigits(int digit) {
        int number = 0;
        for (int[] row : pixels) {
            for (int pixel : row) {
                if (pixel == digit) {
                    number++;
                }
            }
        }
        return number;
    }

    public Color[][] getImage() {
        Color[][] res = new Color[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                res[i][j] = getColorOfPixel(i, j);
            }
        }
        return res;
    }

    public Color getColorOfPixel(int i, int j) {
        if (pixels[i][j] == 0) {
            return Color.BLACK;
        } else if (pixels[i][j] == 1) {
            return Color.WHITE;
        } else {
            if (nextLayer == null) {
                return Color.BLACK;
            }
            return nextLayer.getColorOfPixel(i, j);
        }
    }
}
