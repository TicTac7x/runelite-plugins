package tictac7x.charges.items.weapons;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class W_WebweaverBow extends W_CrawsBow {
    public W_WebweaverBow(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.webweaver_bow, ItemId.WEBWEAVER_BOW_UNCHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.WEBWEAVER_BOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.WEBWEAVER_BOW),
        };
    }
}
