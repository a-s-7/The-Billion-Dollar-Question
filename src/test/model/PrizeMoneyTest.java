package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for PrizeMoney
public class PrizeMoneyTest {
    PrizeMoney gameMoneyLevels;

    @BeforeEach

    void runBefore(){
        gameMoneyLevels = new PrizeMoney( );
    }

    @Test
    void testGetPrizeMoneyForGameTermination() {
        assertEquals("0", gameMoneyLevels.getPrizeMoneyForGameTermination(0));
        assertEquals("1,000", gameMoneyLevels.getPrizeMoneyForGameTermination(1));
        assertEquals("200,000", gameMoneyLevels.getPrizeMoneyForGameTermination(7));
        assertEquals("1,000,000,000", gameMoneyLevels.getPrizeMoneyForGameTermination(14));
    }


    @Test
    void testGetPrizeMoneyForRules(){
        assertEquals("1,000", gameMoneyLevels.getPrizeMoneyForRules(0));
        assertEquals("200,000", gameMoneyLevels.getPrizeMoneyForRules(6));
        assertEquals("1,000,000,000", gameMoneyLevels.getPrizeMoneyForRules(13));
    }
}