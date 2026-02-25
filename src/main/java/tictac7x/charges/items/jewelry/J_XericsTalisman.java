package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class J_XericsTalisman extends ChargedItem {
    public J_XericsTalisman(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.xerics_talisman, ItemId.XERICS_TALISMAN, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.XERICS_TALISMAN_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.XERICS_TALISMAN),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("(The|Your) talisman( now)? has one charge.").onItemClick().setFixedCharges(1),
            new OnChatMessage("(The|Your) talisman( now)? has (?<charges>.+) charges.").setDynamicallyCharges().onItemClick(),

            // Teleport.
            new OnGraphicChanged(1612).decreaseCharges(1),

            // Teleport widget.
            new OnWidgetLoaded(187, 0, 1).text("The talisman has (?<charges>.+) charges.").setDynamically(),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),

            // Auto-charge.
            new OnAutoChargeMessage("Xeric's talisman", "Lizardman fang", 1, this)
        ));
    }
}
