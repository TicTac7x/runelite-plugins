package tictac7x.charges.items.helms;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class H_TanzaniteHelm extends H_SerpentineHelm {
    public H_TanzaniteHelm(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.tanzanite_helm, ItemId.SERPENTINE_TANZANITE_HELM, new TriggerItem[]{
            new TriggerItem(ItemId.SERPENTINE_TANZANITE_HELM_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.SERPENTINE_TANZANITE_HELM)
        }, provider);
    }
}
