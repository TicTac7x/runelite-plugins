package tictac7x.charges.items.foods;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class _Basket extends ChargedItem {
    public _Basket(
        final String configKey,
        final TriggerItem[] items,
        final Provider provider
    ) {
        super(TicTac7xChargesImprovedConfig.baskets + "_" + configKey, items[0].itemId, provider);
        this.items = items;
    }

    @Override
    public String getConfigKey() {
        return TicTac7xChargesImprovedConfig.baskets;
    }
}
