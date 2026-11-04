package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_ZoryasTome extends ChargedItem {
    public W_ZoryasTome(Provider provider) {
        super(TicTac7xChargesImprovedConfig.zoryas_tome, ItemID.ZORYAS_TOME, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ZORYAS_TOME_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.ZORYAS_TOME),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Zorya's tome has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You charge your Zorya's tome with .+ It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Uncharge.
            new OnChatMessage("You uncharge Zorya's tome.").setFixedCharges(0),

            // Attack and special attack (1 charge per attack).
            new OnGraphicChanged(
                SpotanimID.VFX_ZORYA_TOME_STANDARD_SPOTANIM,
                SpotanimID.VFX_ZORYA_TOME_STANDARD_SPOTANIM_FEMALE,
                SpotanimID.VFX_ZORYA_TOME_SPEC_SPOTANIM,
                SpotanimID.VFX_ZORYA_TOME_SPEC_SPOTANIM_FEMALE
            ).isEquipped().decreaseCharges(1)
        ));
    }
}
