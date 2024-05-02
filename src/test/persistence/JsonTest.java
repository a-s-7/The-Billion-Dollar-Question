package persistence;

import model.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Method corresponding to checking question object
public class JsonTest {
    protected void checkQuestion(String q, String a, String b, String c, String d, String answer, int num, Question z) {
        assertEquals(q, z.getQuestion());
        assertEquals(a, z.getOption(1));
        assertEquals(b, z.getOption(2));
        assertEquals(c, z.getOption(3));
        assertEquals(d, z.getOption(4));
        assertEquals(answer, z.getCorrectAnswer());
        assertEquals(num, z.getQuestionNumber());
    }
}
