package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.store.ItemId;
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
        super(TicTac7xChargesImprovedConfig.chugging_barrel, ItemId.CHUGGING_BARREL, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.storage.storableItems(
            // Regular potions.
            new StorableItem(ItemId.ATTACK_POTION1),
            new StorableItem(ItemId.ANTIPOISON1),
            new StorableItem(ItemId.RELICYMS_BALM1),
            new StorableItem(ItemId.STRENGTH_POTION1),
            new StorableItem(ItemId.RESTORE_POTION1),
            new StorableItem(ItemId.GUTHIX_BALANCE1),
            new StorableItem(ItemId.ENERGY_POTION1),
            new StorableItem(ItemId.DEFENCE_POTION1),
            new StorableItem(ItemId.AGILITY_POTION1),
            new StorableItem(ItemId.COMBAT_POTION1),
            new StorableItem(ItemId.PRAYER_POTION1),
            new StorableItem(ItemId.SUPER_ATTACK1),
            new StorableItem(ItemId.SUPERANTIPOISON1),
            new StorableItem(ItemId.FISHING_POTION1),
            new StorableItem(ItemId.SUPER_ENERGY1),
            new StorableItem(ItemId.HUNTER_POTION1),
            new StorableItem(ItemId.GOADING_POTION1),
            new StorableItem(ItemId.SUPER_STRENGTH1),
            new StorableItem(ItemId.MAGIC_ESSENCE1),
            new StorableItem(ItemId.PRAYER_REGENERATION_POTION1),
            new StorableItem(ItemId.SUPER_RESTORE1),
            new StorableItem(ItemId.SANFEW_SERUM1),
            new StorableItem(ItemId.SUPER_DEFENCE1),
            new StorableItem(ItemId.ANTIDOTE1),
            new StorableItem(ItemId.ANTIFIRE_POTION1),
            new StorableItem(ItemId.DIVINE_SUPER_ATTACK_POTION1),
            new StorableItem(ItemId.DIVINE_SUPER_DEFENCE_POTION1),
            new StorableItem(ItemId.DIVINE_SUPER_STRENGTH_POTION1),
            new StorableItem(ItemId.RANGING_POTION1),
            new StorableItem(ItemId.DIVINE_RANGING_POTION1),
            new StorableItem(ItemId.MAGIC_POTION1),
            new StorableItem(ItemId.STAMINA_POTION1),
            new StorableItem(ItemId.ZAMORAK_BREW1),
            new StorableItem(ItemId.DIVINE_MAGIC_POTION1),
            new StorableItem(ItemId.ANTIDOTE1_5958),
            new StorableItem(ItemId.BASTION_POTION1),
            new StorableItem(ItemId.BATTLEMAGE_POTION1),
            new StorableItem(ItemId.SARADOMIN_BREW1),
            new StorableItem(ItemId.EXTENDED_ANTIFIRE1),
            new StorableItem(ItemId.ANCIENT_BREW1),
            new StorableItem(ItemId.DIVINE_BASTION_POTION1),
            new StorableItem(ItemId.DIVINE_BATTLEMAGE_POTION1),
            new StorableItem(ItemId.ANTIVENOM1),
            new StorableItem(ItemId.MENAPHITE_REMEDY1),
            new StorableItem(ItemId.SUPER_COMBAT_POTION1),
            new StorableItem(ItemId.FORGOTTEN_BREW1),
            new StorableItem(ItemId.SUPER_ANTIFIRE_POTION1),
            new StorableItem(ItemId.ANTIVENOM1_12919),
            new StorableItem(ItemId.EXTENDED_ANTIVENOM1),
            new StorableItem(ItemId.DIVINE_SUPER_COMBAT_POTION1),
            new StorableItem(ItemId.EXTENDED_SUPER_ANTIFIRE1),

            // Mixes.
            new StorableItem(ItemId.ATTACK_MIX1),
            new StorableItem(ItemId.ANTIPOISON_MIX1),
            new StorableItem(ItemId.RELICYMS_MIX1),
            new StorableItem(ItemId.STRENGTH_MIX1),
            new StorableItem(ItemId.RESTORE_MIX1),
            new StorableItem(ItemId.ENERGY_MIX1),
            new StorableItem(ItemId.DEFENCE_MIX1),
            new StorableItem(ItemId.AGILITY_MIX1),
            new StorableItem(ItemId.COMBAT_MIX1),
            new StorableItem(ItemId.PRAYER_MIX1),
            new StorableItem(ItemId.SUPERATTACK_MIX1),
            new StorableItem(ItemId.ANTIPOISON_SUPERMIX1),
            new StorableItem(ItemId.FISHING_MIX1),
            new StorableItem(ItemId.SUPER_ENERGY_MIX1),
            new StorableItem(ItemId.HUNTING_MIX1),
            new StorableItem(ItemId.SUPER_STR_MIX1),
            new StorableItem(ItemId.MAGIC_ESSENCE_MIX1),
            new StorableItem(ItemId.SUPER_RESTORE_MIX1),
            new StorableItem(ItemId.SUPER_DEF_MIX1),
            new StorableItem(ItemId.ANTIDOTE_MIX1),
            new StorableItem(ItemId.ANTIFIRE_MIX1),
            new StorableItem(ItemId.RANGING_MIX1),
            new StorableItem(ItemId.MAGIC_MIX1),
            new StorableItem(ItemId.ZAMORAK_MIX1),
            new StorableItem(ItemId.STAMINA_MIX1),
            new StorableItem(ItemId.EXTENDED_ANTIFIRE_MIX1),
            new StorableItem(ItemId.ANCIENT_MIX1),
            new StorableItem(ItemId.SUPER_ANTIFIRE_MIX1),
            new StorableItem(ItemId.EXTENDED_SUPER_ANTIFIRE_MIX1)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CHUGGING_BARREL_DISASSEMBLED),
            new TriggerItem(ItemId.CHUGGING_BARREL),
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
