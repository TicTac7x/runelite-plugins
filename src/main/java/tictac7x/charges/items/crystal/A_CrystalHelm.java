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

public class A_CrystalHelm extends ChargedItem {
    public A_CrystalHelm(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_helm, ItemId.CRYSTAL_HELM, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_HELM),
            new TriggerItem(ItemId.CRYSTAL_HELM_HEFIN),
            new TriggerItem(ItemId.CRYSTAL_HELM_ITHELL),
            new TriggerItem(ItemId.CRYSTAL_HELM_IORWERTH),
            new TriggerItem(ItemId.CRYSTAL_HELM_TRAHAEARN),
            new TriggerItem(ItemId.CRYSTAL_HELM_CADARN),
            new TriggerItem(ItemId.CRYSTAL_HELM_CRWYS),
            new TriggerItem(ItemId.CRYSTAL_HELM_AMLODD),
            new TriggerItem(ItemId.CRYSTAL_HELM_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_HELM_HEFIN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_HELM_ITHELL_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_HELM_IORWERTH_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_HELM_TRAHAEARN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_HELM_CADARN_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_HELM_CRWYS_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_HELM_AMLODD_INACTIVE).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("Your crystal helm has (?<charges>.+) charges? remaining").setDynamicallyCharges().onItemClick(),
            new OnHitsplatApplied(SELF, HitsplatGroup.SUCCESSFUL).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Crystal helm", "Crystal shard", 100, this)
        ));
    }
}
