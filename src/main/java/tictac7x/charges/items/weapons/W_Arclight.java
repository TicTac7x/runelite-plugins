package tictac7x.charges.items.weapons;

import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.store.enums.HitsplatGroup;
import tictac7x.charges.store.ids.AnimationId;
import tictac7x.charges.store.ids.GraphicId;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import static tictac7x.charges.store.enums.HitsplatTarget.ENEMY;

public class W_Arclight extends ChargedItem {
    private boolean attacked = false;

    public W_Arclight(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.arclight, ItemId.ARCLIGHT, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ARCLIGHT),
            new TriggerItem(ItemId.ARCLIGHT_UNCHARGED).fixedCharges(0),
        };

        this.triggers = new TriggerBase[] {
            new OnChatMessage("Your arclight has (?<charges>.+) charges?( left)?.").setDynamicallyCharges(),
            new OnChatMessage("Your arclight can perform (?<charges>.+) more attacks.").setDynamicallyCharges(),
            new OnChatMessage("Your arclight has degraded.").setFixedCharges(0),

            // Attack
            new OnAnimationChanged(AnimationId.HUMAN_SWORD_SLASH, AnimationId.HUMAN_SWORD_STAB).isEquipped().decreaseCharges(1).consumer(() -> {
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
            }),
        };
    }
}
