package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class J_CastleWarsBracelet extends ChargedItem {
    public J_CastleWarsBracelet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.castle_wars_bracelet, ItemID.JEWL_CASTLEWARS_BRACELET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.JEWL_CASTLEWARS_BRACELET).fixedCharges(1).needsToBeEquipped(),
            new TriggerItem(ItemID.JEWL_CASTLEWARS_BRACELET2).fixedCharges(2).needsToBeEquipped(),
            new TriggerItem(ItemID.JEWL_CASTLEWARS_BRACELET3).fixedCharges(3).needsToBeEquipped(),
        };
    }
}
