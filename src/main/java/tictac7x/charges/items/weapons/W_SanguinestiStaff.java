package tictac7x.charges.items.weapons;

import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class W_SanguinestiStaff extends ChargedItem {
    public W_SanguinestiStaff(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.sanguinesti_staff, ItemId.SANGUINESTI_STAFF, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SANGUINESTI_STAFF),
            new TriggerItem(ItemId.SANGUINESTI_STAFF_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.SANGUINESTI_STAFF_HOLY),
            new TriggerItem(ItemId.SANGUINESTI_STAFF_HOLY_UNCHARGED).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your (Holy s|S)anguinesti staff has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Charge partially full.
            new OnChatMessage("You apply an additional .+ charges? to your Sanguinesti staff. It now has (?<charges>.+) charges? in total.").setDynamicallyCharges(),

            // Charge empty.
            new OnChatMessage("You apply (?<charges>.+) charges to your Sanguinesti staff.").setDynamicallyCharges(),

            // Auto-charge.
            new OnAutoChargeMessage("(Holy s|S)anguinesti staff", "Blood rune", 0.33, this)
        ));
    }
}
