package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents a question having a question, optionA, optionB, optionC, option D, correctAnswer, and questionNumber
public class Question implements Writable {
    private String question; // question's question
    private String optionA; // question's option A
    private String optionB; // question's option B
    private String optionC; // question's option C
    private String optionD; // question's option D
    private String correctAnswer; // correct answer of question
    private int questionNumber; // question number


    // REQUIRES: q, a, b, c, d, answer must correspond to question, num must be > 0
    // EFFECTS: Question's question is set to string q, optionA is set to string a, optionB is set to string B,
    //         optionC is set to string C, optionD is set to string D, correctAnswer is set to string answer, and
    //         questionNumber is set to a positive integer num
    public Question(String q, String a, String b, String c, String d, String answer, int num) {
        question = q;
        optionA = a;
        optionB = b;
        optionC = c;
        optionD = d;
        correctAnswer = answer;
        questionNumber = num;
    }

    // EFFECTS: Converts question to JSON Object for file writing
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("option A", optionA);
        json.put("option B", optionB);
        json.put("option C", optionC);
        json.put("option D", optionD);
        json.put("correct answer", correctAnswer);
        json.put("question number", questionNumber);
        return json;
    }

    // Getter
    // EFFECTS: returns Question's question
    public String getQuestion() {
        return question;
    }

    // Getter
    // EFFECTS: returns Question's options depending on option number (1 is A, 2 is B, 3 is C, 4 is D)
    public String getOption(int option) {
        if (option == 1) {
            return optionA;
        } else if (option == 2) {
            return optionB;
        } else if (option == 3) {
            return optionC;
        } else if (option == 4) {
            return optionD;
        } else {
            return "";
        }
    }

    // Setter
    // MODIFIES: this
    // EFFECTS: given an integer (1, 4), changes the corresponding option for this to a new option
    public void setOption(int option, String newOp) {
        if (option == 1) {
            optionA = newOp;
        } else if (option == 2) {
            optionB = newOp;
        } else if (option == 3) {
            optionC = newOp;
        } else {
            optionD = newOp;
        }
    }

    // Getter
    // EFFECTS: returns Question's correct answer
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Getter
    // EFFECTS: returns question number
    public int getQuestionNumber() {
        return questionNumber;
    }

}
