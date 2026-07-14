package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;

import java.util.*;

public class W_ScytheOfVitur extends ChargedItem {
    public W_ScytheOfVitur(Provider provider) {
        super(TicTac7xChargesImprovedConfig.scythe_of_vitur, ItemID.SCYTHE_OF_VITUR, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SCYTHE_OF_VITUR),
            new TriggerItem(ItemID.SCYTHE_OF_VITUR_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.SCYTHE_OF_VITUR_OR),
            new TriggerItem(ItemID.SCYTHE_OF_VITUR_UNCHARGED_OR).fixedCharges(0),
            new TriggerItem(ItemID.SCYTHE_OF_VITUR_BL),
            new TriggerItem(ItemID.SCYTHE_OF_VITUR_UNCHARGED_BL).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your (Holy s|Sanguine s|[Ss])cythe (of vitur )?has (?<charges>.+) charges (remaining|left).").setDynamicallyCharges(),

            // Charge partially full.
            new OnChatMessage("You apply an additional .+ charges to your (Holy s|Sanguine s|S)cythe of vitur. It now has (?<charges>.+) charges in total.").setDynamicallyCharges(),

            // Charge empty.
            new OnChatMessage("You apply (?<charges>.+) charges to your (Holy s|Sanguine s|S)cythe of vitur.").setDynamicallyCharges(),

            // Attack.
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.SUCCESSFUL).moreThanZeroDamage().oncePerGameTick().isEquipped().decreaseCharges(1)
        ));
    }
}
