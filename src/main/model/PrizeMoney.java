package model;

import java.util.ArrayList;

// Represents the prize money levels (constant) in the game in an ArrayList
public class PrizeMoney {
    private static final String Q1 = "1,000"; // prize money for q1
    private static final String Q2 = "2,000"; // prize money for q2
    private static final String Q3 = "10,000"; // prize money for q3
    private static final String Q4 = "20,000"; // prize money for q4
    private static final String Q5 = "40,000"; // prize money for q5
    private static final String Q6 = "100,000"; // prize money for q6
    private static final String Q7 = "200,000"; // prize money for q7
    private static final String Q8 = "1,000,000"; // prize money for q8
    private static final String Q9 = "5,000,000"; // prize money for q9
    private static final String Q10 = "10,000,000"; // prize money for q10
    private static final String Q11 = "20,000,000"; // prize money for q11
    private static final String Q12 = "40,000,000"; // prize money for q12
    private static final String Q13 = "200,000,000"; // prize money for q13
    private static final String Q14 = "1,000,000,000"; // prize money for q14
    private ArrayList<String> questionMoneyList; // the list of prize money levels

    /*
     * MODIFIES: this
     * EFFECTS: prize money levels are instantiated with an empty list, prize values for each question are the added
     */
    public PrizeMoney() {
        questionMoneyList = new ArrayList<>();
        questionMoneyList.add(Q1);
        questionMoneyList.add(Q2);
        questionMoneyList.add(Q3);
        questionMoneyList.add(Q4);
        questionMoneyList.add(Q5);
        questionMoneyList.add(Q6);
        questionMoneyList.add(Q7);
        questionMoneyList.add(Q8);
        questionMoneyList.add(Q9);
        questionMoneyList.add(Q10);
        questionMoneyList.add(Q11);
        questionMoneyList.add(Q12);
        questionMoneyList.add(Q13);
        questionMoneyList.add(Q14);
    }

    // REQUIRES: n must be [1,14]
    // EFFECTS: returns prize money associated with question of player n, n = 0 returns ")
    public String getPrizeMoneyForGameTermination(int n) {
        int q;

        if (n == 0) {
            return "0";
        } else {
            q = n - 1;
        }

        return questionMoneyList.get(q);
    }

    // REQUIRES: n must be [0,13]
    // EFFECTS: returns nth element of questionMoneyList
    public String getPrizeMoneyForRules(int n) {
        return questionMoneyList.get(n);
    }

}
