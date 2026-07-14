package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_CrawsBow extends ChargedItem {
    public W_CrawsBow(Provider provider) {
        this(TicTac7xChargesImprovedConfig.craws_bow, ItemId.CRAWS_BOW_UNCHARGED, provider);

        this.items = new TriggerItem[]{
                new TriggerItem(ItemId.CRAWS_BOW_UNCHARGED).fixedCharges(0),
                new TriggerItem(ItemId.CRAWS_BOW),
        };
    }

    protected W_CrawsBow(String configKey, int itemId, Provider provider) {
        super(configKey, itemId, provider);

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your bow has (?<charges>.+) charges? left powering it.").onItemClick().setDynamicallyCharges(),

            // Activate.
            new OnChatMessage("You use 1000 ether to activate the weapon.").onItemClick().setFixedCharges(0),

            // Charge.
            new OnChatMessage("You add( a further)? .* revenant ether to your weapon, giving it a total of (?<charges>.+) charges.").onItemClick().setDynamicallyCharges(),

            // Attack.
            new OnAnimationChanged(AnimationId.HUMAN_BOW).isEquipped().decreaseCharges(1)
        ));
    }
}
