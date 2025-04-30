package tictac7x.charges.items.helms;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Provider;

public class H_KandarinHeadgear extends ChargedItem {
    public H_KandarinHeadgear(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.kandarin_headgear, ItemId.KANDARIN_HEADGEAR_3, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.KANDARIN_HEADGEAR_3),
            new TriggerItem(ItemId.KANDARIN_HEADGEAR_4).fixedCharges(Charges.UNLIMITED),
        };

        this.triggers = new TriggerBase[] {
            // Try to teleport while empty.
            new OnChatMessage("You have already used your available teleports for today. Your headgear will recharge tomorrow.").onItemClick().setFixedCharges(0),

            // Teleport.
            new OnGraphicChanged(111).onItemClick().decreaseCharges(1),

            // Daily reset.
            new OnResetDaily().specificItem(ItemId.KANDARIN_HEADGEAR_3).setFixedCharges(1),
        };
    }
}
