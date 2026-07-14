package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_IbansStaff extends ChargedItem {
    public W_IbansStaff(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ibans_staff, ItemID.IBANSTAFF, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.IBANSTAFF),
            new TriggerItem(ItemID.BROKENIBANSTAFF),
            new TriggerItem(ItemID.IBANSTAFF_UPGRADED),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("You have (?<charges>.+) charges left on the staff.").setDynamicallyCharges().onItemClick(),

            // Attack.
            new OnGraphicChanged(87).isEquipped().decreaseCharges(1)
        ));
    }
}
