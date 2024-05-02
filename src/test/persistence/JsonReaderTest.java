package persistence;

import model.Player;
import model.Question;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Unit tests for JsonReader
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");

        try {
            Player p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderDefaultPlayerWithEmptyPlayerList() {
        JsonReader reader = new JsonReader("./data/testReaderDefaultPlayer.json");

        try {
            Player p = reader.read();
            assertEquals("", p.getPlayerName());
            assertTrue(p.getIsPlaying());
            assertEquals(0 ,p.getCurrentQuestionNumber());
            assertEquals(0, p.getLatestPrizeBarrier());
            assertFalse(p.isSkipLifelineUsed());
            assertEquals("D",p.getMoveOn());
            assertFalse(p.getCanStartPlay());
            assertEquals(0, p.numQuestionsInList());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderPlayerLoadedWithAllQuestions() {
        JsonReader reader = new JsonReader("./data/testReaderPlayerLoadedWithAllQuestions.json");
        try {
            Player p = reader.read();
            assertEquals("", p.getPlayerName());
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
            fail("Couldn't read from file");
        }
    }
}
