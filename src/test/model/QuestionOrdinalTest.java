package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for QuestionOrdinal
public class QuestionOrdinalTest {
    QuestionOrdinal test;

    @BeforeEach
    void runBefore(){
       test = new QuestionOrdinal();
    }

    @Test
    void testGetOrdinal(){
        assertEquals("first", test.getOrdinal(0));
    }
}
