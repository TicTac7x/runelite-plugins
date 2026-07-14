package tictac7x.charges.items.helms;

import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class H_TanzaniteHelm extends H_SerpentineHelm {
    public H_TanzaniteHelm(Provider provider) {
        super(TicTac7xChargesImprovedConfig.tanzanite_helm, "Tanzanite helm", ItemId.SERPENTINE_TANZANITE_HELM, new TriggerItem[]{
            new TriggerItem(ItemId.SERPENTINE_TANZANITE_HELM_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.SERPENTINE_TANZANITE_HELM)
        }, provider);
    }
}
