package tictac7x.charges.items.capes;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class C_MagicCape extends ChargedItem {
    public C_MagicCape(Provider provider) {
        super(TicTac7xChargesImprovedConfig.magic_cape, ItemID.SKILLCAPE_MAGIC, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SKILLCAPE_MAGIC),
            new TriggerItem(ItemID.SKILLCAPE_MAGIC_TRIMMED)
        };

        this.triggers.addAll(List.of(
            // After spellbook swap.
            new OnChatMessage("You have changed your spellbook (?<used>.+)/(?<total>.+) times today.").setDifferenceCharges(),

            // Spellbook swap widget.
            new OnWidgetLoaded(219, 1, 0).text("Choose spellbook: \\((?<charges>.+)/5 left\\)").setDynamically(),

            // Daily reset.
            new OnResetDaily().setFixedCharges(5)
        ));
    }
}
