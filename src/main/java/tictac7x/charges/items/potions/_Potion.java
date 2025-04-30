package tictac7x.charges.items.potions;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class _Potion extends ChargedItem {
    public _Potion(
        final String configKey,
        final TriggerItem[] items,
        final Provider provider
    ) {
        super(TicTac7xChargesImprovedConfig.potion_ + configKey, items[0].itemId, provider);
        this.items = items;
    }
}
