package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.HitsplatTarget;
import tictac7x.charges.store.Provider;

public class W_ScytheOfVitur extends ChargedItem {
    public W_ScytheOfVitur(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.scythe_of_vitur, ItemId.SCYTHE_OF_VITUR, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SCYTHE_OF_VITUR),
            new TriggerItem(ItemId.SCYTHE_OF_VITUR_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.HOLY_SCYTHE_OF_VITUR),
            new TriggerItem(ItemId.HOLY_SCYTHE_OF_VITUR_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.SANGUINE_SCYTHE_OF_VITUR),
            new TriggerItem(ItemId.SANGUINE_SCYTHE_OF_VITUR_UNCHARGED).fixedCharges(0),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your (Holy s|Sanguine s|[Ss])cythe (of vitur )?has (?<charges>.+) charges (remaining|left).").setDynamicallyCharges(),

            // Charge partially full.
            new OnChatMessage("You apply an additional .+ charges to your (Holy s|Sanguine s|S)cythe of vitur. It now has (?<charges>.+) charges in total.").setDynamicallyCharges(),

            // Charge empty.
            new OnChatMessage("You apply (?<charges>.+) charges to your (Holy s|Sanguine s|S)cythe of vitur.").setDynamicallyCharges(),

            // Attack.
            new OnHitsplatApplied(HitsplatTarget.ENEMY).moreThanZeroDamage().oncePerGameTick().isEquipped().decreaseCharges(1),
        };
    }
}
