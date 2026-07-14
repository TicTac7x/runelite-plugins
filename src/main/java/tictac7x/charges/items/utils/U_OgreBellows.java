package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class U_OgreBellows extends ChargedItem {
    public U_OgreBellows(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ogre_bellows, ItemID.EMPTY_OGRE_BELLOWS, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.EMPTY_OGRE_BELLOWS).fixedCharges(0),
            new TriggerItem(ItemID.FILLED_OGRE_BELLOW1).fixedCharges(1),
            new TriggerItem(ItemID.FILLED_OGRE_BELLOW2).fixedCharges(2),
            new TriggerItem(ItemID.FILLED_OGRE_BELLOW3).fixedCharges(3),
        };
    }
}
