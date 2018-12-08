package org.canvas.lonell.manager;

import org.canvas.lonell.entity.Canvas;
import org.canvas.lonell.entity.Coordinates;
import org.canvas.lonell.utilities.Utils;

import java.util.Arrays;
import java.util.Stack;

public class CanvasManager {

    private static Canvas canvas = null;

    private static void initialise() {
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        char cArray[][] = canvas.getCanvas();
        fill();

        for (int i = 0; i < height; i++) {
            if (i == 0 || i == height - 1) {
                for (int j = 0; j < width; j++) {
                    fill(cArray, i, j, '-');
                }
            } else {
                fill(cArray, i, 0, '|');
                fill(cArray, i, width - 1, '|');
            }
        }
    }

    public static Canvas createCanvas(int width, int height) {
        canvas = new Canvas(width, height);
        initialise();
        return canvas;
    }

    private static Canvas createCanvas(String[] args) {
        return createCanvas(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

    public static void execute(String command, String... args) {
        if (command.equals("Q")) {
            System.exit(0);
        }

        if (checkCommand(command, args)) {
            addShape(command, args);
            draw();
        }
    }

    public static void execute(String args) {
        String[] content = Utils.splitString(args);
        CanvasManager.execute(content[0], Arrays.copyOfRange(content, 1, content.length));
    }

    private static void addShape(String command, String[] args) {
        switch (command) {
            case "C":
                createCanvas(args);
                break;

            case "L":
                addLine(args);
                break;

            case "R":
                addRectangle(args);
                break;

            case "B":
                addBucketFill(args);
                break;

            default:
                break;
        }
    }

    private static void addLine(String... args) {
        char c[][] = canvas.getCanvas();
        int i, x, y, from, to;

        if (args[1].equals(args[3])) {
            from = Integer.parseInt(args[0]);
            to = Integer.parseInt(args[2]);
            y = Integer.parseInt(args[1]);

            for (i = from; i <= to; i++) {
                fill(c, y, i, 'x');
            }
        } else if (args[0].equals(args[2])) {
            from = Integer.parseInt(args[1]);
            to = Integer.parseInt(args[3]);
            x = Integer.parseInt(args[0]);
            for (i = from; i <= to; i++) {
                fill(c, i, x, 'x');
            }
        }
    }

    private static void addRectangle(String... args) {
        addLine(args[0], args[1], args[0], args[3]);
        addLine(args[0], args[1], args[2], args[1]);
        addLine(args[2], args[1], args[2], args[3]);
        addLine(args[0], args[3], args[2], args[3]);
    }

    private static void addBucketFill(String[] args) {
        Stack<Coordinates> moves = new Stack<>();
        char cArray[][] = canvas.getCanvas();
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[0]);
        char c = args[2].charAt(0);

        moves.add(new Coordinates(x, y));

        while (!moves.empty()) {
            Coordinates current = moves.pop();
            x = current.getX();
            y = current.getY();

            fill(cArray, x, y, c);
            push(moves, x, y);
        }
    }

    private static boolean checkCommand(String command, String[] args) {
        boolean check;
        switch (command) {
            case "C":
                check = isValidCreate(args);
                break;
            case "L":
                check = isValidLine(args);
                break;

            case "R":
                check = isValidRectangle(args);
                break;

            case "B":
                check = isValidBucketFill(args);
                break;

            default:
                System.out.println("Sorry, invalid command. Please try: C, L, R, B or Q.");
                check = false;
                break;
        }

        return check;
    }

    private static boolean isValidCreate(String[] args) {
        if (!isValidLength(args, 2)) {
            return false;
        }

        if (!Utils.isNumericAndPositive(0, 1, args)) {
            System.out.println("Invalid arguments. Values must be positive numbers.");
            return false;
        }

        return isMinimumDimension(args);
    }

    private static boolean isValidLine(String[] args) {
        if (!isInitialised()) {
            return false;
        }

        if (!isValidLength(args, 4)) {
            return false;
        }

        if (!Utils.isNumericAndPositive(0, 3, args)) {
            System.out.println("Invalid arguments. Values must be positive numbers.");
            return false;
        }

        if (!args[0].equals(args[2]) && !args[1].equals(args[3])) {
            System.out.println("Invalid arguments, only horizontal and vertical lines are supported.");
            return false;
        }

        Utils.swapSmaller(args, 0, 2);
        Utils.swapSmaller(args, 1, 3);

        return isWithinBoundary(args, 0, 1, 2, 3);
    }

    private static boolean isValidRectangle(String[] args) {
        if (!isInitialised()) {
            return false;
        }

        if (!isValidLength(args, 4)) {
            return false;
        }

        if (!Utils.isNumericAndPositive(0, 3, args)) {
            System.out.println("Invalid arguments. Values must be positive numbers.");
            return false;
        }

        if (calculateArea(args) < 1) {
            System.out.println("Rectangle dimensions are too small.");
            return false;
        }

        Utils.swapSmaller(args, 0, 2);
        Utils.swapSmaller(args, 1, 3);

        return isWithinBoundary(args, 0, 1, 2, 3);
    }

    private static boolean isValidBucketFill(String[] args) {
        if (!isInitialised()) {
            return false;
        }

        if (!isValidLength(args, 3)) {
            return false;
        }

        if (!Utils.isNumericAndPositive(0, 1, args)) {
            System.out.println("Invalid arguments. Values must be positive numbers.");
            return false;
        }

        return isWithinBoundary(args, 0, 0, 1, 1);
    }

    private static boolean isInitialised() {
        if (canvas == null) {
            System.out.println("Please create a canvas before attempting to draw shape.");
            return false;
        }
        return true;
    }

    private static boolean isWithinBoundary(String[] args, int x1, int y1, int x2, int y2) {
        if (Integer.parseInt(args[x1]) <= 0 || Integer.parseInt(args[y1]) >= canvas.getWidth() - 1 ||
                Integer.parseInt(args[x2]) <= 0 || Integer.parseInt(args[y2]) >= canvas.getHeight() - 1) {
            System.out.println("Invalid arguments, coordinates are outside canvas dimensions.");
            return false;
        }
        return true;
    }

    private static boolean isMinimumDimension(String[] args) {
        for (String arg : args) {
            if (Integer.parseInt(arg) < 1) {
                System.out.println("Invalid arguments. Values must be at at least 1.");
                return false;
            }
        }

        return true;
    }

    private static boolean isValidLength(String[] args, int length) {
        if (args.length != length) {
            System.out.println("Invalid number of arguments.");
            return false;
        }

        return true;
    }

    private static void fill() {
        char cArray[][] = canvas.getCanvas();

        for (int i = 0; i < canvas.getHeight(); i++) {
            for (int j = 0; j < canvas.getWidth(); j++) {
                fill(cArray, i, j, ' ');
            }
        }
    }

    private static void fill(char[][] canvas, int i, int j, char c) {
        if (canvas[i][j] == '\u0000' || canvas[i][j] == ' ') {
            canvas[i][j] = c;
        }
    }

    public static void draw() {
        char cArray[][] = canvas.getCanvas();

        for (int i = 0; i < canvas.getHeight(); i++) {
            for (int j = 0; j < canvas.getWidth(); j++) {
                System.out.print(cArray[i][j]);
            }
            System.out.println();
        }
    }

    private static void push(Stack<Coordinates> stack, int x, int y) {
        char box[][] = canvas.getCanvas();
        if (x + 1 > 0 && x + 1 < canvas.getHeight() - 1 && box[x + 1][y] == ' ') {
            stack.add(new Coordinates(x + 1, y));
        }

        if (x - 1 > 0 && x - 1 < canvas.getHeight() - 1 && box[x - 1][y] == ' ') {
            stack.add(new Coordinates(x - 1, y));
        }

        if (y + 1 > 0 && y + 1 < canvas.getWidth() - 1 && box[x][y + 1] == ' ') {
            stack.add(new Coordinates(x, y + 1));
        }

        if (y - 1 > 0 && y - 1 < canvas.getWidth() - 1 && box[x][y - 1] == ' ') {
            stack.add(new Coordinates(x, y - 1));
        }
    }

    private static int calculateArea(String args[]) {
        return Math.abs(Integer.parseInt(args[0]) - Integer.parseInt(args[2])) *
                Math.abs(Integer.parseInt(args[1]) - Integer.parseInt(args[3]));
    }

    public static void setCanvas(Canvas c) {
        canvas = c;
    }
}