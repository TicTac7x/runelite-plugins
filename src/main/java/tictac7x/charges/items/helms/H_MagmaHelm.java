package tictac7x.charges.items.helms;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class H_MagmaHelm extends H_SerpentineHelm {
    public H_MagmaHelm(Provider provider) {
        super(TicTac7xChargesImprovedConfig.magma_helm, "Magma helm", ItemID.SERPENTINE_HELM_CHARGED_RED, new TriggerItem[]{
            new TriggerItem(ItemID.SERPENTINE_HELM_RED).fixedCharges(0),
            new TriggerItem(ItemID.SERPENTINE_HELM_CHARGED_RED)
        }, provider);
    }
}
