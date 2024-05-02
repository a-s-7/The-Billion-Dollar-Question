package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Question
public class QuestionTest {
    Question q1;

    @BeforeEach
    void runBefore(){
        q1 = new Question("What is 2 + 2", "12","14","4", "7", "4", 1);
    }

    @Test
    void testGetQuestion() {
        assertEquals("What is 2 + 2", q1.getQuestion());
    }

    @Test
    void testGetOption() {
        assertEquals("12", q1.getOption(1));
        assertEquals("14", q1.getOption(2));
        assertEquals("4", q1.getOption(3));
        assertEquals("7", q1.getOption(4));
        assertEquals("",q1.getOption(6));
        assertEquals("",q1.getOption(0));
    }

    @Test
    void testGetCorrectAnswer() {
        assertEquals("4", q1.getCorrectAnswer());
    }

    @Test
    void testGetQuestionNumber() {
       assertEquals(1, q1.getQuestionNumber());
    }

    @Test
    void testSetOption(){
        q1.setOption(1,"7");
        assertEquals("7", q1.getOption(1));

        q1.setOption(2,"12");
        assertEquals("12", q1.getOption(2));

        q1.setOption(3,"14");
        assertEquals("14", q1.getOption(3));

        q1.setOption(4,"4");
        assertEquals("4", q1.getOption(4));
    }

}