package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class U_OgreBellows extends ChargedItem {
    public U_OgreBellows(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ogre_bellows, ItemId.OGRE_BELLOWS_0, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.OGRE_BELLOWS_0).fixedCharges(0),
            new TriggerItem(ItemId.OGRE_BELLOWS_1).fixedCharges(1),
            new TriggerItem(ItemId.OGRE_BELLOWS_2).fixedCharges(2),
            new TriggerItem(ItemId.OGRE_BELLOWS_3).fixedCharges(3),
        };
    }
}
