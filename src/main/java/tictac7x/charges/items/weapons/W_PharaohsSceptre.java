package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_PharaohsSceptre extends ChargedItem {
    public W_PharaohsSceptre(Provider provider) {
        super(TicTac7xChargesImprovedConfig.pharaohs_sceptre, ItemId.PHARAOHS_SCEPTRE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.PHARAOHS_SCEPTRE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.PHARAOHS_SCEPTRE_INITIAL),
            new TriggerItem(ItemId.PHARAOHS_SCEPTRE),
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
