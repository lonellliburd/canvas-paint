package org.canvas.lonell.entity;

public class Canvas {
    char[][] canvas;
    private int width;
    private int height;

    public Canvas(int w, int h){
        width = w+2;
        height = h+2;
        canvas = new char[height][width];
    }

    public char[][] getCanvas() {
        return canvas;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}