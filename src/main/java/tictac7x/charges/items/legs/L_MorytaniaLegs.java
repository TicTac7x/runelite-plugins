package tictac7x.charges.items.legs;

import net.runelite.api.gameval.ItemID;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class L_MorytaniaLegs extends ChargedItem {
    public L_MorytaniaLegs(Provider provider) {
        super(TicTac7xChargesImprovedConfig.morytania_legs, ItemID.MORYTANIA_LEGS_HARD, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.MORYTANIA_LEGS_HARD),
            new TriggerItem(ItemID.MORYTANIA_LEGS_ELITE).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            // Teleport.
            new OnChatMessage("You have (?<charges>.+) teleports? remaining for today.").onItemClick().setDynamicallyCharges(),

            // Daily resets.
            new OnResetDaily().specificItem(ItemID.MORYTANIA_LEGS_HARD).setFixedCharges(5)
        ));
    }
}
