package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class W_PharaohsSceptre extends ChargedItem {
    public W_PharaohsSceptre(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.pharaohs_sceptre, ItemId.PHARAOHS_SCEPTRE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.PHARAOHS_SCEPTRE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.PHARAOHS_SCEPTRE_INITIAL),
            new TriggerItem(ItemId.PHARAOHS_SCEPTRE),
        };
        
        this.triggers = new TriggerBase[]{
            // Check and automatic messages.
            new OnChatMessage("Your sceptre has (?<charges>.+) charges? left.").setDynamicallyCharges().onItemClick(),

            // Charge non-empty sceptre.
            new OnChatMessage("Right, you already had .+ charges?, and I don't give discounts. That means .+ artefacts gives you (?<charges>.+) charges?. Now be on your way.").increaseDynamically(),

            // Charge empty sceptre.
            new OnChatMessage("Right, .+ artefacts gives you (?<charges>.+) charges. Now be on your way.").setDynamicallyCharges(),

            // Teleport.
            new OnGraphicChanged(715).decreaseCharges(1),
        };
    }
}
