package tictac7x.charges.items.utils;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Provider;

public class U_EternalTeleportCrystal extends ChargedItem {
    public U_EternalTeleportCrystal(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.eternal_teleport_crystal, ItemId.ETERNAL_TELEPORT_CRYSTAL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ETERNAL_TELEPORT_CRYSTAL).fixedCharges(Charges.UNLIMITED),
        };
    }
}
