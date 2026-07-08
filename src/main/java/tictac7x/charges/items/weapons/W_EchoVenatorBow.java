package tictac7x.charges.items.weapons;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class W_EchoVenatorBow extends W_VenatorBow {
    public W_EchoVenatorBow(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.echo_venator_bow, ItemId.ECHO_VENATOR_BOW, provider, new TriggerItem[]{
            new TriggerItem(ItemId.ECHO_VENATOR_BOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.ECHO_VENATOR_BOW)
        });
    }
}
