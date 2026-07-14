package tictac7x.charges.items.helms;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class H_KandarinHeadgear extends ChargedItem {
    public H_KandarinHeadgear(Provider provider) {
        super(TicTac7xChargesImprovedConfig.kandarin_headgear, ItemId.KANDARIN_HEADGEAR_3, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.KANDARIN_HEADGEAR_3),
            new TriggerItem(ItemId.KANDARIN_HEADGEAR_4).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            // Try to teleport while empty.
            new OnChatMessage("You have already used your available teleports for today. Your headgear will recharge tomorrow.").onItemClick().setFixedCharges(0),

            // Teleport.
            new OnGraphicChanged(111).onItemClick().decreaseCharges(1),

            // Daily reset.
            new OnResetDaily().specificItem(ItemId.KANDARIN_HEADGEAR_3).setFixedCharges(1)
        ));
    }
}
