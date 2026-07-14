package tictac7x.charges.items.shields;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.*;

public class S_Chronicle extends ChargedItem {
    public S_Chronicle(Provider provider) {
        super(TicTac7xChargesImprovedConfig.chronicle, ItemID.CHRONICLE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CHRONICLE),
        };

        this.triggers.addAll(List.of(
            // Check plural.
            new OnChatMessage("Your book has (?<charges>.+) charges? left.").setDynamicallyCharges().onItemClick(),

            // Check single.
            new OnChatMessage("You have one charge left in your book.").setFixedCharges(1).onItemClick()
        ));
    }
}
