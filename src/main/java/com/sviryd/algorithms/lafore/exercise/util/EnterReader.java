package com.sviryd.algorithms.lafore.exercise.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EnterReader {
    public static String getEnteredLine() {
        try (InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader br = new BufferedReader(isr)) {
            String s = br.readLine();
            return s;
        } catch (IOException exception) {
            System.out.println("Problem with reading your data.");
            return null;
        }
    }
}
