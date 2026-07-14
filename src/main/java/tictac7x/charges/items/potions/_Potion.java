package tictac7x.charges.items.potions;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.awt.*;

public class _Potion extends ChargedItem {
    public _Potion(
        String configKey,
        TriggerItem[] items,
        Provider provider
    ) {
        super(TicTac7xChargesImprovedConfig.potions + "_" + configKey, items[0].itemId, provider);
        this.items = items;
    }

    @Override
    public String getConfigKey() {
        return TicTac7xChargesImprovedConfig.potions;
    }

    @Override
    public Color getTextColor(int itemId) {
        for (TriggerItem triggerItem : items) {
            if (triggerItem.itemId == itemId && triggerItem.fixedCharges.isPresent()) {
                switch (triggerItem.fixedCharges.get()) {
                    case 4:
                        return provider.config.get4DoseColor();
                    case 3:
                        return provider.config.get3DoseColor();
                    case 2:
                        return items.length == 2 ? provider.config.get4DoseColor() : provider.config.get2DoseColor();
                    case 1:
                        return provider.config.get1DoseColor();
                }
            }
        }

        return super.getTextColor(itemId);
    }
}
