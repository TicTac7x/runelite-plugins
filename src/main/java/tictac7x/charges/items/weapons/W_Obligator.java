package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;

import java.util.*;

public class W_Obligator extends ChargedItem {
    public W_Obligator(Provider provider) {
        super(TicTac7xChargesImprovedConfig.obligator, ItemID.OBLIGATOR, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.OBLIGATOR),
            new TriggerItem(ItemID.OBLIGATOR_UNCHARGED).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your [Oo]bligator has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You charge your Obligator with .+ It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Uncharge.
            new OnChatMessage("You uncharge the Obligator.").setFixedCharges(0),

            // Attack.
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.SUCCESSFUL).moreThanZeroDamage().oncePerGameTick().isEquipped().decreaseCharges(1)
        ));
    }
}
