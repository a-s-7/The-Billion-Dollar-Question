package gui;

import gui.BillionDollarGameGUI;

import java.io.FileNotFoundException;

// Represents MainGUI for running BillionDollarGUI
public class MainGUI {

    public static void main(String[] args) {
        try {
            new BillionDollarGameGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }

}

