package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class VeracsFlail extends _BarrowsItem {
    public VeracsFlail(Provider provider) {
        super("Verac's weapon", ItemID.BARROWS_VERAC_WEAPON, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_VERAC_WEAPON).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_VERAC_WEAPON_100),
            new TriggerItem(ItemID.BARROWS_VERAC_WEAPON_75),
            new TriggerItem(ItemID.BARROWS_VERAC_WEAPON_50),
            new TriggerItem(ItemID.BARROWS_VERAC_WEAPON_25),
            new TriggerItem(ItemID.BARROWS_VERAC_WEAPON_BROKEN).fixedCharges(0)
        };
    }
}