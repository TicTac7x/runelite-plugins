package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.HitsplatTarget;
import tictac7x.charges.store.Provider;

public class J_RingOfSuffering extends ChargedItemWithStatus {
    public J_RingOfSuffering(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_suffering, ItemId.RING_OF_SUFFERING_UNCHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.RING_OF_SUFFERING_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.RING_OF_SUFFERING_UNCHARGED_IMBUED_NMZ).fixedCharges(0),
            new TriggerItem(ItemId.RING_OF_SUFFERING_UNCHARGED_IMBUED_SW).fixedCharges(0),
            new TriggerItem(ItemId.RING_OF_SUFFERING_UNCHARGED_IMBUED_PVP).fixedCharges(0),
            new TriggerItem(ItemId.RING_OF_SUFFERING),
            new TriggerItem(ItemId.RING_OF_SUFFERING_IMBUED_NMZ),
            new TriggerItem(ItemId.RING_OF_SUFFERING_IMBUED_SW),
            new TriggerItem(ItemId.RING_OF_SUFFERING_IMBUED_PVP),
        };

        this.triggers = new TriggerBase[]{
            // Check
            new OnChatMessage("Your ring currently has (?<charges>.+) recoil charges? remaining. The recoil effect is currently enabled.").setDynamicallyCharges().onItemClick().activate(),
            new OnChatMessage("Your ring currently has (?<charges>.+) recoil charges? remaining. The recoil effect is currently disabled.").setDynamicallyCharges().onItemClick().deactivate(),

            // Charge
            new OnChatMessage("You load your ring with .+ rings? of recoil. It now has (?<charges>.+) recoil charges.").setDynamicallyCharges(),

            // Get hit.
            new OnHitsplatApplied(HitsplatTarget.SELF).moreThanZeroDamage().isEquipped().isActivated().decreaseCharges(1),

            // Disable.
            new OnChatMessage("You disable the recoil effect of your ring.").deactivate(),

            // Enable.
            new OnChatMessage("You enable the recoil effect of your ring.").activate(),

            // Auto-charge.
            new OnChatMessage("The banker charges your Ring of suffering.* using (?<ringofrecoil>.+)x Ring of recoil.").matcherConsumer(m -> {
                final int ringOfRecoils = Integer.parseInt(m.group("ringofrecoil"));
                increaseCharges(ringOfRecoils * 40);
            }),
        };
    }
}
