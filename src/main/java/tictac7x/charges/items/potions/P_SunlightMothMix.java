package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SunlightMothMix extends _Potion {
    public P_SunlightMothMix(Provider provider) {
        super("sunlight_moth_mix", new TriggerItem[]{
            new TriggerItem(ItemID.HUNTER_MIX_SUNMOTH_1DOSE).fixedCharges(1),
            new TriggerItem(ItemID.HUNTER_MIX_SUNMOTH_2DOSE).fixedCharges(2),
        }, provider);
    }

    @Override
    public String getTooltip() {
        return "Sunlight moth mix: " + this.getTotalChargesString();
    }
}
