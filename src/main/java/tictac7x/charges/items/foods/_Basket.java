package tictac7x.charges.items.foods;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.awt.*;

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

    @Override
    public Color getTextColor(final int itemId) {
        for (final TriggerItem triggerItem : items) {
            if (triggerItem.itemId == itemId && triggerItem.fixedCharges.isPresent()) {
                switch (triggerItem.fixedCharges.get()) {
                    case 5:
                    case 4:
                        return provider.config.get4DoseColor();
                    case 3:
                        return provider.config.get3DoseColor();
                    case 2:
                        return provider.config.get2DoseColor();
                    case 1:
                        return provider.config.get1DoseColor();
                }
            }
        }

        return super.getTextColor(itemId);
    }
}
