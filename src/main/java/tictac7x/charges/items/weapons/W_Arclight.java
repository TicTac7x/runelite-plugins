package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import static tictac7x.charges.store.HitsplatTarget.ENEMY;

public class W_Arclight extends ChargedItem {
    public W_Arclight(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.arclight, ItemId.ARCLIGHT, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ARCLIGHT),
            new TriggerItem(ItemId.ARCLIGHT_UNCHARGED).fixedCharges(0),
        };

        this.triggers = new TriggerBase[] {
            new OnChatMessage("Your arclight has (?<charges>.+) charges?( left)?.").setDynamicallyCharges(),
            new OnChatMessage("Your arclight can perform (?<charges>.+) more attacks.").setDynamicallyCharges(),
            new OnChatMessage("Your arclight has degraded.").notification().setFixedCharges(0),
            new OnHitsplatApplied(ENEMY).isEquipped().decreaseCharges(1),
        };
    }
}
