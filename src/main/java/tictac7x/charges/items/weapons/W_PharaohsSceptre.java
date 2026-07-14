package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_PharaohsSceptre extends ChargedItem {
    public W_PharaohsSceptre(Provider provider) {
        super(TicTac7xChargesImprovedConfig.pharaohs_sceptre, ItemID.PHARAOHS_SCEPTRE_CHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE).fixedCharges(0),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_CHARGED_INITIAL),
            new TriggerItem(ItemID.PHARAOHS_SCEPTRE_CHARGED),
        };

        this.triggers.addAll(List.of(
            // Check and automatic messages.
            new OnChatMessage("Your sceptre has (?<charges>.+) charges? left.").setDynamicallyCharges().onItemClick(),

            // Charge non-empty sceptre.
            new OnChatMessage("Right, you already had .+ charges?, and I don't give discounts. That means .+ artefacts gives you (?<charges>.+) charges?. Now be on your way.").increaseDynamically(),

            // Charge empty sceptre.
            new OnChatMessage("Right, .+ artefacts gives you (?<charges>.+) charges. Now be on your way.").setDynamicallyCharges(),

            // Teleport.
            new OnGraphicChanged(715).decreaseCharges(1)
        ));
    }
}
