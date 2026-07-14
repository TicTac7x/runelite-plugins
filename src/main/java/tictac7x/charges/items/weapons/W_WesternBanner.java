package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_WesternBanner extends ChargedItem {
    public W_WesternBanner(Provider provider) {
        super(TicTac7xChargesImprovedConfig.western_banner, ItemID.WESTERN_BANNER_HARD, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.WESTERN_BANNER_HARD),
            new TriggerItem(ItemID.WESTERN_BANNER_ELITE).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            // Teleport.
            new OnMenuOptionClicked("Teleport").hasItemId (ItemID.WESTERN_BANNER_HARD).setFixedCharges(0),

            // Teleport already used.
            new OnChatMessage("You have already used your available teleports for today. Try again tomorrow after the standard has recharged.").onItemClick().setFixedCharges(0),

            // Daily reset.
            new OnResetDaily().requiredItem (ItemID.WESTERN_BANNER_HARD).setFixedCharges(1)
        ));
    }
}
