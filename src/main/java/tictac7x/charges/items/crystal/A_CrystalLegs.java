package tictac7x.charges.items.crystal;

import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.store.enums.HitsplatGroup;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

import static tictac7x.charges.store.enums.HitsplatTarget.SELF;

public class A_CrystalLegs extends ChargedItem {
    public A_CrystalLegs(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_legs, ItemId.CRYSTAL_LEGS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_LEGS),
            new TriggerItem(ItemId.CRYSTAL_LEGS_HEFIN),
            new TriggerItem(ItemId.CRYSTAL_LEGS_ITHELL),
            new TriggerItem(ItemId.CRYSTAL_LEGS_IORWERTH),
            new TriggerItem(ItemId.CRYSTAL_LEGS_TRAHAEARN),
            new TriggerItem(ItemId.CRYSTAL_LEGS_CADARN),
            new TriggerItem(ItemId.CRYSTAL_LEGS_CRWYS),
            new TriggerItem(ItemId.CRYSTAL_LEGS_AMLODD),
            new TriggerItem(ItemId.CRYSTAL_LEGS_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_HEFIN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_ITHELL_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_IORWERTH_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_TRAHAEARN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_CADARN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_CRWYS_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_LEGS_AMLODD_INACTIVE).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("Your crystal legs has (?<charges>.+) charges? remaining").setDynamicallyCharges().onItemClick(),
            new OnHitsplatApplied(SELF, HitsplatGroup.SUCCESSFUL).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Crystal legs", "Crystal shard", 100, this)
        ));
    }
}
