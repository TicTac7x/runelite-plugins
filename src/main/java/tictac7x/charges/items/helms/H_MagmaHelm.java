package tictac7x.charges.items.helms;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class H_MagmaHelm extends H_SerpentineHelm {
    public H_MagmaHelm(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.magma_helm, ItemId.SERPENTINE_MAGMA_HELM, new TriggerItem[]{
            new TriggerItem(ItemId.SERPENTINE_MAGMA_HELM_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.SERPENTINE_MAGMA_HELM)
        }, provider);
    }
}
