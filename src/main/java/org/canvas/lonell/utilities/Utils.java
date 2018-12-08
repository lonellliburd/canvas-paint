package org.canvas.lonell.utilities;

public class Utils {

    public static boolean isEmpty(String str){
        if (str == null || str.length() == 0){
            return true;
        }

        for(int i = 0; i < str.length(); i++){
            if(!Character.isWhitespace(str.charAt(i))){
                return false;
            }
        }

        return true;
    }

    public static void swapSmaller(String[] args, int a, int b){
        if (Integer.parseInt(args[a]) > Integer.parseInt(args[b])){
            String temp = args[a];
            args[a] = args[b];
            args[b] = temp;
        }
    }

    public static boolean isNumericAndPositive(int start, int stop, String... args){
        for(int i=start; i<=stop; i++){
            try{
                if (Integer.parseInt(args[i]) < 0){
                    return false;
                }
            } catch (NumberFormatException nfe){
                return false;
            }
        }
        return true;
    }

    public static String[] splitString(String str){
        return !isEmpty(str) ? str.trim().split(" ") : new String[0];
    }
}