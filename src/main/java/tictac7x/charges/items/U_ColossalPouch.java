package tictac7x.charges.items;

import static tictac7x.charges.store.ItemContainerId.INVENTORY;

import com.google.gson.Gson;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.OnStatChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.Store;

import static tictac7x.charges.ChargesImprovedPlugin.getNumberFromWordRepresentation;

public class U_ColossalPouch extends ChargedItemWithStorage {
    public U_ColossalPouch(
        final Client client,
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(ChargesImprovedConfig.colossal_pouch, ItemID.COLOSSAL_POUCH, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);
        this.storage = storage
            .storeableItems(
                new StorageItem(ItemID.RUNE_ESSENCE).checkName("Colossal pouch"),
                new StorageItem(ItemID.PURE_ESSENCE).checkName("Colossal pouch"),
                new StorageItem(ItemID.DAEYALT_ESSENCE).checkName("Colossal pouch"),
                new StorageItem(ItemID.GUARDIAN_ESSENCE).checkName("Colossal pouch")
            )
            .maximumTotalQuantity(40);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.COLOSSAL_POUCH),
            new TriggerItem(ItemID.COLOSSAL_POUCH_26786), // Degrated
        };

        this.triggers = new TriggerBase[]{
            // Empty
            new OnChatMessage("There is no essence in this pouch.").emptyStorage(),
            new OnChatMessage("The rift becomes active!").emptyStorage(),

            // Check
            new OnChatMessage("There (?:is|are) (?<charges>.+?) (?<essence>pure|daeyalt|guardian)? ?essences? in this pouch.").matcherConsumer((m) -> {
                storage.clear();

                String chargesMatch = m.group("charges");
                int charges = getNumberFromWordRepresentation(chargesMatch);

                int itemID;
                switch (m.group("essence")) {
                    case "pure":
                        itemID = ItemID.PURE_ESSENCE;
                        break;
                    case "daeyalt":
                        itemID = ItemID.DAEYALT_ESSENCE;
                        break;
                    case "guardian":
                        itemID = ItemID.GUARDIAN_ESSENCE;
                        break;
                    default:
                        itemID = ItemID.RUNE_ESSENCE;
                        break;
                }
                
                storage.put(itemID, charges);
            }).onMenuOption("Check"),

            // Decay.
            new OnChatMessage("Your pouch has decayed through use.").onMenuOption("Fill").consumer(() -> {
                int decayCount = config.getColossalPouchDecayCount();
                configs.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.colossal_pouch_decay_count, decayCount + 1);
                this.storage.maximumTotalQuantity(getMaxCharges());
            }),
            
            // Repair.
            new OnChatMessage( "Fine. A simple transfiguration spell should resolve things for you.").consumer(() -> {
                configs.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.colossal_pouch_decay_count, 0);
                this.storage.maximumTotalQuantity(getMaxCharges());
            }),

            // Fill from inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventorySingle().onMenuOption("Fill"),

            // Fill from bank.
            new OnItemContainerChanged(ItemContainerId.BANK).fillStorageFromInventorySingle().onMenuOption("Fill"),

            // Use essence on pouch.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventorySingle().onUseStorageItemOnChargedItem(storage.getStoreableItems()),

            // Empty to inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Set maximum charges on level up
            new OnStatChanged(Skill.RUNECRAFT).consumer(() -> {
                this.storage.maximumTotalQuantity(getMaxCharges());
            }),
        };
    }

    public int getMaxCharges() {
        int decayCount = config.getColossalPouchDecayCount();
        int runecraftLevel = this.client.getRealSkillLevel(Skill.RUNECRAFT);
        if (runecraftLevel >= 85) {
            return 40 - (decayCount * 5);
        }
        else if (runecraftLevel >= 75) {
            return 27 - (decayCount * 3);
        }
        else if (runecraftLevel >= 50) {
            return 16 - (decayCount * 2);
        }
        else return 8;
    }
}
