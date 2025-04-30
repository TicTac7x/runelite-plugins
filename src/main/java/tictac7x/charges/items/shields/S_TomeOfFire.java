package tictac7x.charges.items.shields;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class S_TomeOfFire extends ChargedItem {
    public S_TomeOfFire(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.tome_of_fire, ItemId.TOME_OF_FIRE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TOME_OF_FIRE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TOME_OF_FIRE).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your tome has been charged with (Burnt|Searing) Pages. It currently holds (?<charges>.+) charges?.").setDynamicallyCharges().onItemClick(),

            // Attack with regular spellbook fire spells.
            new OnGraphicChanged(99, 126, 129, 155, 1464).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Tome of fire using (?<burntpage>.+)x Burnt page.").matcherConsumer(m -> {
                final int burntPages = Integer.parseInt(m.group("burntpage"));
                increaseCharges(burntPages * 20);
            }),
        };
    }
}
