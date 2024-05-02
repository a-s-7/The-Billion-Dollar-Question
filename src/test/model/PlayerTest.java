package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Player
public class PlayerTest {
    Player p1;
    Question q1;

    @BeforeEach
    void runBefore() {
        p1 = new Player();

    }

    @Test
    void testAddQuestionToPlayerList(){
        q1 = new Question("What is 100 / 4", "1","0", "100","25","25",1);
        p1.addQuestionToPlayerList(q1);

        assertEquals(q1, p1.getQuestionFromPlayerList(0));
    }

    @Test
    void testSetPlayerName() {
        p1.setPlayerName("Ayush");
        assertEquals("Ayush",p1.getPlayerName());
    }

    @Test
    void testGetIsPlaying() {
        assertTrue(p1.getIsPlaying());
    }

    @Test
    void testSetGameStatus() {
        p1.setGameStatus(false);
        assertFalse(p1.getIsPlaying());
    }

    @Test
    void testGetCurrentQuestionNumber() {
        assertEquals(0, p1.getCurrentQuestionNumber());
    }

    @Test
    void testIncreaseCurrentQuestionNumber() {
        p1.increaseCurrentQuestionNumber();
        assertEquals(1, p1.getCurrentQuestionNumber());
    }


    @Test
    void testGetPrizeBarrier() {
        assertEquals(0, p1.getLatestPrizeBarrier());
    }

    @Test
    void testIncreasePrizeBarrier() {
        p1.increasePrizeBarrier();
        assertEquals(1, p1.getLatestPrizeBarrier());
    }

    @Test
    void testIsSkipLifelineUsed() {
        assertFalse(p1.isSkipLifelineUsed());
    }

    @Test
    void testSkipLifelineHasBeenUsed() {
        p1.skipLifelineHasBeenUsed();
        assertTrue(p1.isSkipLifelineUsed());
    }


    @Test
    void testCanPlayerStart(){
        assertFalse(p1.getCanStartPlay());
    }

    @Test
    void testStopPlayer(){
       p1.stopPlayer();
       assertFalse(p1.getCanStartPlay());
    }

    @Test
    void testPlayerCanStart(){
        p1.playerCanStartPlay();
        assertTrue(p1.getCanStartPlay());
    }

    @Test
    void testGetMoveOn(){
        assertEquals("D", p1.getMoveOn());
    }

    @Test
    void testSetMoveOnSkip(){
        p1.setMoveOn("S");
        assertEquals("S", p1.getMoveOn());
    }

    @Test
    void testSetMoveOnQuit(){
        p1.setMoveOn("Q");
        assertEquals("Q", p1.getMoveOn());
    }
}
