package tictac7x.charges.items.helms;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class H_KandarinHeadgear extends ChargedItem {
    public H_KandarinHeadgear(Provider provider) {
        super(TicTac7xChargesImprovedConfig.kandarin_headgear, ItemID.SEERS_HEADBAND_HARD, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SEERS_HEADBAND_HARD),
            new TriggerItem(ItemID.SEERS_HEADBAND_ELITE).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            // Try to teleport while empty.
            new OnChatMessage("You have already used your available teleports for today. Your headgear will recharge tomorrow.").onItemClick().setFixedCharges(0),

            // Teleport.
            new OnGraphicChanged(111).onItemClick().decreaseCharges(1),

            // Daily reset.
            new OnResetDaily().specificItem(ItemID.SEERS_HEADBAND_HARD).setFixedCharges(1)
        ));
    }
}
