package tictac7x.charges.items.utils;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

public class U_CrystalSaw extends ChargedItem {
    public U_CrystalSaw(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_saw, ItemId.CRYSTAL_SAW, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_SAW),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your saw has (?<charges>.+) charges? left.").setDynamicallyCharges(),
        };
    }
}
