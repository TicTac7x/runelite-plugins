package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_InfernalAxe extends ChargedItem {
    public W_InfernalAxe(Provider provider) {
        super(TicTac7xChargesImprovedConfig.infernal_axe, ItemID.INFERNAL_AXE_EMPTY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.INFERNAL_AXE_EMPTY).fixedCharges(0),
            new TriggerItem(ItemID.INFERNAL_AXE),
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
