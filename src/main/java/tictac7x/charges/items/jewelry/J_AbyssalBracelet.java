package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class J_AbyssalBracelet extends ChargedItem {
    public J_AbyssalBracelet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.abyssal_bracelet, ItemID.JEWL_RUNERUNNING_BRACELET_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.JEWL_RUNERUNNING_BRACELET_1).fixedCharges(1),
            new TriggerItem(ItemID.JEWL_RUNERUNNING_BRACELET_2).fixedCharges(2),
            new TriggerItem(ItemID.JEWL_RUNERUNNING_BRACELET_3).fixedCharges(3),
            new TriggerItem(ItemID.JEWL_RUNERUNNING_BRACELET_4).fixedCharges(4),
            new TriggerItem(ItemID.JEWL_RUNERUNNING_BRACELET_5).fixedCharges(5),
        };
    }
}
