/*
 * Decompiled with CFR 0_118.
 */
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IO {
    public static void printStatus(String status, String message) {
        System.out.print("[" + status + "] " + message);
    }

    public static Scanner getUserInput(String question) {
        IO.printStatus("?", question);
        return new Scanner(System.in);
    }
}

