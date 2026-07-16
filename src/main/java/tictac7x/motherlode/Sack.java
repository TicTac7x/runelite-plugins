package tictac7x.motherlode;


import net.runelite.api.gameval.*;

import java.util.function.*;

public class Sack {
    private final MyVarbitManager varbitManager;

    private final int SACK_SIZE_SMALL = 108;
    private final int SACK_SIZE_UPGRADED = 189;

    public Sack(MyVarbitManager varbitManager) {
        this.varbitManager = varbitManager;
    }

    public int getPaydirt() {
        return varbitManager.getVarbitValue(VarbitID.MOTHERLODE_SACK_TRANSMIT);
    }

    private boolean isSackUpgraded() {
        return varbitManager.getVarbitValue(VarbitID.MOTHERLODE_BIGGERSACK) == 1;
    }

    public int getSize() {
        return isSackUpgraded() ? SACK_SIZE_UPGRADED : SACK_SIZE_SMALL;
    }

    public int getSpaceLeft() {
        return getSize() - getPaydirt();
    }
}
