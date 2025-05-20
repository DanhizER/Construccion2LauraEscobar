package app.adapters.rest.utils;

import java.util.Scanner;

public abstract class Utils {
    private static Scanner reader = new Scanner(System.in);
    public static Scanner geteader(){
        return reader;
    }
}
