package org.canvas.lonell.manager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Temp {

    @Test
    public void testCreateCanvasMinimumDimensions() throws Exception {
        CanvasManager.execute("C", "1", "1");
        assertEquals(
                "---\n" +
                        "| |\n" +
                        "---\n", "");
    }

}