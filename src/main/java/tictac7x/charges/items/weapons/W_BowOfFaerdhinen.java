package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.ChargeId;
import tictac7x.charges.store.Provider;

public class W_BowOfFaerdhinen extends ChargedItem {
    public W_BowOfFaerdhinen(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.bow_of_faerdhinen, ItemId.BOW_OF_FAERDHINEN, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED).fixedCharges(ChargeId.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_ITHELL).fixedCharges(ChargeId.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_IORWERTH).fixedCharges(ChargeId.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_TRAHAEARN).fixedCharges(ChargeId.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_CADARN).fixedCharges(ChargeId.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_CRWYS).fixedCharges(ChargeId.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_MEILYR).fixedCharges(ChargeId.UNLIMITED),
            new TriggerItem(ItemId.BOW_OF_FAERDHINEN_CORRUPTED_AMLODD).fixedCharges(ChargeId.UNLIMITED),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your bow of Faerdhinen has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(1888).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Bow of faerdhinen using (?<crystalshard>.+)x Crystal shard.").matcherConsumer(m -> {
                final int crystalShards = Integer.parseInt(m.group("crystalshard"));
                increaseCharges(crystalShards * 100);
            }),
        };
    }
}
