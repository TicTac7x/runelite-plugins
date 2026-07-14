package tictac7x.charges.items.foods;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class _Basket extends ChargedItem {
    public _Basket(
        String configKey,
        TriggerItem[] items,
        Provider provider
    ) {
        super(TicTac7xChargesImprovedConfig.baskets + "_" + configKey, items[0].itemId, provider);
        this.items = items;
    }

    @Override
    public String getConfigKey() {
        return TicTac7xChargesImprovedConfig.baskets;
    }
}
