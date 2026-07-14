package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class U_CrystalSaw extends ChargedItem {
    public U_CrystalSaw(Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_saw, ItemID.EYEGLO_CRYSTAL_SAW, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.EYEGLO_CRYSTAL_SAW),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your saw has (?<charges>.+) charges? left.").setDynamicallyCharges()
        ));
    }
}
