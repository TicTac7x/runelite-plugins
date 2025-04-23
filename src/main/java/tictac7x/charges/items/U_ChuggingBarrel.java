package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.store.AnimationId;
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
            new StorableItem(ItemId.ANTIDOTE1_5958),
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
            new StorableItem(ItemId.SUPERATTACK_MIX_1),
            new StorableItem(ItemId.ANTIPOISON_SUPERMIX_1),
            new StorableItem(ItemId.FISHING_MIX_1),
            new StorableItem(ItemId.SUPER_ENERGY_MIX_1),
            new StorableItem(ItemId.HUNTING_MIX_1),
            new StorableItem(ItemId.SUPER_STR_MIX_1),
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
            new StorableItem(ItemId.ANCIENT_MIX_1),
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
