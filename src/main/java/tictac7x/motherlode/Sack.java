package tictac7x.motherlode;

import net.runelite.api.gameval.*;

public class Sack {
    private final Provider provider;

    private final int SACK_SIZE_SMALL = 108;
    private final int SACK_SIZE_UPGRADED = 189;

    public Sack(Provider provider) {
        this.provider = provider;
    }

    public int getPaydirt() {
        return provider.client.getVarbitValue(VarbitID.MOTHERLODE_SACK_TRANSMIT);
    }

    private boolean isSackUpgraded() {
        return provider.client.getVarbitValue(VarbitID.MOTHERLODE_BIGGERSACK) == 1;
    }

    public int getSize() {
        return isSackUpgraded() ? SACK_SIZE_UPGRADED : SACK_SIZE_SMALL;
    }

    public int getSpaceLeft() {
        return getSize() - getPaydirt();
    }
}
