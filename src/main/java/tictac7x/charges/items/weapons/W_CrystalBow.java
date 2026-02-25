package tictac7x.charges.items.weapons;

import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.store.ids.AnimationId;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class W_CrystalBow extends ChargedItem {
    public W_CrystalBow(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_bow, ItemId.CRYSTAL_BOW, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_BOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_BOW),
            new TriggerItem(ItemId.CRYSTAL_BOW_FULL).fixedCharges(2500),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your crystal bow has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnAnimationChanged(AnimationId.HUMAN_BOW).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Crystal bow", "Crystal shard", 100, this)
        ));
    }
}
