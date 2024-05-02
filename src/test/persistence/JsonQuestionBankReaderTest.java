package persistence;

import model.Player;
import model.Question;
import model.QuestionBank;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for JsonQuestionBankReader
public class JsonQuestionBankReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonQuestionBankReader reader = new JsonQuestionBankReader("./data/noSuchFile.json");

        try {
            QuestionBank bank = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderPlayerLoadedWithAllQuestions() {
        JsonQuestionBankReader reader = new JsonQuestionBankReader("./data/testQuestionBankReader.json");
        try {
            QuestionBank bank = reader.read();

            Question q1 = bank.getQuestionFromQuestionBank(1,1);
            checkQuestion("What is 1 + 1?", "1", "2", "3", "4", "2",1,  q1);

            Question q2 = bank.getQuestionFromQuestionBank(2,1);
            checkQuestion("What is 1 + 2?", "1", "2", "3", "4", "3",2,  q2);

            Question q3 = bank.getQuestionFromQuestionBank(3,1);
            checkQuestion("What is 1 + 3?", "1", "2", "3", "4", "4",3,  q3);

            Question q4 = bank.getQuestionFromQuestionBank(4,1);
            checkQuestion("What is 1 + 4?", "3", "4", "5", "6", "5",4,  q4);

            Question q5 = bank.getQuestionFromQuestionBank(5,1);
            checkQuestion("What is 1 + 5?", "3", "4", "5", "6", "6",5,  q5);

            Question q6 = bank.getQuestionFromQuestionBank(6,1);
            checkQuestion("What is 1 + 6?", "7", "8", "9", "10", "7",6,  q6);

            Question q7 = bank.getQuestionFromQuestionBank(7,1);
            checkQuestion("What is 1 + 7?", "7", "8", "9", "10", "8",7,  q7);

            Question q8 = bank.getQuestionFromQuestionBank(8,1);
            checkQuestion("What is 1 + 8?", "8", "9", "10", "11", "9",8,  q8);

            Question q9 = bank.getQuestionFromQuestionBank(9,1);
            checkQuestion("What is 1 + 9?", "10", "11", "12", "13", "10",9,  q9);

            Question q10 = bank.getQuestionFromQuestionBank(10,1);
            checkQuestion("What is 1 + 10?", "10", "11", "12", "13", "11",10,  q10);

            Question q11 = bank.getQuestionFromQuestionBank(11,1);
            checkQuestion("What is 1 + 11?", "10", "11", "12", "13", "12",11,  q11);

            Question q12 = bank.getQuestionFromQuestionBank(12,1);
            checkQuestion("What is 1 + 12?", "12", "13", "14", "15", "13",12,  q12);

            Question q13 = bank.getQuestionFromQuestionBank(13,1);
            checkQuestion("What is 1 + 13?", "12", "13", "14", "15", "14",13,  q13);

            Question q14 = bank.getQuestionFromQuestionBank(14,1);
            checkQuestion("What is 1 + 14?", "12", "13", "14", "15", "15",14,  q14);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}

