package model;

import java.util.ArrayList;

// Represents a bank of questions having 14 lists for each of the questions in the game that a player can pass
public class QuestionBank {
    private ArrayList<Question> l1; // list for storing all questions that are question number 1
    private ArrayList<Question> l2; // list for storing all questions that are question number 2
    private ArrayList<Question> l3; // list for storing all questions that are question number 3
    private ArrayList<Question> l4; // list for storing all questions that are question number 4
    private ArrayList<Question> l5; // list for storing all questions that are question number 5
    private ArrayList<Question> l6; // list for storing all questions that are question number 6
    private ArrayList<Question> l7; // list for storing all questions that are question number 7
    private ArrayList<Question> l8; // list for storing all questions that are question number 8
    private ArrayList<Question> l9; // list for storing all questions that are question number 9
    private ArrayList<Question> l10; // list for storing all questions that are question number 10
    private ArrayList<Question> l11; // list for storing all questions that are question number 11
    private ArrayList<Question> l12; // list for storing all questions that are question number 12
    private ArrayList<Question> l13; // list for storing all questions that are question number 13
    private ArrayList<Question> l14; // list for storing all questions that are question number 14

    // EFFECTS: creates a QuestionBank with 14 different lists each corresponding to the group of questions that are
    //          grouped by question number in the JSON file where all questions are stored.
    public QuestionBank() {
        l1 = new ArrayList<>();
        l2 = new ArrayList<>();
        l3 = new ArrayList<>();
        l4 = new ArrayList<>();
        l5 = new ArrayList<>();
        l6 = new ArrayList<>();
        l7 = new ArrayList<>();
        l8 = new ArrayList<>();
        l9 = new ArrayList<>();
        l10 = new ArrayList<>();
        l11 = new ArrayList<>();
        l12 = new ArrayList<>();
        l13 = new ArrayList<>();
        l14 = new ArrayList<>();
    }

    // REQUIRES: l > 0
    // MODIFIES: this
    // EFFECTS: given an integer, corresponding to the specific list of questions (grouped by question number), adds q
    //          to the list for the question
    @SuppressWarnings("methodlength")
    public void addQuestionToQuestionBank(int l, Question q) {
        if (l == 1) {
            l1.add(q);
        } else if (l == 2) {
            l2.add(q);
        } else if (l == 3) {
            l3.add(q);
        } else if (l == 4) {
            l4.add(q);
        } else if (l == 5) {
            l5.add(q);
        } else if (l == 6) {
            l6.add(q);
        } else if (l == 7) {
            l7.add(q);
        } else if (l == 8) {
            l8.add(q);
        } else if (l == 9) {
            l9.add(q);
        } else if (l == 10) {
            l10.add(q);
        } else if (l == 11) {
            l11.add(q);
        } else if (l == 12) {
            l12.add(q);
        } else if (l == 13) {
            l13.add(q);
        } else {
            l14.add(q);
        }
    }

    // REQUIRES: l > 0, n > 0
    // MODIFIES: this
    // EFFECTS: given two integers, l corresponding to the specific list of questions, and n corresponding to the
    //          position in the list (index + 1), retrieves and returns the corresponding question
    @SuppressWarnings("methodlength")
    public Question getQuestionFromQuestionBank(int l, int n) {
        Question q;
        int index = n - 1;

        if (l == 1) {
            q = l1.get(index);
        } else if (l == 2) {
            q = l2.get(index);
        } else if (l == 3) {
            q = l3.get(index);
        } else if (l == 4) {
            q = l4.get(index);
        } else if (l == 5) {
            q = l5.get(index);
        } else if (l == 6) {
            q = l6.get(index);
        } else if (l == 7) {
            q = l7.get(index);
        } else if (l == 8) {
            q = l8.get(index);
        } else if (l == 9) {
            q = l9.get(index);
        } else if (l == 10) {
            q = l10.get(index);
        } else if (l == 11) {
            q = l11.get(index);
        } else if (l == 12) {
            q = l12.get(index);
        } else if (l == 13) {
            q = l13.get(index);
        } else {
            q = l14.get(index);
        }
        return q;
    }

}
