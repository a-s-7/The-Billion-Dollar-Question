package persistence;

import model.Question;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import model.Player;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonWriter
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Player p = new Player();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterDefaultPlayer() {
        try {
            Player p = new Player();
            JsonWriter writer = new JsonWriter("./data/testWriterDefaultPlayer.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefaultPlayer.json");
            p = reader.read();

            assertEquals("NEW", p.getPlayerName());
            assertTrue(p.getIsPlaying());
            assertEquals(0 ,p.getCurrentQuestionNumber());
            assertEquals(0, p.getLatestPrizeBarrier());
            assertFalse(p.isSkipLifelineUsed());
            assertEquals("D",p.getMoveOn());
            assertFalse(p.getCanStartPlay());
            assertEquals(0, p.numQuestionsInList());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterPlayerLoadedWithAllQuestions() {
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

        try {
            Player p = new Player();
            p.addQuestionToList(q1);
            p.addQuestionToList(q2);
            p.addQuestionToList(q3);
            p.addQuestionToList(q4);
            p.addQuestionToList(q5);
            p.addQuestionToList(q6);
            p.addQuestionToList(q7);
            p.addQuestionToList(q8);
            p.addQuestionToList(q9);
            p.addQuestionToList(q10);
            p.addQuestionToList(q11);
            p.addQuestionToList(q12);
            p.addQuestionToList(q13);
            p.addQuestionToList(q14);

            JsonWriter writer = new JsonWriter("./data/testWriterPlayerLoadedWithAllQuestions.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterPlayerLoadedWithAllQuestions.json");
            p = reader.read();
            assertEquals("NEW", p.getPlayerName());
            assertTrue(p.getIsPlaying());
            assertEquals(0 ,p.getCurrentQuestionNumber());
            assertEquals(0, p.getLatestPrizeBarrier());
            assertFalse(p.isSkipLifelineUsed());
            assertEquals("D",p.getMoveOn());
            assertFalse(p.getCanStartPlay());

            List<Question> playerList = p.getQuestionList();
            assertEquals(14, playerList.size());

            checkQuestion("What is 1 + 1", "2", "1", "0", "3", "2", 1,  playerList.get(0));
            checkQuestion("What is 1 + 2", "2", "3", "4", "5", "3", 2,  playerList.get(1));
            checkQuestion("What is 1 + 3", "5", "6", "4", "1", "4", 3,  playerList.get(2));
            checkQuestion("What is 1 + 4", "1", "2", "4", "5", "5", 4,  playerList.get(3));
            checkQuestion("What is 1 + 5", "6", "7", "8", "9", "6", 5,  playerList.get(4));
            checkQuestion("What is 1 + 6", "6", "7", "8", "9", "7", 6,  playerList.get(5));
            checkQuestion("What is 1 + 7", "6", "7", "8", "9", "8", 7,  playerList.get(6));
            checkQuestion("What is 1 + 8", "6", "7", "8", "9", "9", 8,  playerList.get(7));
            checkQuestion("What is 1 + 9", "10", "11", "12", "13", "10", 9, playerList.get(8));
            checkQuestion("What is 1 + 10", "10", "11", "12", "13", "11", 10, playerList.get(9));
            checkQuestion("What is 1 + 11", "10", "11", "12", "13", "12", 11,  playerList.get(10));
            checkQuestion("What is 1 + 12", "10", "11", "12", "13", "13", 12,  playerList.get(11));
            checkQuestion("What is 1 + 13", "14", "15", "16", "17", "14", 13,  playerList.get(12));
            checkQuestion("What is 1 + 14", "14", "15", "16", "17", "15", 14,  playerList.get(13));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
