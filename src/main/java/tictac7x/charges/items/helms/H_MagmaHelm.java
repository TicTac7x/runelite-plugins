package tictac7x.charges.items.helms;

import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class H_MagmaHelm extends H_SerpentineHelm {
    public H_MagmaHelm(Provider provider) {
        super(TicTac7xChargesImprovedConfig.magma_helm, "Magma helm", ItemId.SERPENTINE_MAGMA_HELM, new TriggerItem[]{
            new TriggerItem(ItemId.SERPENTINE_MAGMA_HELM_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.SERPENTINE_MAGMA_HELM)
        }, provider);
    }
}
