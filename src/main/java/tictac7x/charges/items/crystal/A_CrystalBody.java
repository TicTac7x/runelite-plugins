package tictac7x.charges.items.crystal;

import tictac7x.charges.store.enums.HitsplatGroup;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import static tictac7x.charges.store.enums.HitsplatTarget.SELF;

public class A_CrystalBody extends ChargedItem {
    public A_CrystalBody(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_body, ItemId.CRYSTAL_BODY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_BODY),
            new TriggerItem(ItemId.CRYSTAL_BODY_HEFIN),
            new TriggerItem(ItemId.CRYSTAL_BODY_ITHELL),
            new TriggerItem(ItemId.CRYSTAL_BODY_IORWERTH),
            new TriggerItem(ItemId.CRYSTAL_BODY_TRAHAEARN),
            new TriggerItem(ItemId.CRYSTAL_BODY_CADARN),
            new TriggerItem(ItemId.CRYSTAL_BODY_CRWYS),
            new TriggerItem(ItemId.CRYSTAL_BODY_AMLODD),
            new TriggerItem(ItemId.CRYSTAL_BODY_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_BODY_HEFIN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_BODY_ITHELL_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_BODY_IORWERTH_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_BODY_TRAHAEARN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_BODY_CADARN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_BODY_CRWYS_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_BODY_AMLODD_INACTIVE).fixedCharges(0),
        };

        this.triggers = new TriggerBase[] {
            new OnChatMessage("Your crystal body has (?<charges>.+) charges? remaining").setDynamicallyCharges().onItemClick(),
            new OnHitsplatApplied(SELF, HitsplatGroup.SUCCESSFUL).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Crystal body using (?<crystalshard>.+)x Crystal shard.").matcherConsumer(m -> {
                final int crystalShards = Integer.parseInt(m.group("crystalshard"));
                increaseCharges(crystalShards * 100);
            }),
        };
    }
}
