package tictac7x.charges.items.capes;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.List;

public class C_ArdougneCloak extends ChargedItem {
    public C_ArdougneCloak(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ardougne_cloak, ItemID.ARDY_CAPE_EASY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ARDY_CAPE_EASY).unlimitedCharges(),
            new TriggerItem(ItemID.ARDY_CAPE_MEDIUM),
            new TriggerItem(ItemID.ARDY_CAPE_HARD),
            new TriggerItem(ItemID.ARDY_CAPE_ELITE).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("You have used (?<used>.+) of your (?<total>.+) Ardougne Farm teleports for today.").setDifferenceCharges(),
            new OnResetDaily().specificItem(ItemID.ARDY_CAPE_MEDIUM).setFixedCharges(3),
            new OnResetDaily().specificItem(ItemID.ARDY_CAPE_HARD).setFixedCharges(5)
        ));
    }
}
