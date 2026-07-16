package tictac7x.motherlode;

import net.runelite.api.gameval.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class SackTest {
    Map<Integer, Integer> varbits = new HashMap<>();
    MyVarbitManager varbitManager = new MyVarbitManager() {
        @Override
        public int getVarbitValue(int varbitId) {
            return varbits.get(varbitId);
        }
    };
    Sack sack;

    @Before
    public void setup() {
        sack = new Sack(varbitManager);
    }

    @Test
    public void PaydirtAmount() {
        varbits.put(VarbitID.MOTHERLODE_SACK_TRANSMIT, 0);
        varbits.put(VarbitID.MOTHERLODE_BIGGERSACK, 0);
        assertEquals(0, sack.getPaydirt());
        assertEquals(108, sack.getSpaceLeft());

        varbits.put(VarbitID.MOTHERLODE_SACK_TRANSMIT, 10);
        assertEquals(10, sack.getPaydirt());
        assertEquals(98, sack.getSpaceLeft());

        varbits.put(VarbitID.MOTHERLODE_SACK_TRANSMIT, 108);
        assertEquals(108, sack.getPaydirt());
        assertEquals(0, sack.getSpaceLeft());

        varbits.put(VarbitID.MOTHERLODE_BIGGERSACK, 1);
        varbits.put(VarbitID.MOTHERLODE_SACK_TRANSMIT, 189);
        assertEquals(189, sack.getPaydirt());
        assertEquals(0, sack.getSpaceLeft());

        varbits.put(VarbitID.MOTHERLODE_SACK_TRANSMIT, 0);
        assertEquals(0, sack.getPaydirt());
        assertEquals(189, sack.getSpaceLeft());

        varbits.put(VarbitID.MOTHERLODE_SACK_TRANSMIT, 20);
        assertEquals(169, sack.getSpaceLeft());
    }

    @Test
    public void Sizes() {
        varbits.put(VarbitID.MOTHERLODE_BIGGERSACK, 0);
        assertEquals(108, sack.getSize());

        varbits.put(VarbitID.MOTHERLODE_BIGGERSACK, 1);
        assertEquals(189, sack.getSize());
    }
}
