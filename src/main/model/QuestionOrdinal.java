package model;

import java.util.ArrayList;

// Represents the ordinals for questions (constant) in the game in an ArrayList
public class QuestionOrdinal {
    private static final String O1 = "first"; // ordinal for question 1
    private static final String O2 = "second"; // ordinal for question 2
    private static final String O3 = "third"; // ordinal for question 3
    private static final String O4 = "fourth"; // ordinal for question 4
    private static final String O5 = "fifth"; // ordinal for question 5
    private static final String O6 = "sixth"; // ordinal for question 6
    private static final String O7 = "seventh"; // ordinal for question 7
    private static final String O8 = "eighth"; // ordinal for question 8
    private static final String O9 = "ninth"; // ordinal for question 9
    private static final String O10 = "tenth"; // ordinal for question 10
    private static final String O11 = "eleventh"; // ordinal for question 11
    private static final String O12 = "twelfth"; // ordinal for question 12
    private static final String O13 = "thirteenth"; // ordinal for question 13
    private static final String O14 = "fourteenth and final"; // ordinal for question 14
    private ArrayList<String> ordinalList; // the list of ordinal's for questions

    // EFFECTS: instantiates an empty ordinalList of type ArrayList, adds ordinals for all questions
    public QuestionOrdinal() {
        ordinalList = new ArrayList<>();
        ordinalList.add(O1);
        ordinalList.add(O2);
        ordinalList.add(O3);
        ordinalList.add(O4);
        ordinalList.add(O5);
        ordinalList.add(O6);
        ordinalList.add(O7);
        ordinalList.add(O8);
        ordinalList.add(O9);
        ordinalList.add(O10);
        ordinalList.add(O11);
        ordinalList.add(O12);
        ordinalList.add(O13);
        ordinalList.add(O14);
    }

    // EFFECTS: retrieves and returns ordinal for question q from ordinal list
    public String getOrdinal(int q) {
        return ordinalList.get(q);
    }

}
