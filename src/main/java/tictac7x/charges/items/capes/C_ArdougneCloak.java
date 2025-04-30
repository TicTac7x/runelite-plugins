package tictac7x.charges.items.capes;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Provider;

public class C_ArdougneCloak extends ChargedItem {
    public C_ArdougneCloak(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ardougne_cloak, ItemId.ARDOUGNE_CLOAK_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ARDOUGNE_CLOAK_1).fixedCharges(Charges.UNLIMITED),
            new TriggerItem(ItemId.ARDOUGNE_CLOAK_2),
            new TriggerItem(ItemId.ARDOUGNE_CLOAK_3),
            new TriggerItem(ItemId.ARDOUGNE_CLOAK_4).fixedCharges(Charges.UNLIMITED),
        };

        this.triggers = new TriggerBase[] {
            new OnChatMessage("You have used (?<used>.+) of your (?<total>.+) Ardougne Farm teleports for today.").setDifferenceCharges(),
            new OnResetDaily().specificItem(ItemId.ARDOUGNE_CLOAK_2).setFixedCharges(3),
            new OnResetDaily().specificItem(ItemId.ARDOUGNE_CLOAK_3).setFixedCharges(5),
        };
    }
}
