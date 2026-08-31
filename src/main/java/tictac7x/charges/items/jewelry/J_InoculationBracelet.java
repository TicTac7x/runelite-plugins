package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.ItemID;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.enums.HitsplatGroup;
import tictac7x.charges.store.enums.HitsplatTarget;

import java.util.List;

public class J_InoculationBracelet extends ChargedItem {
    public J_InoculationBracelet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.inoculation_bracelet, ItemID.JEWL_BRACELET_OF_INNOCULATION, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.JEWL_BRACELET_OF_INNOCULATION).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("Your bracelet will protect you from (?<charges>.+) points? of disease damage.").setDynamicallyCharges(),

            // Break
            new OnChatMessage("Your bracelet of inoculation runs out of charges and crumbles to dust.").setFixedCharges(275),

            // Hitsplat
            new OnHitsplatApplied(HitsplatTarget.SELF, HitsplatGroup.DISEASE_BLOCKED).isEquipped().consumer(() -> {
                decreaseCharges(1);
            })
        ));
    }
}
