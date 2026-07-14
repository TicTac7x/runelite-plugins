package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class W_WebweaverBow extends W_CrawsBow {
    public W_WebweaverBow(Provider provider) {
        super(TicTac7xChargesImprovedConfig.webweaver_bow, ItemID.WILD_CAVE_WEBWEAVER_UNCHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.WILD_CAVE_WEBWEAVER_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.WILD_CAVE_WEBWEAVER_CHARGED),
        };
    }
}
