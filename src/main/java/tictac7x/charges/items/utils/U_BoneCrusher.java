package tictac7x.charges.items.utils;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.ItemId;
import net.runelite.api.Skill;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.store.Provider;

import java.util.List;

public class U_BoneCrusher extends ChargedItemWithStatus {
    public U_BoneCrusher(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.bonecrusher, ItemId.BONECRUSHER, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BONECRUSHER),
            new TriggerItem(ItemId.BONECRUSHER_NECKLACE)
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("The bonecrusher( necklace)? has no charges.").setFixedCharges(0),
            new OnChatMessage("The bonecrusher( necklace)? has one charge.").setFixedCharges(1),
            new OnChatMessage("(The|Your) bonecrusher( necklace)? has (?<charges>.+) charges?( left)?. It is active").setDynamicallyCharges().activate(),
            new OnChatMessage("(The|Your) bonecrusher( necklace)? has (?<charges>.+) charges?( left)?. It has been deactivated").setDynamicallyCharges().deactivate(),
            new OnChatMessage("(The|Your) bonecrusher( necklace)? has (?<charges>.+) charges?( left)?.").setDynamicallyCharges(),

            // Uncharge.
            new OnChatMessage("You remove all the charges from the bonecrusher( necklace)?.").setFixedCharges(0),

            // Ran out.
            new OnChatMessage("Your bonecrusher( necklace)? has run out of charges.").setFixedCharges(0),

            // Activate.
            new OnChatMessage("The bonecrusher( necklace)? has been deactivated").deactivate(),

            // Deactivate.
            new OnChatMessage("The bonecrusher( necklace)? is active").activate(),

            // Automatic bury.
            new OnXpDrop(Skill.PRAYER).isActivated().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Bonecrusher( necklace)?", "Ecto-token", 25, this),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }
}
