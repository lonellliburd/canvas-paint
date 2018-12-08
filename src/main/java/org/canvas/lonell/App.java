package org.canvas.lonell;

import org.canvas.lonell.manager.CanvasManager;

import java.util.Scanner;

public class App {

    public static void main( String[] args ) {
        Scanner in = new Scanner(System.in);

        while(true){
            System.out.print("enter a command: ");
            String input = in.nextLine();

            if (input.length() > 0){
                CanvasManager.execute(input);
            }
        }

    }


}
