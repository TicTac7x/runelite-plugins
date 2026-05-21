package tictac7x.charges.item.triggers;

public class OnItemUsed extends TriggerBase {
    public final int usedItemId;
    public final int targetItemId;
    public boolean isBothWays = false;

    public OnItemUsed(final int usedItemId, final int targetItemId) {
        this.usedItemId = usedItemId;
        this.targetItemId = targetItemId;
    }

    public OnItemUsed isBothWays() {
        this.isBothWays = true;
        return this;
    }
}
