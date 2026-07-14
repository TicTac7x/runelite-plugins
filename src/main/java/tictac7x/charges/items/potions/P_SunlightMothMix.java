package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_SunlightMothMix extends _Potion {
    public P_SunlightMothMix(Provider provider) {
        super("sunlight_moth_mix", new TriggerItem[]{
            new TriggerItem(ItemId.SUNLIGHT_MOTH_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.SUNLIGHT_MOTH_MIX_2).fixedCharges(2),
        }, provider);
    }

    @Override
    public String getTooltip() {
        return "Sunlight moth mix: " + this.getTotalChargesString();
    }
}
