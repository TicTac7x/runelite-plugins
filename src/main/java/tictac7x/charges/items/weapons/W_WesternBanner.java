package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Provider;

public class W_WesternBanner extends ChargedItem {
    public W_WesternBanner(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.western_banner, ItemId.WESTERN_BANNER_3, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.WESTERN_BANNER_3),
            new TriggerItem(ItemId.WESTERN_BANNER_4).fixedCharges(Charges.UNLIMITED),
        };

        this.triggers = new TriggerBase[]{
            // Teleport.
            new OnMenuOptionClicked("Teleport").hasItemId(ItemId.WESTERN_BANNER_3).setFixedCharges(0),

            // Teleport already used.
            new OnChatMessage("You have already used your available teleports for today. Try again tomorrow after the standard has recharged.").onItemClick().setFixedCharges(0),

            // Daily reset.
            new OnResetDaily().requiredItem(ItemId.WESTERN_BANNER_3).setFixedCharges(1),
        };
    }
}
