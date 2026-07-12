package tictac7x.charges.items.capes;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class C_ArdougneCloak extends ChargedItem {
    public C_ArdougneCloak(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ardougne_cloak, ItemId.ARDOUGNE_CLOAK_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ARDOUGNE_CLOAK_1).unlimitedCharges(),
            new TriggerItem(ItemId.ARDOUGNE_CLOAK_2),
            new TriggerItem(ItemId.ARDOUGNE_CLOAK_3),
            new TriggerItem(ItemId.ARDOUGNE_CLOAK_4).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("You have used (?<used>.+) of your (?<total>.+) Ardougne Farm teleports for today.").setDifferenceCharges(),
            new OnResetDaily().specificItem(ItemId.ARDOUGNE_CLOAK_2).setFixedCharges(3),
            new OnResetDaily().specificItem(ItemId.ARDOUGNE_CLOAK_3).setFixedCharges(5)
        ));
    }
}
