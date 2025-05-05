package tictac7x.charges.items.utils;

import tictac7x.charges.store.*;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.AnimationId;
import tictac7x.charges.store.ids.ItemContainerId;
import tictac7x.charges.store.ids.ItemId;

public class U_ChuggingBarrel extends ChargedItemWithStorage {
    public U_ChuggingBarrel(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.chugging_barrel, ItemId.CHUGGING_BARREL, provider);
        this.storage.storableItems(
            // Regular potions.
            new StorableItem(ItemId.ATTACK_POTION_1),
            new StorableItem(ItemId.ANTIPOISON_1),
            new StorableItem(ItemId.RELICYMS_BALM_1),
            new StorableItem(ItemId.STRENGTH_POTION_1),
            new StorableItem(ItemId.RESTORE_POTION_1),
            new StorableItem(ItemId.GUTHIX_BALANCE_1),
            new StorableItem(ItemId.ENERGY_POTION_1),
            new StorableItem(ItemId.DEFENCE_POTION_1),
            new StorableItem(ItemId.AGILITY_POTION_1),
            new StorableItem(ItemId.COMBAT_POTION_1),
            new StorableItem(ItemId.PRAYER_POTION_1),
            new StorableItem(ItemId.SUPER_ATTACK_1),
            new StorableItem(ItemId.SUPER_ANTIPOISON_1),
            new StorableItem(ItemId.FISHING_POTION_1),
            new StorableItem(ItemId.SUPER_ENERGY_POTION_1),
            new StorableItem(ItemId.HUNTER_POTION_1),
            new StorableItem(ItemId.GOADING_POTION_1),
            new StorableItem(ItemId.SUPER_STRENGTH_1),
            new StorableItem(ItemId.MAGIC_ESSENCE_1),
            new StorableItem(ItemId.PRAYER_REGENERATION_POTION_1),
            new StorableItem(ItemId.SUPER_RESTORE_1),
            new StorableItem(ItemId.SANFEW_SERUM_1),
            new StorableItem(ItemId.SUPER_DEFENCE_1),
            new StorableItem(ItemId.ANTIDOTE_1),
            new StorableItem(ItemId.ANTIFIRE_POTION_1),
            new StorableItem(ItemId.DIVINE_SUPER_ATTACK_POTION_1),
            new StorableItem(ItemId.DIVINE_SUPER_DEFENCE_POTION_1),
            new StorableItem(ItemId.DIVINE_SUPER_STRENGTH_POTION_1),
            new StorableItem(ItemId.RANGING_POTION_1),
            new StorableItem(ItemId.DIVINE_RANGING_POTION_1),
            new StorableItem(ItemId.MAGIC_POTION_1),
            new StorableItem(ItemId.STAMINA_POTION_1),
            new StorableItem(ItemId.ZAMORAK_BREW_1),
            new StorableItem(ItemId.DIVINE_MAGIC_POTION_1),
            new StorableItem(ItemId.ANTIDOTE_PLUS_PLUS_1),
            new StorableItem(ItemId.BASTION_POTION_1),
            new StorableItem(ItemId.BATTLEMAGE_POTION_1),
            new StorableItem(ItemId.SARADOMIN_BREW_1),
            new StorableItem(ItemId.EXTENDED_ANTIFIRE_1),
            new StorableItem(ItemId.ANCIENT_BREW_1),
            new StorableItem(ItemId.DIVINE_BASTION_POTION_1),
            new StorableItem(ItemId.DIVINE_BATTLEMAGE_POTION_1),
            new StorableItem(ItemId.ANTIVENOM_1),
            new StorableItem(ItemId.MENAPHITE_REMEDY_1),
            new StorableItem(ItemId.SUPER_COMBAT_POTION_1),
            new StorableItem(ItemId.FORGOTTEN_BREW_1),
            new StorableItem(ItemId.SUPER_ANTIFIRE_POTION_1),
            new StorableItem(ItemId.ANTIVENOM_PLUS_1),
            new StorableItem(ItemId.EXTENDED_ANTIVENOM_1),
            new StorableItem(ItemId.DIVINE_SUPER_COMBAT_POTION_1),
            new StorableItem(ItemId.EXTENDED_SUPER_ANTIFIRE_1),
            new StorableItem(ItemId.SERUM_207_1),
            new StorableItem(ItemId.SERUM_208_1),

            // Mixes.
            new StorableItem(ItemId.ATTACK_MIX_1),
            new StorableItem(ItemId.ANTIPOISON_MIX_1),
            new StorableItem(ItemId.RELICYMS_MIX_1),
            new StorableItem(ItemId.STRENGTH_MIX_1),
            new StorableItem(ItemId.RESTORE_MIX_1),
            new StorableItem(ItemId.ENERGY_MIX_1),
            new StorableItem(ItemId.DEFENCE_MIX_1),
            new StorableItem(ItemId.AGILITY_MIX_1),
            new StorableItem(ItemId.COMBAT_MIX_1),
            new StorableItem(ItemId.PRAYER_MIX_1),
            new StorableItem(ItemId.SUPER_ATTACK_MIX_1),
            new StorableItem(ItemId.SUPER_ANTIPOISON_MIX_1),
            new StorableItem(ItemId.FISHING_MIX_1),
            new StorableItem(ItemId.SUPER_ENERGY_MIX_1),
            new StorableItem(ItemId.HUNTING_MIX_1),
            new StorableItem(ItemId.SUPER_STRENGTH_MIX_1),
            new StorableItem(ItemId.MAGIC_ESSENCE_MIX_1),
            new StorableItem(ItemId.SUPER_RESTORE_MIX_1),
            new StorableItem(ItemId.SUPER_DEFENCE_MIX_1),
            new StorableItem(ItemId.ANTIDOTE_MIX_1),
            new StorableItem(ItemId.ANTIFIRE_MIX_1),
            new StorableItem(ItemId.RANGING_MIX_1),
            new StorableItem(ItemId.MAGIC_MIX_1),
            new StorableItem(ItemId.ZAMORAK_MIX_1),
            new StorableItem(ItemId.STAMINA_MIX_1),
            new StorableItem(ItemId.EXTENDED_ANTIFIRE_MIX_1),
            new StorableItem(ItemId.ANCIENT_BREW_MIX_1),
            new StorableItem(ItemId.SUPER_ANTIFIRE_MIX_1),
            new StorableItem(ItemId.EXTENDED_SUPER_ANTIFIRE_MIX_1)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CHUGGING_BARREL_DISASSEMBLED).fixedCharges(0),
            new TriggerItem(ItemId.CHUGGING_BARREL),
        };

        this.triggers = new TriggerBase[]{
            // Check contents.
            new OnItemContainerChanged(ItemContainerId.CHUGGING_BARREL).updateStorage(),

            // Drink.
            new OnAnimationChanged(AnimationId.CHUGGING_BARREL_DRINK).consumer(() -> {
                for (final StorageItem storageItem : storage.getStorage().getItems()) {
                    storage.put(storageItem.getId(), storageItem.getQuantity() - 1);
                }
            }),

            // Hide dismantle.
            new OnMenuEntryAdded("Dismantle").hide(),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),

            // Unify "Open" to "Configure"
            new OnMenuEntryAdded("Open").replaceOption("Configure"),
        };
    }

    private int getMinimumSips() {
        int sipsMinimum = 0;

        for (final StorageItem storageItem : storage.getStorage().getItems()) {
            if (sipsMinimum == 0 || storageItem.getQuantity() < sipsMinimum) {
                sipsMinimum = storageItem.getQuantity();
            }
        }

        return sipsMinimum;
    }

    @Override
    public int getCharges(final int itemId) {
        return getMinimumSips();
    }

    @Override
    public int getTotalCharges() {
        return getMinimumSips();
    }

    @Override
    public String getTooltip() {
        final String tooltip = super.getTooltip();
        return tooltip.replaceAll("\\(1\\)", "");
    }
}
