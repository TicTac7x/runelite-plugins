package tictac7x.charges.items.jewelry;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class J_CombatBracelet extends ChargedItem {
    public J_CombatBracelet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.combat_bracelet, ItemId.COMBAT_BRACELET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.COMBAT_BRACELET).fixedCharges(0),
            new TriggerItem(ItemId.COMBAT_BRACELET_1).fixedCharges(1),
            new TriggerItem(ItemId.COMBAT_BRACELET_2).fixedCharges(2),
            new TriggerItem(ItemId.COMBAT_BRACELET_3).fixedCharges(3),
            new TriggerItem(ItemId.COMBAT_BRACELET_4).fixedCharges(4),
            new TriggerItem(ItemId.COMBAT_BRACELET_5).fixedCharges(5),
            new TriggerItem(ItemId.COMBAT_BRACELET_6).fixedCharges(6),
        };
    }
}
