package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class J_PhoenixNecklace extends ChargedItem {
    public J_PhoenixNecklace(Provider provider) {
        super(TicTac7xChargesImprovedConfig.phoenix_necklace, ItemID.JEWL_NECKLACE_OF_PHOENIX, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.JEWL_NECKLACE_OF_PHOENIX).fixedCharges(1).needsToBeEquipped(),
        };
    }
}
