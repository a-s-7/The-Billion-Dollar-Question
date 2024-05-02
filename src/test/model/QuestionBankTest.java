package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for QuestionBank
public class QuestionBankTest {
    QuestionBank qBank;

    @BeforeEach
    void runBefore(){
        qBank = new QuestionBank();
    }

    @Test
    void testAddQuestionsToQuestionBank(){
        Question q1 = new Question("What is 1 + 1", "2", "1", "0", "3", "2", 1);
        Question q2 = new Question("What is 1 + 2", "2", "3", "4", "5", "3", 2);
        Question q3 = new Question("What is 1 + 3", "5", "6", "4", "1", "4", 3);
        Question q4 = new Question("What is 1 + 4", "1", "2", "4", "5", "5", 4);
        Question q5 = new Question("What is 1 + 5", "6", "7", "8", "9", "6", 5);
        Question q6 = new Question("What is 1 + 6", "6", "7", "8", "9", "7", 6);
        Question q7 = new Question("What is 1 + 7", "6", "7", "8", "9", "8", 7);
        Question q8 = new Question("What is 1 + 8", "6", "7", "8", "9", "9", 8);
        Question q9 = new Question("What is 1 + 9", "10", "11", "12", "13", "10", 9);
        Question q10 = new Question("What is 1 + 10", "10", "11", "12", "13", "11", 10);
        Question q11 = new Question("What is 1 + 11", "10", "11", "12", "13", "12", 11);
        Question q12 = new Question("What is 1 + 12", "10", "11", "12", "13", "13", 12);
        Question q13 = new Question("What is 1 + 13", "14", "15", "16", "17", "14", 13);
        Question q14 = new Question("What is 1 + 14", "14", "15", "16", "17", "15", 14);

        qBank.addQuestionToQuestionBank(1, q1);
        assertEquals(q1, qBank.getQuestionFromQuestionBank(1,1));

        qBank.addQuestionToQuestionBank(2, q2);
        assertEquals(q2, qBank.getQuestionFromQuestionBank(2,1));

        qBank.addQuestionToQuestionBank(3, q3);
        assertEquals(q3, qBank.getQuestionFromQuestionBank(3,1));

        qBank.addQuestionToQuestionBank(4, q4);
        assertEquals(q4, qBank.getQuestionFromQuestionBank(4,1));

        qBank.addQuestionToQuestionBank(5, q5);
        assertEquals(q5, qBank.getQuestionFromQuestionBank(5,1));

        qBank.addQuestionToQuestionBank(6, q6);
        assertEquals(q6, qBank.getQuestionFromQuestionBank(6,1));

        qBank.addQuestionToQuestionBank(7, q7);
        assertEquals(q7, qBank.getQuestionFromQuestionBank(7,1));

        qBank.addQuestionToQuestionBank(8, q8);
        assertEquals(q8, qBank.getQuestionFromQuestionBank(8,1));

        qBank.addQuestionToQuestionBank(9, q9);
        assertEquals(q9, qBank.getQuestionFromQuestionBank(9,1));

        qBank.addQuestionToQuestionBank(10, q10);
        assertEquals(q10, qBank.getQuestionFromQuestionBank(10,1));

        qBank.addQuestionToQuestionBank(11, q11);
        assertEquals(q11, qBank.getQuestionFromQuestionBank(11,1));

        qBank.addQuestionToQuestionBank(12, q12);
        assertEquals(q12, qBank.getQuestionFromQuestionBank(12,1));

        qBank.addQuestionToQuestionBank(13, q13);
        assertEquals(q13, qBank.getQuestionFromQuestionBank(13,1));

        qBank.addQuestionToQuestionBank(14, q14);
        assertEquals(q14, qBank.getQuestionFromQuestionBank(14,1));

    }
}
