package tictac7x.charges.items.helms;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class H_TanzaniteHelm extends H_SerpentineHelm {
    public H_TanzaniteHelm(Provider provider) {
        super(TicTac7xChargesImprovedConfig.tanzanite_helm, "Tanzanite helm", ItemID.SERPENTINE_HELM_CHARGED_CYAN, new TriggerItem[]{
            new TriggerItem(ItemID.SERPENTINE_HELM_CYAN).fixedCharges(0),
            new TriggerItem(ItemID.SERPENTINE_HELM_CHARGED_CYAN)
        }, provider);
    }
}
