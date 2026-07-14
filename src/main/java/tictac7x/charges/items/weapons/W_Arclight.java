package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

import static tictac7x.charges.store.enums.HitsplatTarget.*;

public class W_Arclight extends ChargedItem {
    private boolean attacked = false;

    public W_Arclight(Provider provider) {
        super(TicTac7xChargesImprovedConfig.arclight, ItemID.ARCLIGHT, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ARCLIGHT),
            new TriggerItem(ItemID.ARCLIGHT_INACTIVE).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("Your arclight has (?<charges>.+) charges?( left)?.").setDynamicallyCharges(),
            new OnChatMessage("Your arclight can perform (?<charges>.+) more attacks.").setDynamicallyCharges(),
            new OnChatMessage("Your arclight has degraded.").setFixedCharges(0),

            // Attack
            new OnAnimationChanged(AnimationID.HUMAN_SWORD_SLASH, AnimationID.HUMAN_SWORD_STAB).isEquipped().decreaseCharges(1).consumer(() -> {
                attacked = true;
            }),
            new OnHitsplatApplied(ENEMY, HitsplatGroup.BLOCKED).isEquipped().consumer(() -> {
                if (attacked) {
                    increaseCharges(1);
                    attacked = false;
                }
            }),
            new OnHitsplatApplied(ENEMY, HitsplatGroup.SUCCESSFUL).isEquipped().consumer(() -> {
                attacked = false;
            })
        ));
    }
}
