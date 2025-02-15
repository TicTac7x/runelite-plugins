package tictac7x.charges.item.triggers;

import java.util.Optional;

public class OnResetDaily extends TriggerBase {
    public final int resetSpecificItem;

    public OnResetDaily(final int resetSpecificItem) {
        this.resetSpecificItem = resetSpecificItem;
    }
}
