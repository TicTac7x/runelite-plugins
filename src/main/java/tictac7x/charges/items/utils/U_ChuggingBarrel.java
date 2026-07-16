package tictac7x.charges.items.utils;

import net.runelite.api.gameval.AnimationID;
import net.runelite.api.gameval.InventoryID;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

import java.util.*;

public class U_ChuggingBarrel extends ChargedItemWithStorage {
    public U_ChuggingBarrel(Provider provider) {
        super(TicTac7xChargesImprovedConfig.chugging_barrel, ItemID.MM_PREPOT_DEVICE, provider);
        this.storage.storableItems(
            // Regular potions.
            new StorableItem(ItemID._1DOSE1ATTACK),
            new StorableItem(ItemID._1DOSEANTIPOISON),
            new StorableItem(ItemID.RELICYMS_BALM1),
            new StorableItem(ItemID._1DOSE1STRENGTH),
            new StorableItem(ItemID._1DOSESTATRESTORE),
            new StorableItem(ItemID.BURGH_GUTHIX_BALANCE_1),
            new StorableItem(ItemID._1DOSE1ENERGY),
            new StorableItem(ItemID._1DOSE1DEFENSE),
            new StorableItem(ItemID._1DOSE1AGILITY),
            new StorableItem(ItemID._1DOSECOMBAT),
            new StorableItem(ItemID._1DOSEPRAYERRESTORE),
            new StorableItem(ItemID._1DOSE2ATTACK),
            new StorableItem(ItemID._1DOSE2ANTIPOISON),
            new StorableItem(ItemID._1DOSEFISHERSPOTION),
            new StorableItem(ItemID._1DOSE2ENERGY),
            new StorableItem(ItemID._1DOSEHUNTING),
            new StorableItem(ItemID._1DOSEGOADING),
            new StorableItem(ItemID._1DOSE2STRENGTH),
            new StorableItem(ItemID._1DOSEMAGICESS),
            new StorableItem(ItemID._1DOSE1PRAYER_REGENERATION),
            new StorableItem(ItemID._1DOSE2RESTORE),
            new StorableItem(ItemID.SANFEW_SALVE_1_DOSE),
            new StorableItem(ItemID._1DOSE2DEFENSE),
            new StorableItem(ItemID.ANTIDOTE_1),
            new StorableItem(ItemID._1DOSE1ANTIDRAGON),
            new StorableItem(ItemID._1DOSEDIVINEATTACK),
            new StorableItem(ItemID._1DOSEDIVINEDEFENCE),
            new StorableItem(ItemID._1DOSEDIVINESTRENGTH),
            new StorableItem(ItemID._1DOSERANGERSPOTION),
            new StorableItem(ItemID._1DOSEDIVINERANGE),
            new StorableItem(ItemID._1DOSE1MAGIC),
            new StorableItem(ItemID._1DOSESTAMINA),
            new StorableItem(ItemID._1DOSEPOTIONOFZAMORAK),
            new StorableItem(ItemID._1DOSEDIVINEMAGIC),
            new StorableItem(ItemID.ANTIDOTE__1),
            new StorableItem(ItemID._1DOSEBASTION),
            new StorableItem(ItemID._1DOSEBATTLEMAGE),
            new StorableItem(ItemID._1DOSEPOTIONOFSARADOMIN),
            new StorableItem(ItemID._1DOSESURGE),
            new StorableItem(ItemID._1DOSE2ANTIDRAGON),
            new StorableItem(ItemID._1DOSEANCIENTBREW),
            new StorableItem(ItemID._1DOSEDIVINEBASTION),
            new StorableItem(ItemID._1DOSEDIVINEBATTLEMAGE),
            new StorableItem(ItemID.ANTIVENOM1),
            new StorableItem(ItemID._1DOSESTATRENEWAL),
            new StorableItem(ItemID._1DOSE2COMBAT),
            new StorableItem(ItemID._1DOSEFORGOTTENBREW),
            new StorableItem(ItemID._1DOSE3ANTIDRAGON),
            new StorableItem(ItemID.ANTIVENOM_1),
            new StorableItem(ItemID.EXTENDED_ANTIVENOM_1),
            new StorableItem(ItemID._1DOSEDIVINECOMBAT),
            new StorableItem(ItemID._1DOSE4ANTIDRAGON),
            new StorableItem(ItemID.MORT_SERUM1),
            new StorableItem(ItemID.MORT_SERUM_PERM1),

            // Mixes.
            new StorableItem(ItemID.BRUTAL_1DOSE1ATTACK),
            new StorableItem(ItemID.BRUTAL_1DOSEANTIPOISON),
            new StorableItem(ItemID.BRUTAL_RELICYMS_BALM1),
            new StorableItem(ItemID.BRUTAL_1DOSE1STRENGTH),
            new StorableItem(ItemID.BRUTAL_1DOSESTATRESTORE),
            new StorableItem(ItemID.BRUTAL_1DOSE1ENERGY),
            new StorableItem(ItemID.BRUTAL_1DOSE1DEFENSE),
            new StorableItem(ItemID.BRUTAL_1DOSE1AGILITY),
            new StorableItem(ItemID.BRUTAL_1DOSECOMBAT),
            new StorableItem(ItemID.BRUTAL_1DOSEPRAYERRESTORE),
            new StorableItem(ItemID.BRUTAL_1DOSE2ATTACK),
            new StorableItem(ItemID.BRUTAL_1DOSE2ANTIPOISON),
            new StorableItem(ItemID.BRUTAL_1DOSEFISHERSPOTION),
            new StorableItem(ItemID.BRUTAL_1DOSE2ENERGY),
            new StorableItem(ItemID.BRUTAL_1DOSE1HUNTING),
            new StorableItem(ItemID.BRUTAL_1DOSE2STRENGTH),
            new StorableItem(ItemID.BRUTAL_1DOSEMAGICESS),
            new StorableItem(ItemID.BRUTAL_1DOSE2RESTORE),
            new StorableItem(ItemID.BRUTAL_1DOSE2DEFENSE),
            new StorableItem(ItemID.BRUTAL_ANTIDOTE_1),
            new StorableItem(ItemID.BRUTAL_1DOSE1ANTIDRAGON),
            new StorableItem(ItemID.BRUTAL_1DOSERANGERSPOTION),
            new StorableItem(ItemID.BRUTAL_1DOSE1MAGIC),
            new StorableItem(ItemID.BRUTAL_1DOSEPOTIONOFZAMORAK),
            new StorableItem(ItemID.BRUTAL_1DOSESTAMINA),
            new StorableItem(ItemID.BRUTAL_1DOSE2ANTIDRAGON),
            new StorableItem(ItemID.BRUTAL_1DOSEANCIENTBREW),
            new StorableItem(ItemID.BRUTAL_1DOSE3ANTIDRAGON),
            new StorableItem(ItemID.BRUTAL_1DOSE4ANTIDRAGON)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.MM_PREPOT_DEVICE_DISASSEMBLED).fixedCharges(0),
            new TriggerItem(ItemID.MM_PREPOT_DEVICE),
        };

        this.triggers.addAll(List.of(
            // Check contents.
            new OnItemContainerChanged(InventoryID.PREPOT_DEVICE_INV).updateStorage(),

            // Drink.
            new OnAnimationChanged(AnimationID.PREPOT_DEVICE_DRINK_FULL).consumer(() -> {
                for (StorageItem storageItem : storage.getStorage().getItems()) {
                    storage.put(storageItem.itemId, storageItem.getQuantity() - 1);
                }
            }),

            // Hide dismantle.
            new OnMenuEntryAdded("Dismantle").hide(),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),

            // Unify "Open" to "Configure"
            new OnMenuEntryAdded("Open").replaceOption("Configure")
        ));
    }

    private int getMinimumSips() {
        int sipsMinimum = 0;

        for (StorageItem storageItem : storage.getStorage().getItems()) {
            if (sipsMinimum == 0 || storageItem.getQuantity() < sipsMinimum) {
                sipsMinimum = storageItem.getQuantity();
            }
        }

        return sipsMinimum;
    }

    @Override
    public int getCharges(int itemId) {
        return getMinimumSips();
    }

    @Override
    public int getTotalCharges() {
        return getMinimumSips();
    }

    @Override
    public String getTooltip() {
        String tooltip = super.getTooltip();
        return tooltip.replaceAll("\\(1\\)", "");
    }
}
