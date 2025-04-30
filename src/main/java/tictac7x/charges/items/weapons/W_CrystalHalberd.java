package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import static tictac7x.charges.store.HitsplatTarget.ENEMY;

public class W_CrystalHalberd extends ChargedItem {
    public W_CrystalHalberd(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_halberd, ItemId.CRYSTAL_HALBERD, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_HALBERD_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_HALBERD),
            new TriggerItem(ItemId.CRYSTAL_HALBERD_FULL).fixedCharges(2500),
        };
        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your crystal halberd has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnHitsplatApplied(ENEMY).oncePerGameTick().isEquipped().decreaseCharges(1),
        };
    }
}
