package ui;

import java.io.FileNotFoundException;

// Represents MainUI for running BillionDollarUI
public class MainUI {

    public static void main(String[] args) {
        try {
            new BillionDollarGameUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }

}
