package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.Store;

public class U_ChuggingBarrel extends ChargedItemWithStorage {
    public U_ChuggingBarrel(
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ChatMessageManager chatMessageManager,
        final Notifier notifier,
        final TicTac7xChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(TicTac7xChargesImprovedConfig.chugging_barrel, ItemID.CHUGGING_BARREL, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.storage.storableItems(
            // Regular potions.
            new StorableItem(ItemID.ATTACK_POTION1),
            new StorableItem(ItemID.ANTIPOISON1),
            new StorableItem(ItemID.RELICYMS_BALM1),
            new StorableItem(ItemID.STRENGTH_POTION1),
            new StorableItem(ItemID.RESTORE_POTION1),
            new StorableItem(ItemID.GUTHIX_BALANCE1),
            new StorableItem(ItemID.ENERGY_POTION1),
            new StorableItem(ItemID.DEFENCE_POTION1),
            new StorableItem(ItemID.AGILITY_POTION1),
            new StorableItem(ItemID.COMBAT_POTION1),
            new StorableItem(ItemID.PRAYER_POTION1),
            new StorableItem(ItemID.SUPER_ATTACK1),
            new StorableItem(ItemID.SUPERANTIPOISON1),
            new StorableItem(ItemID.FISHING_POTION1),
            new StorableItem(ItemID.SUPER_ENERGY1),
            new StorableItem(ItemID.HUNTER_POTION1),
            new StorableItem(ItemID.GOADING_POTION1),
            new StorableItem(ItemID.SUPER_STRENGTH1),
            new StorableItem(ItemID.MAGIC_ESSENCE1),
            new StorableItem(ItemID.PRAYER_REGENERATION_POTION1),
            new StorableItem(ItemID.SUPER_RESTORE1),
            new StorableItem(ItemID.SANFEW_SERUM1),
            new StorableItem(ItemID.SUPER_DEFENCE1),
            new StorableItem(ItemID.ANTIDOTE1),
            new StorableItem(ItemID.ANTIFIRE_POTION1),
            new StorableItem(ItemID.DIVINE_SUPER_ATTACK_POTION1),
            new StorableItem(ItemID.DIVINE_SUPER_DEFENCE_POTION1),
            new StorableItem(ItemID.DIVINE_SUPER_STRENGTH_POTION1),
            new StorableItem(ItemID.RANGING_POTION1),
            new StorableItem(ItemID.DIVINE_RANGING_POTION1),
            new StorableItem(ItemID.MAGIC_POTION1),
            new StorableItem(ItemID.STAMINA_POTION1),
            new StorableItem(ItemID.ZAMORAK_BREW1),
            new StorableItem(ItemID.DIVINE_MAGIC_POTION1),
            new StorableItem(ItemID.ANTIDOTE1_5958),
            new StorableItem(ItemID.BASTION_POTION1),
            new StorableItem(ItemID.BATTLEMAGE_POTION1),
            new StorableItem(ItemID.SARADOMIN_BREW1),
            new StorableItem(ItemID.EXTENDED_ANTIFIRE1),
            new StorableItem(ItemID.ANCIENT_BREW1),
            new StorableItem(ItemID.DIVINE_BASTION_POTION1),
            new StorableItem(ItemID.DIVINE_BATTLEMAGE_POTION1),
            new StorableItem(ItemID.ANTIVENOM1),
            new StorableItem(ItemID.MENAPHITE_REMEDY1),
            new StorableItem(ItemID.SUPER_COMBAT_POTION1),
            new StorableItem(ItemID.FORGOTTEN_BREW1),
            new StorableItem(ItemID.SUPER_ANTIFIRE_POTION1),
            new StorableItem(ItemID.ANTIVENOM1_12919),
            new StorableItem(ItemID.EXTENDED_ANTIVENOM1),
            new StorableItem(ItemID.DIVINE_SUPER_COMBAT_POTION1),
            new StorableItem(ItemID.EXTENDED_SUPER_ANTIFIRE1),

            // Mixes.
            new StorableItem(ItemID.ATTACK_MIX1),
            new StorableItem(ItemID.ANTIPOISON_MIX1),
            new StorableItem(ItemID.RELICYMS_MIX1),
            new StorableItem(ItemID.STRENGTH_MIX1),
            new StorableItem(ItemID.RESTORE_MIX1),
            new StorableItem(ItemID.ENERGY_MIX1),
            new StorableItem(ItemID.DEFENCE_MIX1),
            new StorableItem(ItemID.AGILITY_MIX1),
            new StorableItem(ItemID.COMBAT_MIX1),
            new StorableItem(ItemID.PRAYER_MIX1),
            new StorableItem(ItemID.SUPERATTACK_MIX1),
            new StorableItem(ItemID.ANTIPOISON_SUPERMIX1),
            new StorableItem(ItemID.FISHING_MIX1),
            new StorableItem(ItemID.SUPER_ENERGY_MIX1),
            new StorableItem(ItemID.HUNTING_MIX1),
            new StorableItem(ItemID.SUPER_STR_MIX1),
            new StorableItem(ItemID.MAGIC_ESSENCE_MIX1),
            new StorableItem(ItemID.SUPER_RESTORE_MIX1),
            new StorableItem(ItemID.SUPER_DEF_MIX1),
            new StorableItem(ItemID.ANTIDOTE_MIX1),
            new StorableItem(ItemID.ANTIFIRE_MIX1),
            new StorableItem(ItemID.RANGING_MIX1),
            new StorableItem(ItemID.MAGIC_MIX1),
            new StorableItem(ItemID.ZAMORAK_MIX1),
            new StorableItem(ItemID.STAMINA_MIX1),
            new StorableItem(ItemID.EXTENDED_ANTIFIRE_MIX1),
            new StorableItem(ItemID.ANCIENT_MIX1),
            new StorableItem(ItemID.SUPER_ANTIFIRE_MIX1),
            new StorableItem(ItemID.EXTENDED_SUPER_ANTIFIRE_MIX1)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CHUGGING_BARREL_DISASSEMBLED),
            new TriggerItem(ItemID.CHUGGING_BARREL),
        };

        this.triggers = new TriggerBase[]{
            // Check contents.
            new OnItemContainerChanged(ItemContainerId.CHUGGING_BARREL).updateStorage(),

            // Drink.
            new OnAnimationChanged(11645).consumer(() -> {
                for (final StorageItem storageItem : storage.getStorage().getItems()) {
                    storage.put(storageItem.getId(), storageItem.getQuantity() - 1);
                }
            }),

            // Hide dismantle.
            new OnMenuEntryAdded("Dismantle").hide(),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }

    @Override
    public String getCharges(final int itemId) {
        int sipsMinimum = 0;

        for (final StorageItem storageItem : storage.getStorage().getItems()) {
            if (sipsMinimum == 0 || storageItem.getQuantity() < sipsMinimum) {
                sipsMinimum = storageItem.getQuantity();
            }
        }

        return String.valueOf(sipsMinimum);
    }

    @Override
    public String getTooltip() {
        final String tooltip = super.getTooltip();
        return tooltip.replaceAll("\\(1\\)", "");
    }
}
