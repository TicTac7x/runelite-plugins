package tictac7x.charges.store;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;

import java.util.*;
import java.util.function.Consumer;

public class Store {
    private final Client client;
    private final ItemManager itemManager;
    private final ConfigManager configManager;

    private int gametick = 0;
    private int gametick_before = 0;

    private Optional<ChargedItemBase[]> chargedItems = Optional.empty();
    private List<Integer> dailyResetItemIds = new ArrayList<>();
    private Optional<String> lastChatMessage = Optional.empty();
    public Optional<ItemContainer> inventory = Optional.empty();
    public Optional<ItemContainer> equipment = Optional.empty();
    public Optional<ItemContainer> bank = Optional.empty();

    public List<StorageItem> currentInventoryItems = new ArrayList<>();
    public List<StorageItem> previousInventoryItems = new ArrayList<>();
    public List<StorageItem> currentBankItems = new ArrayList<>();
    public List<StorageItem> previousBankItems = new ArrayList<>();
    public final Queue<Runnable> nextTickQueue = new ArrayDeque<>();

    public final List<AdvancedMenuEntry> menuOptionsClicked = new ArrayList<>();
    private final Map<Skill, Integer> skillsXp = new HashMap<>();

    public Store(final Client client, final ItemManager itemManager, final ConfigManager configManager) {
        this.client = client;
        this.itemManager = itemManager;
        this.configManager = configManager;
    }

    public Optional<String> getLastChatMessage() {
        return this.lastChatMessage;
    }

    public void setLastChatMessage(final ChatMessage event) {
        if (event.getType() == ChatMessageType.GAMEMESSAGE || event.getType() == ChatMessageType.DIALOG || event.getType() == ChatMessageType.SPAM) {
            lastChatMessage = Optional.of(TicTac7xChargesImprovedPlugin.getCleanChatMessage(event));
        }
    }

    public void setChargedItems(final ChargedItemBase[] chargedItems) {
        this.chargedItems = Optional.of(chargedItems);

        List<Integer> dailyResetItemIds = new ArrayList<>();
        Arrays.stream(chargedItems).filter(chargedItem -> {
            for (final TriggerBase trigger : chargedItem.triggers) {
                if (trigger instanceof OnResetDaily) {
                    return true;
                }
            }
            return false;
        }).forEach(chargedItem -> {
            for (final TriggerItem triggerItem : chargedItem.items) {
                dailyResetItemIds.add(triggerItem.itemId);
            }
        });
        this.dailyResetItemIds = dailyResetItemIds;
    }

    public Optional<Integer> getSkillXp(final Skill skill) {
        if (skillsXp.containsKey(skill)) {
            return Optional.of(skillsXp.get(skill));
        }

        return Optional.empty();
    }

    public int getInventoryPreviouslyEmptySlots() {
        return 28 - previousInventoryItems.size();
    }

    public int getInventoryEmptySlots() {
        return 28 - currentInventoryItems.size();
    }

    public void onStatChanged(final StatChanged event) {
        skillsXp.put(event.getSkill(), event.getXp());
    }

    public void onItemContainerChanged(final ItemContainerChanged event) {
        runNextGameTickQueue();

        // Update inventory, save previous items.
        if (event.getContainerId() == InventoryID.INVENTORY.getId()) {
            inventory = Optional.of(event.getItemContainer());

            previousInventoryItems = new ArrayList<>();
            for (final StorageItem storageItem : currentInventoryItems) {
                previousInventoryItems.add(new StorageItem(storageItem.itemId, storageItem.quantity));
            }

            currentInventoryItems = new ArrayList<>();
            for (final Item item : event.getItemContainer().getItems()) {
                if (isInvalidItem(item)) continue;
                currentInventoryItems.add(new StorageItem(item.getId(), item.getQuantity()));
            }
        } else if (event.getContainerId() == InventoryID.EQUIPMENT.getId()) {
            equipment = Optional.of(event.getItemContainer());
        } else if (event.getContainerId() == InventoryID.BANK.getId()) {
            bank = Optional.of(event.getItemContainer());

            previousBankItems = new ArrayList<>();
            for (final StorageItem storageItem : currentBankItems) {
                previousBankItems.add(new StorageItem(storageItem.itemId, storageItem.quantity));
            }

            currentBankItems = new ArrayList<>();
            for (final Item item : event.getItemContainer().getItems()) {
                if (isInvalidItem(item)) continue;
                currentBankItems.add(new StorageItem(item.getId(), item.getQuantity()));
            }
        }

        updateStorage(event);
    }

    public void onMenuOptionClicked(final AdvancedMenuEntry advancedMenuEntry) {
        // Gametick changed, clear previous menu entries since they are no longer valid.
        if (gametick >= gametick_before + 2) {
            gametick = 0; gametick_before = 0;
            menuOptionsClicked.clear();
        }

        // Save menu option and target for other triggers to use.
        menuOptionsClicked.add(advancedMenuEntry);
    }

    private void runNextGameTickQueue() {
        while (!nextTickQueue.isEmpty()) {
            final Runnable consumer = nextTickQueue.poll();
            consumer.run();
        }
    }

    public void onGameTick(final GameTick ignored) {
        runNextGameTickQueue();

        // Automatically load all skill xps.
        if (!getSkillXp(Skill.MAGIC).isPresent()) {
            skillsXp.put(Skill.AGILITY, client.getSkillExperience(Skill.AGILITY));
            skillsXp.put(Skill.ATTACK, client.getSkillExperience(Skill.ATTACK));
            skillsXp.put(Skill.CONSTRUCTION, client.getSkillExperience(Skill.CONSTRUCTION));
            skillsXp.put(Skill.COOKING, client.getSkillExperience(Skill.COOKING));
            skillsXp.put(Skill.CRAFTING, client.getSkillExperience(Skill.CRAFTING));
            skillsXp.put(Skill.DEFENCE, client.getSkillExperience(Skill.DEFENCE));
            skillsXp.put(Skill.FARMING, client.getSkillExperience(Skill.FARMING));
            skillsXp.put(Skill.FIREMAKING, client.getSkillExperience(Skill.FIREMAKING));
            skillsXp.put(Skill.FISHING, client.getSkillExperience(Skill.FISHING));
            skillsXp.put(Skill.FLETCHING, client.getSkillExperience(Skill.FLETCHING));
            skillsXp.put(Skill.HERBLORE, client.getSkillExperience(Skill.HERBLORE));
            skillsXp.put(Skill.HITPOINTS, client.getSkillExperience(Skill.HITPOINTS));
            skillsXp.put(Skill.HUNTER, client.getSkillExperience(Skill.HUNTER));
            skillsXp.put(Skill.MAGIC, client.getSkillExperience(Skill.MAGIC));
            skillsXp.put(Skill.MINING, client.getSkillExperience(Skill.MINING));
            skillsXp.put(Skill.PRAYER, client.getSkillExperience(Skill.PRAYER));
            skillsXp.put(Skill.RANGED, client.getSkillExperience(Skill.RANGED));
            skillsXp.put(Skill.RUNECRAFT, client.getSkillExperience(Skill.RUNECRAFT));
            skillsXp.put(Skill.SLAYER, client.getSkillExperience(Skill.SLAYER));
            skillsXp.put(Skill.SMITHING, client.getSkillExperience(Skill.SMITHING));
            skillsXp.put(Skill.STRENGTH, client.getSkillExperience(Skill.STRENGTH));
            skillsXp.put(Skill.THIEVING, client.getSkillExperience(Skill.THIEVING));
            skillsXp.put(Skill.WOODCUTTING, client.getSkillExperience(Skill.WOODCUTTING));
        }
        gametick++;

        // Keep only last menu entry.
        if (menuOptionsClicked.size() > 1) {
            final AdvancedMenuEntry lastMenuEntry = menuOptionsClicked.get(menuOptionsClicked.size() - 1);
            menuOptionsClicked.clear();
            menuOptionsClicked.add(lastMenuEntry);
        }
    }

    public boolean inMenuTargets(final int ...itemIds) {
        final String[] targets = new String[itemIds.length];

        for (int i = 0; i < itemIds.length; i++) {
            targets[i] = itemManager.getItemComposition(itemIds[i]).getName();
        }

        return inMenuTargets(targets);
    }

    public boolean inMenuTargets(final String ...targets) {
        for (final String target : targets) {
            for (final AdvancedMenuEntry advancedMenuEntry : menuOptionsClicked) {
                if (advancedMenuEntry.target.contains(target)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean notInMenuTargets(final String ...targets) {
        return !inMenuTargets(targets);
    }

    public boolean notInMenuTargets(final StorageItem... storageItems) {
        final int[] storeableItemIds = new int[storageItems.length];

        for (int i = 0; i < storageItems.length; i ++) {
            storeableItemIds[i] = storageItems[i].itemId;
        }

        return notInMenuTargets(storeableItemIds);
    }

    public boolean notInMenuTargets(final int ...itemIds) {
        return !inMenuTargets(itemIds);
    }

    public boolean inMenuOptions(final String ...options) {
        for (final AdvancedMenuEntry advancedMenuEntry : menuOptionsClicked) {
            for (final String option : options) {
                if (advancedMenuEntry.option.equals(option)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean notInMenuOptions(final String ...options) {
        return !inMenuOptions(options);
    }

    public boolean inMenuOptionIds(final int ...menuOptionIds) {
        for (final AdvancedMenuEntry advancedMenuEntry : menuOptionsClicked) {
            for (final int menuOptionId : menuOptionIds) {
                if (advancedMenuEntry.eventId == menuOptionId) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean notInMenuOptionIds(final int ...menuOptionsIds) {
        return !inMenuOptionIds(menuOptionsIds);
    }

    public boolean inMenuImpostors(final int ...impostorIds) {
        for (final AdvancedMenuEntry advancedMenuEntry : menuOptionsClicked) {
            for (final int impostorId : impostorIds) {
                if (advancedMenuEntry.impostorId == impostorId) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean notInMenuImpostors(final int ...impostorIds) {
        return !inMenuImpostors(impostorIds);
    }

    public int getInventoryItemQuantity(final int itemId) {
        int quantity = 0;

        for (final StorageItem storageItem : currentInventoryItems) {
            if (storageItem.itemId == itemId) {
                quantity += storageItem.quantity;
            }
        }

        return quantity;
    }

    public int getEquipmentItemQuantity(final int itemId) {
        int quantity = 0;

        if (equipment.isPresent()) {
            for (final Item item : equipment.get().getItems()) {
                if (item.getId() == itemId) {
                    quantity += item.getQuantity();
                }
            }
        }

        return quantity;
    }

    public int getPreviousInventoryItemQuantity(final int itemId) {
        int quantity = 0;

        for (final StorageItem storageItem : previousInventoryItems) {
            if (storageItem.itemId == itemId) {
                quantity += storageItem.quantity;
            }
        }

        return quantity;
    }

    public boolean inventoryContainsItem(final int itemId) {
        for (final StorageItem storageItem : currentInventoryItems) {
            if (storageItem.itemId == itemId) {
                return true;
            }
        }

        return false;
    }

    public boolean equipmentContainsItem(final int ...itemIds) {
        if (!equipment.isPresent()) return false;

        for (final Item equipmentItem : equipment.get().getItems()) {
            for (final int itemId : itemIds) {
                if (equipmentItem.getId() == itemId) {
                    return true;
                }
            }
        }

        return false;
    }

    private void updateStorage(final ItemContainerChanged event) {
        if (
            event.getContainerId() != InventoryID.BANK.getId() &&
            event.getContainerId() != InventoryID.INVENTORY.getId() &&
            event.getContainerId() != InventoryID.EQUIPMENT.getId()
        ) {
            return;
        }

        final List<Integer> itemIds = new ArrayList<>();

        // Update items.
        for (final Item item : event.getItemContainer().getItems()) {
            if (item.getId() != -1 && item.getQuantity() > 0 && dailyResetItemIds.contains(item.getId())) {
                itemIds.add(item.getId());
            }
        }

        final StringBuilder storageStringBuilder = new StringBuilder();
        for (final Integer itemId : itemIds) {
            storageStringBuilder.append(itemId).append(",");
        }
        final String storageString = storageStringBuilder.toString().replaceAll(",$", "");

        // Save storage string to config.
        if (event.getContainerId() == InventoryID.BANK.getId()) {
            configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.storage_bank, storageString);
        } else if (event.getContainerId() == InventoryID.INVENTORY.getId()) {
            configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.storage_inventory, storageString);
        } else if (event.getContainerId() == InventoryID.EQUIPMENT.getId()) {
            configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.storage_equipment, storageString);
        }
    }

    private Set<Integer> getAllItems() {
        final Set<Integer> allItems = new HashSet<>();

        final String storageBank = configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.storage_bank);
        final String storageInventory = configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.storage_inventory);
        final String storageEquipment = configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.storage_equipment);

        final List<String> storages = new ArrayList<>();
        storages.addAll(Arrays.asList(storageBank.split(",")));
        storages.addAll(Arrays.asList(storageInventory.split(",")));
        storages.addAll(Arrays.asList(storageEquipment.split(",")));

        for (final String itemId : storages) {
            try {
                allItems.add(Integer.parseInt(itemId));
            } catch (final Exception ignored) {}
        }

        return allItems;
    }

    public boolean itemInPossession(final int itemId) {
        for (final int item : getAllItems()) {
            if (item == itemId) {
                return true;
            }
        }

        return false;
    }

    public ItemsDifference getInventoryItemsDifference() {
        final List<ItemWithQuantity> itemsDifference = new ArrayList<>();

        final Map<Integer, Integer> quantitiesNew = new HashMap<>();
        final Map<Integer, Integer> quantitiesBefore = new HashMap<>();

        if (inventory.isPresent()) {
            for (final Item itemNew : inventory.get().getItems()) {
                if (isInvalidItem(itemNew) || quantitiesNew.containsKey(itemNew.getId())) continue;
                quantitiesNew.put(itemNew.getId(), inventory.get().count(itemNew.getId()));
            }

            for (final StorageItem itemOld : previousInventoryItems) {
                if (quantitiesBefore.containsKey(itemOld.itemId)) {
                    quantitiesBefore.put(itemOld.itemId, quantitiesBefore.get(itemOld.itemId) + itemOld.quantity);
                } else {
                    quantitiesBefore.put(itemOld.itemId, itemOld.quantity);
                }
            }
        }

        for (final int itemId : quantitiesNew.keySet()) {
            final int quantity = quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0);
            if (quantity != 0) {
                itemsDifference.add(new ItemWithQuantity(itemId, quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0)));
            }
        }

        for (final int itemId : quantitiesBefore.keySet()) {
            if (!quantitiesNew.containsKey(itemId)) {
                itemsDifference.add(new ItemWithQuantity(itemId, -quantitiesBefore.get(itemId)));
            }
        }

        return new ItemsDifference(itemsDifference);
    }

    public ItemsDifference getBankItemsDifference() {
        final List<ItemWithQuantity> itemsDifference = new ArrayList<>();

        final Map<Integer, Integer> quantitiesNew = new HashMap<>();
        final Map<Integer, Integer> quantitiesBefore = new HashMap<>();

        if (bank.isPresent()) {
            for (final Item itemNew : bank.get().getItems()) {
                if (isInvalidItem(itemNew)) continue;
                quantitiesNew.put(itemNew.getId(), bank.get().count(itemNew.getId()));
            }

            for (final StorageItem itemOld : previousBankItems) {
                if (quantitiesBefore.containsKey(itemOld.itemId)) {
                    quantitiesBefore.put(itemOld.itemId, quantitiesBefore.get(itemOld.itemId) + itemOld.quantity);
                } else {
                    quantitiesBefore.put(itemOld.itemId, itemOld.quantity);
                }
            }
        }

        for (final int itemId : quantitiesNew.keySet()) {
            itemsDifference.add(new ItemWithQuantity(itemId, quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0)));
        }

        for (final int itemId : quantitiesBefore.keySet()) {
            if (!quantitiesNew.containsKey(itemId)) {
                itemsDifference.add(new ItemWithQuantity(itemId, -quantitiesBefore.get(itemId)));
            }
        }

        return new ItemsDifference(itemsDifference);
    }

    private boolean isInvalidItem(final Item item) {
        return item == null | item.getId() == -1 || item.getId() == 6512;
    }

    public void addConsumerToNextTickQueue(final Runnable consumer) {
        nextTickQueue.add(consumer);
    }
}
