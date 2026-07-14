package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_XericsTalisman extends ChargedItem {
    public J_XericsTalisman(Provider provider) {
        super(TicTac7xChargesImprovedConfig.xerics_talisman, ItemID.XERIC_TALISMAN, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.XERIC_TALISMAN_EMPTY).fixedCharges(0),
            new TriggerItem(ItemID.XERIC_TALISMAN),
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
