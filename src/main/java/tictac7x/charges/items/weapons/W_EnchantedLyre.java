package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_EnchantedLyre extends ChargedItem {
    public W_EnchantedLyre(Provider provider) {
        super(TicTac7xChargesImprovedConfig.enchanted_lyre, ItemID.VIKING_ENCHANTED_STRUNG_LYRE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.VIKING_ENCHANTED_STRUNG_LYRE).fixedCharges(0),
            new TriggerItem(ItemID.MAGIC_STRUNG_LYRE).fixedCharges(1),
            new TriggerItem(ItemID.MAGIC_STRUNG_LYRE_2).fixedCharges(2),
            new TriggerItem(ItemID.MAGIC_STRUNG_LYRE_3).fixedCharges(3),
            new TriggerItem(ItemID.MAGIC_STRUNG_LYRE_4).fixedCharges(4),
            new TriggerItem(ItemID.MAGIC_STRUNG_LYRE_5).fixedCharges(5),
            new TriggerItem(ItemID.MAGIC_STRUNG_LYRE_INFINITE).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            new OnMenuEntryAdded("Play").replaceOption("Teleport")
        ));
    }
}
