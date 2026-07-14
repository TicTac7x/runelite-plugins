package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class W_EchoVenatorBow extends W_VenatorBow {
    public W_EchoVenatorBow(Provider provider) {
        super(TicTac7xChargesImprovedConfig.echo_venator_bow, ItemId.ECHO_VENATOR_BOW, provider, new TriggerItem[]{
            new TriggerItem(ItemId.ECHO_VENATOR_BOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.ECHO_VENATOR_BOW)
        });
    }
}
