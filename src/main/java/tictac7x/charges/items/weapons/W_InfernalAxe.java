package tictac7x.charges.items.weapons;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.AnimationId;
import tictac7x.charges.store.ids.GraphicId;
import tictac7x.charges.store.ids.ItemId;

public class W_InfernalAxe extends ChargedItem {
    public W_InfernalAxe(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.infernal_axe, ItemId.INFERNAL_AXE_UNCHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.INFERNAL_AXE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.INFERNAL_AXE),
        };

        this.triggers = new TriggerBase[] {
            // Charge used.
            new OnGraphicChanged(GraphicId.INFERNAL_AXE_SMOKE).isEquipped().decreaseCharges(1),
        };
    }
}
