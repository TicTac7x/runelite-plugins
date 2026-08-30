package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_BowOfFaerdhinen extends ChargedItem {
    public W_BowOfFaerdhinen(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bow_of_faerdhinen, ItemID.BOW_OF_FAERDHINEN, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INFINITE).unlimitedCharges(),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INFINITE_ITHELL).unlimitedCharges(),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INFINITE_IORWERTH).unlimitedCharges(),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INFINITE_TRAHAEARN).unlimitedCharges(),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INFINITE_CADARN).unlimitedCharges(),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INFINITE_CRWYS).unlimitedCharges(),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INFINITE_MEILYR).unlimitedCharges(),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INFINITE_AMLODD).unlimitedCharges(),
            new TriggerItem(ItemID.BOW_OF_FAERDHINEN_INFINITE_DEADMAN).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your bow of Faerdhinen has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(1888).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Bow of Faerdhinen", "Crystal shard", 100, this)
        ));
    }
}
