package tictac7x.motherlode;


public class Sack {
    private int paydirt = 0;
    private boolean isSackUpgraded = false;

    private final int SACK_SIZE_SMALL = 108;
    private final int SACK_SIZE_UPGRADED = 189;

    public int getPaydirt() {
        return paydirt;
    }

    public void setPaydirt(final int paydirt) {
        this.paydirt = Math.min(paydirt, getSize());
    }

    private boolean isSackUpgraded() {
        return isSackUpgraded;
    }

    public void setIsSackUpgraded(final boolean isSackUpgraded) {
        this.isSackUpgraded = isSackUpgraded;
    }

    public int getSize() {
        return isSackUpgraded() ? SACK_SIZE_UPGRADED : SACK_SIZE_SMALL;
    }

    public int getSpaceLeft() {
        return getSize() - getPaydirt();
    }
}
