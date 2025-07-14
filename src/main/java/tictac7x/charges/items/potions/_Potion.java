package tictac7x.charges.items.potions;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.awt.Color;

public class _Potion extends ChargedItem {
    public _Potion(
        final String configKey,
        final TriggerItem[] items,
        final Provider provider
    ) {
        super(TicTac7xChargesImprovedConfig.potions + "_" + configKey, items[0].itemId, provider);
        this.items = items;
    }

    @Override
    public String getConfigKey() {
        return TicTac7xChargesImprovedConfig.potions;
    }

    @Override
    public Color getTextColor(final int itemId) {
        for (final TriggerItem triggerItem : items) {
            if (triggerItem.itemId == itemId && triggerItem.fixedCharges.isPresent()) {
                switch (triggerItem.fixedCharges.get()) {
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
