package tictac7x.charges.items.crystal;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;

import java.util.*;

import static tictac7x.charges.store.enums.HitsplatTarget.*;

public class A_CrystalBody extends ChargedItem {
    public A_CrystalBody(Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_body, ItemID.CRYSTAL_CHESTPLATE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_HEFIN),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_ITHELL),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_IORWERTH),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_TRAHAEARN),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_CADARN),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_CRWYS),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_AMLODD),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_DEADMAN),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_INACTIVE_HEFIN).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_INACTIVE_ITHELL).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_INACTIVE_IORWERTH).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_INACTIVE_TRAHAEARN).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_INACTIVE_CADARN).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_INACTIVE_CRWYS).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_INACTIVE_AMLODD).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_CHESTPLATE_INACTIVE_DEADMAN).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("Your crystal body has (?<charges>.+) charges? remaining").setDynamicallyCharges().onItemClick(),
            new OnHitsplatApplied(SELF, HitsplatGroup.SUCCESSFUL).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Crystal body", "Crystal shard", 100, this)
        ));
    }
}
