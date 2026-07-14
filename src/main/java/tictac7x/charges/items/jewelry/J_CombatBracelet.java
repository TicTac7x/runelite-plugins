package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class J_CombatBracelet extends ChargedItem {
    public J_CombatBracelet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.combat_bracelet, ItemID.JEWL_BRACELET_OF_COMBAT, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.JEWL_BRACELET_OF_COMBAT).fixedCharges(0),
            new TriggerItem(ItemID.JEWL_BRACELET_OF_COMBAT_1).fixedCharges(1),
            new TriggerItem(ItemID.JEWL_BRACELET_OF_COMBAT_2).fixedCharges(2),
            new TriggerItem(ItemID.JEWL_BRACELET_OF_COMBAT_3).fixedCharges(3),
            new TriggerItem(ItemID.JEWL_BRACELET_OF_COMBAT_4).fixedCharges(4),
            new TriggerItem(ItemID.JEWL_BRACELET_OF_COMBAT_5).fixedCharges(5),
            new TriggerItem(ItemID.JEWL_BRACELET_OF_COMBAT_6).fixedCharges(6),
        };
    }
}
