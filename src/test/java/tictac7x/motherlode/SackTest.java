package tictac7x.motherlode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SackTest extends TicTac7xMotherlodePluginTest {
    @Test
    public void PaydirtAmount() {
        assertEquals(0, sack.getPaydirt());

        sack.setPaydirt(10);
        assertEquals(10, sack.getPaydirt());

        sack.setPaydirt(200);
        assertEquals(108, sack.getPaydirt());
        assertEquals(0, sack.getSpaceLeft());

        sack.setIsSackUpgraded(true);
        sack.setPaydirt(200);
        assertEquals(189, sack.getPaydirt());
        assertEquals(0, sack.getSpaceLeft());

        sack.setPaydirt(0);
        assertEquals(0, sack.getPaydirt());
        assertEquals(189, sack.getSpaceLeft());

        sack.setPaydirt(20);
        assertEquals(169, sack.getSpaceLeft());
    }

    @Test
    public void Sizes() {
        assertEquals(108, sack.getSize());

        sack.setIsSackUpgraded(true);
        assertEquals(189, sack.getSize());
    }
}
