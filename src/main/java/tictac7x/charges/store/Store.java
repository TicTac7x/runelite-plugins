package tictac7x.charges.store;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.events.CustomChatMessage;
import tictac7x.charges.events.CustomHitsplatApplied;
import tictac7x.charges.events.CustomMenuOptionClicked;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.events.CustomItemContainerChanged;
import tictac7x.charges.item.storage.StorageItems;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.GraphicId;
import tictac7x.charges.store.ids.ItemContainerId;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Store {
    private final Client client;
    private final ItemManager itemManager;
    private final ConfigManager configManager;
    private final int HIGHEST_MONSTER_ATTACK_SPEED = 8;

    private int gametick = 0;
    private int gametick_before = 0;
    private int inCombatTicksRemainingDamageDoneToOthers = 0;
    private int inCombatTicksRemainingDamageDoneToMe = 0;

    private ChargedItemBase[] chargedItems = new ChargedItemBase[]{};
    private int lastChatMessagesTick = 0;
    private List<String> lastChatMessages = new ArrayList<>();

    public CustomItemContainerChanged inventory = new CustomItemContainerChanged(ItemContainerId.INVENTORY, new ArrayList<>());
    public CustomItemContainerChanged previousInventory = new CustomItemContainerChanged(ItemContainerId.INVENTORY, new ArrayList<>());
    public CustomItemContainerChanged equipment = new CustomItemContainerChanged(ItemContainerId.EQUIPMENT, new ArrayList<>());
    public CustomItemContainerChanged bank = new CustomItemContainerChanged(ItemContainerId.BANK, new ArrayList<>());
    public CustomItemContainerChanged previousBank = new CustomItemContainerChanged(ItemContainerId.BANK, new ArrayList<>());


    public final Queue<Runnable> nextTickQueue = new ArrayDeque<>();
    public final List<CustomMenuOptionClicked> menuOptionsClicked = new ArrayList<>();
    private final Map<Skill, Integer> skillsXp = new HashMap<>();

    final Pattern withdrawPattern = Pattern.compile("Withdraw-(?<amount>.+)");

    public Store(final Client client, final ItemManager itemManager, final ConfigManager configManager) {
        this.client = client;
        this.itemManager = itemManager;
        this.configManager = configManager;
    }

    public List<String> getLastChatMessages() {
        return lastChatMessages;
    }

    public void onChatMessage(final CustomChatMessage chatMessage) {
        switch (chatMessage.type) {
            case GAMEMESSAGE:
            case DIALOG:
            case SPAM:
                break;
            default:
                return;
        }

        final int tick = client.getTickCount();
        if (tick != lastChatMessagesTick) {
            lastChatMessages = new ArrayList<>();
            lastChatMessagesTick = tick;
        }

        lastChatMessages.add(chatMessage.message);
    }

    public void setChargedItems(final ChargedItemBase[] chargedItems) {
        this.chargedItems = chargedItems;
    }

    public Optional<Integer> getSkillXp(final Skill skill) {
        if (skillsXp.containsKey(skill)) {
            return Optional.of(skillsXp.get(skill));
        }

        return Optional.empty();
    }

    public int getInventoryEmptySlots() {
        return 28 - inventory.size();
    }

    public void onStatChanged(final StatChanged event) {
        skillsXp.put(event.getSkill(), event.getXp());
    }

    public void onItemContainerChanged(final CustomItemContainerChanged itemContainerChanged) {
        runNextGameTickQueue();

        if (
            itemContainerChanged.getContainerId() == ItemContainerId.BANK ||
            itemContainerChanged.getContainerId() == ItemContainerId.INVENTORY ||
            itemContainerChanged.getContainerId() == ItemContainerId.EQUIPMENT
        ) {
            // Update inventory, save previous items.
            if (itemContainerChanged.getContainerId() == ItemContainerId.INVENTORY) {
                previousInventory = inventory;
                inventory = itemContainerChanged;
            } else if (itemContainerChanged.getContainerId() == ItemContainerId.EQUIPMENT) {
                equipment = itemContainerChanged;
            } else if (itemContainerChanged.getContainerId() == ItemContainerId.BANK) {
                previousBank = bank;
                bank = itemContainerChanged;

                final StringBuilder storageStringBuilder = new StringBuilder();
                for (final StorageItem item : itemContainerChanged.getItems()) {
                    storageStringBuilder.append(item.getId()).append(",");
                }
                final String storageString = storageStringBuilder.toString().replaceAll(",$", "");
                configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.storage_bank, storageString);
            }

            updateChargedItemsPrimaryId(itemContainerChanged.getContainerId() == ItemContainerId.BANK);
        }

        for (final ChargedItemBase infobox : chargedItems) {
            infobox.onItemContainerChanged(itemContainerChanged);
        }
    }

    private void updateChargedItemsPrimaryId(final boolean checkBank) {
        for (final ChargedItemBase chargedItem : chargedItems) {
            Optional<Integer> bankItemId = Optional.empty();
            boolean bankItemDynamic = false;

            Optional<Integer> inventoryItemId = Optional.empty();
            boolean inventoryItemDynamic = false;

            Optional<Integer> equipmentItemId = Optional.empty();
            boolean equipmentItemDynamic = false;

            // Bank has the least priority.
            if (checkBank) {
                for (final StorageItem item : bank.getItems()) {
                    for (final TriggerItem triggerItem : chargedItem.items) {
                        if (item.getId() == triggerItem.itemId) {
                            if (!bankItemId.isPresent() || triggerItem.fixedCharges.isPresent() && !bankItemDynamic || !triggerItem.fixedCharges.isPresent()) {
                                bankItemId = Optional.of(item.getId());

                                if (!triggerItem.fixedCharges.isPresent()) {
                                    bankItemDynamic = true;
                                }
                            }
                        }
                    }
                }
            }

            // Inventory is more important than bank.
            for (final StorageItem item : inventory.getItems()) {
                for (final TriggerItem triggerItem : chargedItem.items) {
                    if (item.getId() == triggerItem.itemId) {
                        if (!inventoryItemId.isPresent() || triggerItem.fixedCharges.isPresent() && !inventoryItemDynamic || !triggerItem.fixedCharges.isPresent()) {
                            inventoryItemId = Optional.of(item.getId());

                            if (!triggerItem.fixedCharges.isPresent()) {
                                inventoryItemDynamic = true;
                            }
                        }
                    }
                }
            }

            // Equipment has most priority.
            for (final StorageItem item : equipment.getItems()) {
                for (final TriggerItem triggerItem : chargedItem.items) {
                    if (item.getId() == triggerItem.itemId) {
                        if (!equipmentItemId.isPresent() || triggerItem.fixedCharges.isPresent() && !equipmentItemDynamic || !triggerItem.fixedCharges.isPresent()) {
                            equipmentItemId = Optional.of(item.getId());

                            if (!triggerItem.fixedCharges.isPresent()) {
                                equipmentItemDynamic = true;
                            }
                        }
                    }
                }
            }

            chargedItem.inEquipment = equipmentItemId.isPresent();
            chargedItem.inInventory = inventoryItemId.isPresent();
            chargedItem.itemId =
                equipmentItemId.isPresent() ? equipmentItemId.get() :
                inventoryItemId.isPresent() ? inventoryItemId.get() :
                bankItemId.isPresent() ? bankItemId.get() :
                chargedItem.itemId;
        }
    }

    public void onMenuOptionClicked(final CustomMenuOptionClicked customMenuOptionClicked) {
        checkBankWithdraw(customMenuOptionClicked);

        // Gametick changed, clear previous menu entries since they are no longer valid.
        if (gametick >= gametick_before + 2) {
            gametick = 0; gametick_before = 0;
            menuOptionsClicked.clear();
        }

        // Save menu option and target for other triggers to use.
        menuOptionsClicked.add(customMenuOptionClicked);
    }

    private void checkBankWithdraw(final CustomMenuOptionClicked customMenuOptionClicked) {
        final Matcher matcher = withdrawPattern.matcher(customMenuOptionClicked.option);
        if (!matcher.find()) return;

        final String amountString = matcher.group("amount");
        if (amountString.equals("X")) return;

        final int amount =
            amountString.equals("All") ? bank.count(customMenuOptionClicked.itemId) :
            amountString.equals("All-but-1") ? bank.count(customMenuOptionClicked.itemId) - 1 :
            Integer.parseInt(amountString);

        final ItemComposition itemComposition = itemManager.getItemComposition(customMenuOptionClicked.itemId);

        // Copy of current inventory.
        final CustomItemContainerChanged itemContainerChanged = new CustomItemContainerChanged(inventory);

        // Add new items.
        if (itemComposition.isStackable() || client.getVarbitValue(3958) == 1) {
            itemContainerChanged.addStackableItem(new StorageItem(customMenuOptionClicked.itemId, amount));
        } else {
            itemContainerChanged.addNonStackableItem(new StorageItem(customMenuOptionClicked.itemId, Math.min(amount, 28 - inventory.getItems().size())));
        }

        onItemContainerChanged(itemContainerChanged);
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
            final CustomMenuOptionClicked lastMenuEntry = menuOptionsClicked.get(menuOptionsClicked.size() - 1);
            menuOptionsClicked.clear();
            menuOptionsClicked.add(lastMenuEntry);
        }

        if (isInCombat()) {
            for (final ChargedItemBase chargedItem : chargedItems) {
                chargedItem.onCombat();
            }
        }

        inCombatTicksRemainingDamageDoneToOthers = Math.max(0, inCombatTicksRemainingDamageDoneToOthers - 1);
        inCombatTicksRemainingDamageDoneToMe = Math.max(0, inCombatTicksRemainingDamageDoneToMe - 1);
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
            for (final CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
                final boolean found = Pattern.compile(target).matcher(customMenuOptionClicked.target).find();
                if (found) return true;
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
            storeableItemIds[i] = storageItems[i].getId();
        }

        return notInMenuTargets(storeableItemIds);
    }

    public boolean notInMenuTargets(final int ...itemIds) {
        return !inMenuTargets(itemIds);
    }

    public boolean inMenuOptions(final String ...options) {
        for (final CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
            for (final String option : options) {
                if (customMenuOptionClicked.option.equals(option)) {
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
        for (final CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
            for (final int menuOptionId : menuOptionIds) {
                if (customMenuOptionClicked.eventId == menuOptionId) {
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
        for (final CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
            for (final int impostorId : impostorIds) {
                if (customMenuOptionClicked.impostorId == impostorId) {
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

        for (final StorageItem storageItem : inventory.getItems()) {
            if (storageItem.getId() == itemId) {
                quantity += storageItem.getQuantity();
            }
        }

        return quantity;
    }

    public int getEquipmentItemQuantity(final int itemId) {
        int quantity = 0;

        for (final StorageItem item : equipment.getItems()) {
            if (item.getId() == itemId) {
                quantity += item.getQuantity();
            }
        }

        return quantity;
    }

    public int getPreviousInventoryItemQuantity(final int itemId) {
        int quantity = 0;

        for (final StorageItem storageItem : previousInventory.getItems()) {
            if (storageItem.getId() == itemId) {
                quantity += storageItem.getQuantity();
            }
        }

        return quantity;
    }

    public boolean inventoryContainsItem(final int itemId) {
        for (final StorageItem storageItem : inventory.getItems()) {
            if (storageItem.getId() == itemId) {
                return true;
            }
        }

        return false;
    }

    public boolean equipmentContainsItem(final int ...itemIds) {
        for (final StorageItem equipmentItem : equipment.getItems()) {
            for (final int itemId : itemIds) {
                if (equipmentItem.getId() == itemId) {
                    return true;
                }
            }
        }

        return false;
    }

    private List<StorageItem> getAllItems() {
        final List<StorageItem> allItems = new ArrayList<>();
        allItems.addAll(inventory.getItems());
        allItems.addAll(equipment.getItems());
        allItems.addAll(bank.getItems());
        return allItems;
    }

    public boolean itemInPossession(final int itemId) {
        for (final StorageItem item : getAllItems()) {
            if (item.getId() == itemId) {
                return true;
            }
        }

        return false;
    }

    public StorageItems getInventoryItemsDifference() {
        final StorageItems itemsDifference = new StorageItems();

        final Map<Integer, Integer> quantitiesNew = new HashMap<>();
        final Map<Integer, Integer> quantitiesBefore = new HashMap<>();

        for (final StorageItem itemNew : inventory.getItems()) {
            if (quantitiesNew.containsKey(itemNew.getId())) continue;
            quantitiesNew.put(itemNew.getId(), inventory.count(itemNew.getId()));
        }

        for (final StorageItem itemOld : previousInventory.getItems()) {
            if (quantitiesBefore.containsKey(itemOld.getId())) {
                quantitiesBefore.put(itemOld.getId(), quantitiesBefore.get(itemOld.getId()) + itemOld.getQuantity());
            } else {
                quantitiesBefore.put(itemOld.getId(), itemOld.getQuantity());
            }
        }

        for (final int itemId : quantitiesNew.keySet()) {
            final int quantity = quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0);
            if (quantity != 0) {
                itemsDifference.put(new StorageItem(itemId, quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0)));
            }
        }

        for (final int itemId : quantitiesBefore.keySet()) {
            if (!quantitiesNew.containsKey(itemId)) {
                itemsDifference.put(new StorageItem(itemId, -quantitiesBefore.get(itemId)));
            }
        }

        return itemsDifference;
    }

    public StorageItems getBankItemsDifference() {
        final StorageItems itemsDifference = new StorageItems();

        final Map<Integer, Integer> quantitiesNew = new HashMap<>();
        final Map<Integer, Integer> quantitiesBefore = new HashMap<>();

        for (final StorageItem itemNew : bank.getItems()) {
            if (quantitiesNew.containsKey(itemNew.getId())) continue;
            quantitiesNew.put(itemNew.getId(), bank.count(itemNew.getId()));
        }

        for (final StorageItem itemOld : previousBank.getItems()) {
            if (quantitiesBefore.containsKey(itemOld.getId())) {
                quantitiesBefore.put(itemOld.getId(), quantitiesBefore.get(itemOld.getId()) + itemOld.getQuantity());
            } else {
                quantitiesBefore.put(itemOld.getId(), itemOld.getQuantity());
            }
        }

        for (final int itemId : quantitiesNew.keySet()) {
            final int quantity = quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0);
            if (quantity != 0) {
                itemsDifference.put(new StorageItem(itemId, quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0)));
            }
        }

        for (final int itemId : quantitiesBefore.keySet()) {
            if (!quantitiesNew.containsKey(itemId)) {
                itemsDifference.put(new StorageItem(itemId, -quantitiesBefore.get(itemId)));
            }
        }

        return itemsDifference;
    }

    public void addConsumerToNextTickQueue(final Runnable consumer) {
        nextTickQueue.add(consumer);
    }

    public void onHitSplatApplied(final CustomHitsplatApplied event) {
        if (event.byMe) {
            inCombatTicksRemainingDamageDoneToOthers = HIGHEST_MONSTER_ATTACK_SPEED;
        }

        if (event.toMe) {
            inCombatTicksRemainingDamageDoneToMe = HIGHEST_MONSTER_ATTACK_SPEED;
        }
    }

    public void onGraphicChanged(final GraphicChanged event) {
        if (event.getActor() == client.getLocalPlayer() && event.getActor().getGraphic() == GraphicId.SPLASH) {
            inCombatTicksRemainingDamageDoneToOthers = HIGHEST_MONSTER_ATTACK_SPEED;
        }
    }

    public boolean isInCombat() {
        return inCombatTicksRemainingDamageDoneToOthers > 0;
    }

    public boolean isLockedInCombat() {
        return inCombatTicksRemainingDamageDoneToMe > 0;
    }
}
