package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_InfernalAxe extends ChargedItem {
    public W_InfernalAxe(Provider provider) {
        super(TicTac7xChargesImprovedConfig.infernal_axe, ItemId.INFERNAL_AXE_UNCHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.INFERNAL_AXE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.INFERNAL_AXE),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Infernal axe: (?<percentage>.+)% remaining.").matcherConsumer(m -> {
                double percentage = Double.parseDouble(m.group("percentage"));
                setCharges((int) (percentage * 5000 / 100));
            }),

            // Charge used.
            new OnGraphicChanged(GraphicId.INFERNAL_AXE_SMOKE).isEquipped().decreaseCharges(1)
        ));
    }
}
