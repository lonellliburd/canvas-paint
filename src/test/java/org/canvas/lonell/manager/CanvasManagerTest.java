package org.canvas.lonell.manager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;


public class CanvasManagerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        CanvasManager.setCanvas(null);
    }

    @After
    public void restoreStreams() {
        System.setOut(out);
    }

    @Test
    public void testCreateCanvasMinimumDimensions() throws Exception {
        CanvasManager.execute("C", "1", "1");
        assertEquals(
            "---\n" +
            "| |\n" +
            "---\n", outContent.toString());
    }

    @Test
    public void testCreateCanvasWithNegatives() throws Exception {
        CanvasManager.execute("C", "-1", "1");
        assertEquals("Invalid arguments. Values must be positive numbers.\n", outContent.toString());
    }

    @Test
    public void testCreateCanvasBelowMinimumDimensions() throws Exception {
        CanvasManager.execute("C", "0", "1");
        assertEquals("Invalid arguments. Values must be at at least 1.\n", outContent.toString());
    }

    @Test
    public void testCreateCanvas() throws Exception {
        CanvasManager.execute("C", "20", "4");
        assertEquals(
            "----------------------\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testDraw() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.draw();
        assertEquals(
            "----------------------\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testInvalidCommand() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "H", "1", "2", "3", "4");
        assertEquals("Sorry, invalid command. Please try: C, L, R, B or Q.\n", outContent.toString());
    }
    
    @Test
    public void testCannotDrawOnNullCanvas() throws Exception {
       CanvasManager.execute("L", "10", "2", "10", "5");
        assertEquals("Please create a canvas before attempting to draw shape.\n", outContent.toString());
    }

    @Test
    public void testLineInvalidNumberOfArguments() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "1", "1", "4");
        assertEquals("Invalid number of arguments.\n", outContent.toString());
    }

    @Test
    public void testLineDrawDot() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "1", "1", "1", "1");
        assertEquals(
            "----------------------\n" +
            "|x                   |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testLineInvalidTypeOfArguments() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "wq", "1", "3", "2");
        assertEquals("Invalid arguments. Values must be positive numbers.\n", outContent.toString());
    }

    @Test
    public void testLineCoordinatesNegative() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "0", "1", "-3", "-2");
        assertEquals("Invalid arguments. Values must be positive numbers.\n", outContent.toString());
    }

    @Test
    public void testLineCoordinatesOutsideCanvasEdge() throws Exception{
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "0", "1", "0", "11");
        assertEquals("Invalid arguments, coordinates are outside canvas dimensions.\n", outContent.toString());
    }

    @Test
    public void testLineOnlyHorizontalOrVerticalInvalid() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "1", "1", "4", "3");
        assertEquals("Invalid arguments, only horizontal and vertical lines are supported.\n", outContent.toString());
    }

    @Test
    public void testRectangleCoordinatesNegative() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "R", "-1", "1", "3", "-2");
        assertEquals("Invalid arguments. Values must be positive numbers.\n", outContent.toString());
    }

    @Test
    public void testLineHorizontalIsValid() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "1", "2", "6", "2");
        assertEquals(
            "----------------------\n" +
            "|                    |\n" +
            "|xxxxxx              |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testLineHorizontalHandleReverseCoordinates() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "6", "2", "1", "2");
        assertEquals(
            "----------------------\n" +
            "|                    |\n" +
            "|xxxxxx              |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testLineVerticalIsValid() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "6", "3", "6", "4");
        assertEquals(
            "----------------------\n" +
            "|                    |\n" +
            "|                    |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testLineSuccessiveCommands() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "1", "2", "6", "2");
        CanvasManager.execute( "L", "6", "3", "6", "4");
        assertEquals(
            "----------------------\n" +
            "|                    |\n" +
            "|xxxxxx              |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n" +
            "----------------------\n" +
            "|                    |\n" +
            "|xxxxxx              |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testRectangleInvalidNumberOfArguments() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "R", "1", "1", "4");
        assertEquals("Invalid number of arguments.\n", outContent.toString());
    }

    @Test
    public void testRectangleCoordinatesOutsideCanvasEdge() throws Exception{
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "0", "1", "0", "11");
        assertEquals("Invalid arguments, coordinates are outside canvas dimensions.\n", outContent.toString());
    }

    @Test
    public void tesRectangleMinimum() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "R", "16", "1", "17", "2");
        assertEquals(
            "----------------------\n" +
            "|               xx   |\n" +
            "|               xx   |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void tesRectangleCoordinatesNotMinimum() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "R", "16", "1", "16", "2");
        assertEquals("Rectangle dimensions are too small.\n", outContent.toString());
    }

    @Test
    public void tesRectangleIsValid() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "R", "16", "1", "20", "3");
        assertEquals(
            "----------------------\n" +
            "|               xxxxx|\n" +
            "|               x   x|\n" +
            "|               xxxxx|\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void tesRectangleHandleReversedCoordinates() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "R", "20", "3", "16", "1");
        assertEquals(
            "----------------------\n" +
            "|               xxxxx|\n" +
            "|               x   x|\n" +
            "|               xxxxx|\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void tesRectangleThenLineIsValid() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "R", "16", "1", "20", "3");
        CanvasManager.execute( "L", "1", "2", "6", "2");
        assertEquals(
            "----------------------\n" +
            "|               xxxxx|\n" +
            "|               x   x|\n" +
            "|               xxxxx|\n" +
            "|                    |\n" +
            "----------------------\n" +
            "----------------------\n" +
            "|               xxxxx|\n" +
            "|xxxxxx         x   x|\n" +
            "|               xxxxx|\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testLineThenRectangleIsValid() throws Exception{
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "1", "2", "6", "2");
        CanvasManager.execute( "L", "6", "3", "6", "4");
        CanvasManager.execute( "R", "16", "1", "20", "3");
        assertEquals(
            "----------------------\n" +
            "|                    |\n" +
            "|xxxxxx              |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n" +
            "----------------------\n" +
            "|                    |\n" +
            "|xxxxxx              |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "----------------------\n" +
            "----------------------\n" +
            "|               xxxxx|\n" +
            "|xxxxxx         x   x|\n" +
            "|     x         xxxxx|\n" +
            "|     x              |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testLineAndRectangleOverlap() throws Exception{
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "1", "2", "6", "2");
        CanvasManager.execute( "R", "3", "1", "20", "3");
        assertEquals(
            "----------------------\n" +
            "|                    |\n" +
            "|xxxxxx              |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n" +
            "----------------------\n" +
            "|  xxxxxxxxxxxxxxxxxx|\n" +
            "|xxxxxx             x|\n" +
            "|  xxxxxxxxxxxxxxxxxx|\n" +
            "|                    |\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testBucketFillInvalidNumberOfArguments() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "B", "1", "1");
        assertEquals("Invalid number of arguments.\n", outContent.toString());
    }

    @Test
    public void testBucketFillInvalidTypeOfArguments() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "B", "wq", "b", "o");
        assertEquals("Invalid arguments. Values must be positive numbers.\n", outContent.toString());
    }

    @Test
    public void testBucketFillCoordinatesNegative() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "B", "-1", "2", "o");
        assertEquals("Invalid arguments. Values must be positive numbers.\n", outContent.toString());
    }

    @Test
    public void testBucketFill() throws Exception{
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "B", "1", "1", "o");
        assertEquals(
            "----------------------\n" +
            "|oooooooooooooooooooo|\n" +
            "|oooooooooooooooooooo|\n" +
            "|oooooooooooooooooooo|\n" +
            "|oooooooooooooooooooo|\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testBucketFillCannotFillOutsideCanvas() throws Exception {
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "B", "21", "21", "o");
        assertEquals("Invalid arguments, coordinates are outside canvas dimensions.\n", outContent.toString());
    }

    @Test
    public void testLineThenRectangleThenBucketFillIsValid() throws Exception{
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "L", "1", "2", "6", "2");
        CanvasManager.execute( "L", "6", "3", "6", "4");
        CanvasManager.execute( "R", "16", "1", "20", "3");
        CanvasManager.execute( "B", "10", "3", "o");
        assertEquals(
            "----------------------\n" +
            "|                    |\n" +
            "|xxxxxx              |\n" +
            "|                    |\n" +
            "|                    |\n" +
            "----------------------\n" +
            "----------------------\n" +
            "|                    |\n" +
            "|xxxxxx              |\n" +
            "|     x              |\n" +
            "|     x              |\n" +
            "----------------------\n" +
            "----------------------\n" +
            "|               xxxxx|\n" +
            "|xxxxxx         x   x|\n" +
            "|     x         xxxxx|\n" +
            "|     x              |\n" +
            "----------------------\n" +
            "----------------------\n" +
            "|oooooooooooooooxxxxx|\n" +
            "|xxxxxxooooooooox   x|\n" +
            "|     xoooooooooxxxxx|\n" +
            "|     xoooooooooooooo|\n" +
            "----------------------\n", outContent.toString());
    }

    @Test
    public void testCannotDrawShapeAfterBucketFill() throws Exception{
        CanvasManager.createCanvas(20, 4);
        CanvasManager.execute( "B", "1", "1", "o");
        CanvasManager.execute( "L", "6", "3", "6", "4");
        assertEquals(
            "----------------------\n" +
            "|oooooooooooooooooooo|\n" +
            "|oooooooooooooooooooo|\n" +
            "|oooooooooooooooooooo|\n" +
            "|oooooooooooooooooooo|\n" +
            "----------------------\n" +
            "----------------------\n" +
            "|oooooooooooooooooooo|\n" +
            "|oooooooooooooooooooo|\n" +
            "|oooooooooooooooooooo|\n" +
            "|oooooooooooooooooooo|\n" +
            "----------------------\n", outContent.toString());
    }
}
