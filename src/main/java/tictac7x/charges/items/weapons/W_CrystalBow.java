package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_CrystalBow extends ChargedItem {
    public W_CrystalBow(Provider provider) {
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
