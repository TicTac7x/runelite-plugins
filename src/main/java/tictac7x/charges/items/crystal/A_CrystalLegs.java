package tictac7x.charges.items.crystal;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;

import java.util.*;

import static tictac7x.charges.store.enums.HitsplatTarget.*;

public class A_CrystalLegs extends ChargedItem {
    public A_CrystalLegs(Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_legs, ItemID.CRYSTAL_PLATELEGS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_HEFIN),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_ITHELL),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_IORWERTH),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_TRAHAEARN),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_CADARN),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_CRWYS),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_AMLODD),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_DEADMAN),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_INACTIVE_HEFIN).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_INACTIVE_ITHELL).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_INACTIVE_IORWERTH).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_INACTIVE_TRAHAEARN).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_INACTIVE_CADARN).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_INACTIVE_CRWYS).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_INACTIVE_AMLODD).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_PLATELEGS_INACTIVE_DEADMAN).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("Your crystal legs has (?<charges>.+) charges? remaining").setDynamicallyCharges().onItemClick(),
            new OnHitsplatApplied(SELF, HitsplatGroup.SUCCESSFUL).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Crystal legs", "Crystal shard", 100, this)
        ));
    }
}
