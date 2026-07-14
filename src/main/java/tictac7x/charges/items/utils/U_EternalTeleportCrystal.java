package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

public class U_EternalTeleportCrystal extends ChargedItem {
    public U_EternalTeleportCrystal(Provider provider) {
        super(TicTac7xChargesImprovedConfig.eternal_teleport_crystal, ItemId.ETERNAL_TELEPORT_CRYSTAL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ETERNAL_TELEPORT_CRYSTAL).unlimitedCharges(),
        };
    }
}
