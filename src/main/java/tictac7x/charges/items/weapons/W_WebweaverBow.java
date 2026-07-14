package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class W_WebweaverBow extends W_CrawsBow {
    public W_WebweaverBow(Provider provider) {
        super(TicTac7xChargesImprovedConfig.webweaver_bow, ItemId.WEBWEAVER_BOW_UNCHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.WEBWEAVER_BOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.WEBWEAVER_BOW),
        };
    }
}
