package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_SlayerStaffE extends ChargedItem {
    public W_SlayerStaffE(Provider provider) {
        super(TicTac7xChargesImprovedConfig.slayer_staff_e, ItemID.SLAYER_STAFF_ENCHANTED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SLAYER_STAFF_ENCHANTED)
        };

        this.triggers.addAll(List.of(
            // Enchant.
            new OnChatMessage("The spell enchants your staff. The tatty parchment crumbles to dust.").setFixedCharges(2500),

            // Check.
            new OnChatMessage("Your staff has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Attack.
            new OnAnimationChanged(AnimationID.SLAYER_MAGICDART_CAST).isEquipped().decreaseCharges(1)
        ));
    }
}
