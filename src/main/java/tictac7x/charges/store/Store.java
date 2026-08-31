package tictac7x.charges.store;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.client.chat.*;
import net.runelite.client.config.*;
import net.runelite.client.events.*;
import net.runelite.client.game.*;
import tictac7x.charges.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.listeners.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.utils.*;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class Store {
    private Client client;
    private ItemManager itemManager;
    private ConfigManager configManager;
    private Provider provider;

    private int HIGHEST_MONSTER_ATTACK_SPEED = 8;

    private int gametick = 0;
    private int gametickBefore = 0;
    private int inCombatTicksRemainingDamageDoneToOthers = 0;
    private int inCombatTicksRemainingDamageDoneToMe = 0;

    private ChargedItemBase[] chargedItems = new ChargedItemBase[]{};
    private int lastChatMessagesTick = 0;
    private List<String> lastChatMessages = new ArrayList<>();

    public CustomItemContainerChanged inventory = new CustomItemContainerChanged(InventoryID.INV, new ArrayList<>());
    public CustomItemContainerChanged previousInventory = new CustomItemContainerChanged(InventoryID.INV, new ArrayList<>());
    public CustomItemContainerChanged equipment = new CustomItemContainerChanged(InventoryID.WORN, new ArrayList<>());
    public CustomItemContainerChanged bank = new CustomItemContainerChanged(InventoryID.BANK, new ArrayList<>());
    public CustomItemContainerChanged previousBank = new CustomItemContainerChanged(InventoryID.BANK, new ArrayList<>());


    public Queue<Runnable> nextTickQueue = new ArrayDeque<>();
    public Optional<CustomMenuOptionClicked> previousMenuOptionClicked = Optional.empty();
    public List<CustomMenuOptionClicked> menuOptionsClicked = new ArrayList<>();
    private List<CustomWidgetMenuOptionClicked> widgetMenuActionsClicked = new ArrayList<>();
    private Map<Skill, Integer> skillsXp = new HashMap<>();

    Pattern withdrawPattern = Pattern.compile("Withdraw-(?<amount>.+)");

    private ListenerOnChatMessage listenerOnChatMessage;
    private ListenerOnItemContainerChanged listenerOnItemContainerChanged;
    private ListenerOnItemPickup listenerOnItemPickup;
    private ListenerOnXpDrop listenerOnXpDrop;
    private ListenerOnStatChanged listenerOnStatChanged;
    private ListenerOnMenuEntryAdded listenerOnMenuEntryAdded;
    private ListenerOnResetDaily listenerOnResetDaily;
    private ListenerOnGraphicChanged listenerOnGraphicChanged;
    private ListenerOnAnimationChanged listenerOnAnimationChanged;
    private ListenerOnHitsplatApplied listenerOnHitsplatApplied;
    private ListenerOnWidgetLoaded listenerOnWidgetLoaded;
    private ListenerOnVarbitChanged listenerOnVarbitChanged;
    private ListenerOnVarbitsMapChanged listenerOnVarbitsMapChanged;
    private ListenerOnUserAction listenerOnUserAction;
    private ListenerOnMenuOptionClicked listenerOnMenuOptionClicked;
    private ListenerOnItemUsed listenerOnItemUsed;
    private ListenerOnScriptPreFired listenerOnScriptPreFired;
    private ListenerOnCombat listenerOnCombat;
    private ListenerOnMenuOpened listenerOnMenuOpened;
    private ListenerOnGameTick listenerOnGameTick;

    public Store(Client client, ItemManager itemManager, ConfigManager configManager) {
        this.client = client;
        this.itemManager = itemManager;
        this.configManager = configManager;
    }

    public Store addProvider(Provider provider) {
        this.provider = provider;
        listenerOnChatMessage = new ListenerOnChatMessage(provider);
        listenerOnItemContainerChanged = new ListenerOnItemContainerChanged(provider);
        listenerOnItemPickup = new ListenerOnItemPickup(provider);
        listenerOnXpDrop = new ListenerOnXpDrop(provider);
        listenerOnStatChanged = new ListenerOnStatChanged(provider);
        listenerOnMenuEntryAdded = new ListenerOnMenuEntryAdded(provider);
        listenerOnResetDaily = new ListenerOnResetDaily(provider);
        listenerOnGraphicChanged = new ListenerOnGraphicChanged(provider);
        listenerOnAnimationChanged = new ListenerOnAnimationChanged(provider);
        listenerOnHitsplatApplied = new ListenerOnHitsplatApplied(provider);
        listenerOnWidgetLoaded = new ListenerOnWidgetLoaded(provider);
        listenerOnVarbitChanged = new ListenerOnVarbitChanged(provider);
        listenerOnVarbitsMapChanged = new ListenerOnVarbitsMapChanged(provider);
        listenerOnUserAction = new ListenerOnUserAction(provider);
        listenerOnMenuOptionClicked = new ListenerOnMenuOptionClicked(provider);
        listenerOnItemUsed = new ListenerOnItemUsed(provider);
        listenerOnScriptPreFired = new ListenerOnScriptPreFired(provider);
        listenerOnCombat = new ListenerOnCombat(provider);
        listenerOnMenuOpened = new ListenerOnMenuOpened(provider);
        listenerOnGameTick = new ListenerOnGameTick(provider);

        return this;
    }

    public List<String> getLastChatMessages() {
        return lastChatMessages;
    }

    public void setChargedItems(ChargedItemBase[] chargedItems) {
        this.chargedItems = chargedItems;
    }

    public Optional<Integer> getSkillXp(Skill skill) {
        if (skillsXp.containsKey(skill)) {
            return Optional.of(skillsXp.get(skill));
        }

        return Optional.empty();
    }

    public int getInventoryEmptySlots() {
        return 28 - inventory.size();
    }

    private Stream<ChargedItemBase> getInventoryAndEquipmentChargedItems() {
        return Arrays.stream(chargedItems).filter(ChargedItemBase::inInventoryOrEquipment);
    }

    public void onStatChanged(StatChanged eventOriginal) {
        CustomStatChanged event = new CustomStatChanged(eventOriginal, this);
        skillsXp.put(event.skill, event.xp);

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnStatChanged.trigger(event, chargedItem);
            listenerOnXpDrop.trigger(event, chargedItem);
        });
    }

    public void onItemContainerChanged(CustomItemContainerChanged event) {
        if (
            event.getContainerId() == InventoryID.BANK ||
            event.getContainerId() == InventoryID.INV ||
            event.getContainerId() == InventoryID.WORN
        ) {
            // Update inventory, save previous items.
            if (event.getContainerId() == InventoryID.INV) {
                previousInventory = inventory;
                inventory = event;
            } else if (event.getContainerId() == InventoryID.WORN) {
                equipment = event;
            } else if (event.getContainerId() == InventoryID.BANK) {
                previousBank = bank;
                bank = event;

                StringBuilder storageStringBuilder = new StringBuilder();
                for (StorageItem item : event.getItems()) {
                    storageStringBuilder.append(item.itemId).append(",");
                }
                String storageString = storageStringBuilder.toString().replaceAll(",$", "");
                configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.storage_bank, storageString);
            }

            updateChargedItemsPrimaryId(event.getContainerId() == InventoryID.BANK);
        }

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> listenerOnItemContainerChanged.trigger(event, chargedItem));
    }

    private void updateChargedItemsPrimaryId(boolean checkBank) {
        for (ChargedItemBase chargedItem : chargedItems) {
            Optional<Integer> bankItemId = Optional.empty();
            boolean bankItemDynamic = false;

            Optional<Integer> inventoryItemId = Optional.empty();
            boolean inventoryItemDynamic = false;

            Optional<Integer> equipmentItemId = Optional.empty();
            boolean equipmentItemDynamic = false;

            // Bank has the least priority.
            if (checkBank) {
                for (StorageItem item : bank.getItems()) {
                    for (TriggerItem triggerItem : chargedItem.items) {
                        if (item.itemId == triggerItem.itemId) {
                            if (!bankItemId.isPresent() || triggerItem.fixedCharges.isPresent() && !bankItemDynamic || !triggerItem.fixedCharges.isPresent()) {
                                bankItemId = Optional.of(item.itemId);

                                if (!triggerItem.fixedCharges.isPresent()) {
                                    bankItemDynamic = true;
                                }
                            }
                        }
                    }
                }
            }

            // Inventory is more important than bank.
            for (StorageItem item : inventory.getItems()) {
                for (TriggerItem triggerItem : chargedItem.items) {
                    if (item.itemId == triggerItem.itemId) {
                        if (!inventoryItemId.isPresent() || triggerItem.fixedCharges.isPresent() && !inventoryItemDynamic || !triggerItem.fixedCharges.isPresent()) {
                            inventoryItemId = Optional.of(item.itemId);

                            if (!triggerItem.fixedCharges.isPresent()) {
                                inventoryItemDynamic = true;
                            }
                        }
                    }
                }
            }

            // Equipment has most priority.
            for (StorageItem item : equipment.getItems()) {
                for (TriggerItem triggerItem : chargedItem.items) {
                    if (item.itemId == triggerItem.itemId) {
                        if (!equipmentItemId.isPresent() || triggerItem.fixedCharges.isPresent() && !equipmentItemDynamic || !triggerItem.fixedCharges.isPresent()) {
                            equipmentItemId = Optional.of(item.itemId);

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

    public void onMenuOptionClicked(CustomMenuOptionClicked event) {
        if (
            // Menu option not found.
            event.option.isEmpty() ||
            // Not menu.
            event.target.isEmpty() && (
                !event.option.contains("Buy-") &&
                !event.option.equals("Continue") &&
                !event.option.equals("Yes") &&
                event.eventId != 65540 && // Special event check for log basket
                event.eventId != 65538 && // Special event check for forestry basket
                event.eventId != 131074 && // Special event check for forestry basket
                event.eventId != 131076 // Special event check for forestry basket
            ) ||
            // Cancel option.
            event.actionName.equals("CANCEL") ||
            // RuneLite specific action.
            event.actionName.equals("RUNELITE") ||
            // Use
            event.option.equals("Use") && !event.target.contains(" -> ")
        ) return;

        checkBankWithdraw(event);

        // Gametick changed, clear previous menu entries since they are no longer valid.
        if (gametick >= gametickBefore + 2) {
            gametickBefore = gametick;
            menuOptionsClicked.clear();
        }

        // Save menu option and target for other triggers to use.
        menuOptionsClicked.add(event);

        if (
            previousMenuOptionClicked.isPresent() &&
            previousMenuOptionClicked.get().option.equals("Use") &&
            event.option.equals("Use") &&
            event.target.contains("->")
        ) {
            event.assignUsedItemId(previousMenuOptionClicked.get().itemId);
        }

        this.previousMenuOptionClicked = Optional.of(event);

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnMenuOptionClicked.trigger(event, chargedItem);
            listenerOnItemUsed.trigger(event, chargedItem);
        });
    }

    public void onWidgetMenuOptionClicked(CustomWidgetMenuOptionClicked customWidgetMenuOptionClicked) {
        // Gametick changed, clear previous widget menu entries since they are no longer valid.
        if (gametick >= gametickBefore + 2) {
            gametickBefore = gametick;
            widgetMenuActionsClicked.clear();
        }

        widgetMenuActionsClicked.add(customWidgetMenuOptionClicked);
    }

    private void checkBankWithdraw(CustomMenuOptionClicked customMenuOptionClicked) {
        Matcher matcher = withdrawPattern.matcher(customMenuOptionClicked.option);
        if (!matcher.find()) return;

        String amountString = matcher.group("amount");
        if (amountString.equals("X")) return;

        int amount =
            amountString.equals("All") ? bank.count(customMenuOptionClicked.itemId) :
            amountString.equals("All-but-1") ? bank.count(customMenuOptionClicked.itemId) - 1 :
            Integer.parseInt(amountString);

        ItemComposition itemComposition = itemManager.getItemComposition(customMenuOptionClicked.itemId);

        // Copy of current inventory.
        CustomItemContainerChanged itemContainerChanged = new CustomItemContainerChanged(inventory);

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
            Runnable consumer = nextTickQueue.poll();
            consumer.run();
        }
    }

    public void onGameTick(GameTick event) {
        runNextGameTickQueue();
        gametick++;

        // Keep only last menu entry.
        if (menuOptionsClicked.size() > 1) {
            CustomMenuOptionClicked lastMenuEntry = menuOptionsClicked.get(menuOptionsClicked.size() - 1);
            menuOptionsClicked.clear();
            menuOptionsClicked.add(lastMenuEntry);
        }

        if (isInCombat()) {
            onCombat();
        }

        inCombatTicksRemainingDamageDoneToOthers = Math.max(0, inCombatTicksRemainingDamageDoneToOthers - 1);
        inCombatTicksRemainingDamageDoneToMe = Math.max(0, inCombatTicksRemainingDamageDoneToMe - 1);

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnGameTick.trigger(event, chargedItem);
        });
    }

    public boolean inMenuTargets(int ...itemIds) {
        for (int itemId : itemIds) {
            for (CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
                if (
                    customMenuOptionClicked.itemId == itemId ||
                    // Additional target name check, because itemId can be -1 when the clicked item was not in inventory.
                    // Target can be also in format of item -> something else, which is why we are checking for partial match
                    customMenuOptionClicked.target.contains(itemManager.getItemComposition(itemId).getName())
                ) return true;
            }
        }

        return false;
    }

    public boolean inMenuTargets(String ...targets) {
        for (String target : targets) {
            for (CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
                boolean found = Pattern.compile(target).matcher(customMenuOptionClicked.target).find();
                if (found) return true;
            }
        }

        return false;
    }

    public boolean notInMenuTargets(String ...targets) {
        return !inMenuTargets(targets);
    }

    public boolean notInMenuTargets(StorageItem... storageItems) {
        int[] storeableItemIds = new int[storageItems.length];

        for (int i = 0; i < storageItems.length; i ++) {
            storeableItemIds[i] = storageItems[i].itemId;
        }

        return notInMenuTargets(storeableItemIds);
    }

    public boolean notInMenuTargets(int ...itemIds) {
        return !inMenuTargets(itemIds);
    }

    public boolean inMenuOptions(String ...options) {
        for (CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
            for (String option : options) {
                if (customMenuOptionClicked.option.equals(option)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean notInMenuOptions(String ...options) {
        return !inMenuOptions(options);
    }

    public boolean inMenuOptionIds(int ...menuOptionIds) {
        for (CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
            for (int menuOptionId : menuOptionIds) {
                if (customMenuOptionClicked.eventId == menuOptionId) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean notInMenuOptionIds(int ...menuOptionsIds) {
        return !inMenuOptionIds(menuOptionsIds);
    }

    public boolean notInWidgetMenuActions(WidgetMenuAction widgetMenuAction) {
        for (CustomWidgetMenuOptionClicked widgetMenuOptionClicked : widgetMenuActionsClicked) {
            if (
                widgetMenuOptionClicked.selectedOption.equals(widgetMenuAction.selectedOption) &&
                widgetMenuOptionClicked.options.size() > widgetMenuAction.childIndex &&
                widgetMenuOptionClicked.options.get(widgetMenuAction.childIndex).equals(widgetMenuAction.childString)
            ) {
                return false;
            }
        }

        return true;
    }

    public boolean inMenuImpostors(int ...impostorIds) {
        for (CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
            for (int impostorId : impostorIds) {
                if (customMenuOptionClicked.impostorId == impostorId) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean notInMenuImpostors(int ...impostorIds) {
        return !inMenuImpostors(impostorIds);
    }

    public int getInventoryItemQuantity(int itemId) {
        int quantity = 0;

        for (StorageItem storageItem : inventory.getItems()) {
            if (storageItem.itemId == itemId) {
                quantity += storageItem.getQuantity();
            }
        }

        return quantity;
    }

    public int getEquipmentItemQuantity(int itemId) {
        int quantity = 0;

        for (StorageItem item : equipment.getItems()) {
            if (item.itemId == itemId) {
                quantity += item.getQuantity();
            }
        }

        return quantity;
    }

    public int getPreviousInventoryItemQuantity(int itemId) {
        int quantity = 0;

        for (StorageItem storageItem : previousInventory.getItems()) {
            if (storageItem.itemId == itemId) {
                quantity += storageItem.getQuantity();
            }
        }

        return quantity;
    }

    public boolean inventoryContainsItem(int itemId) {
        for (StorageItem storageItem : inventory.getItems()) {
            if (storageItem.itemId == itemId) {
                return true;
            }
        }

        return false;
    }

    public boolean equipmentContainsItem(int ...itemIds) {
        for (StorageItem equipmentItem : equipment.getItems()) {
            for (int itemId : itemIds) {
                if (equipmentItem.itemId == itemId) {
                    return true;
                }
            }
        }

        return false;
    }

    private List<StorageItem> getAllItems() {
        List<StorageItem> allItems = new ArrayList<>();
        allItems.addAll(inventory.getItems());
        allItems.addAll(equipment.getItems());

        String[] storageBank = provider.config.getStorageBank().split(",");
        for (String bankItemId : storageBank) {
            allItems.add(new StorageItem(Integer.parseInt(bankItemId)));
        }
        return allItems;
    }

    public boolean itemInPossession(int itemId) {
        for (StorageItem item : getAllItems()) {
            if (item.itemId == itemId) {
                return true;
            }
        }

        return false;
    }

    public StorageItems getInventoryItemsDifference() {
        StorageItems itemsDifference = new StorageItems();

        Map<Integer, Integer> quantitiesNew = new HashMap<>();
        Map<Integer, Integer> quantitiesBefore = new HashMap<>();

        for (StorageItem itemNew : inventory.getItems()) {
            if (quantitiesNew.containsKey(itemNew.itemId)) continue;
            quantitiesNew.put(itemNew.itemId, inventory.count(itemNew.itemId));
        }

        for (StorageItem itemOld : previousInventory.getItems()) {
            if (quantitiesBefore.containsKey(itemOld.itemId)) {
                quantitiesBefore.put(itemOld.itemId, quantitiesBefore.get(itemOld.itemId) + itemOld.getQuantity());
            } else {
                quantitiesBefore.put(itemOld.itemId, itemOld.getQuantity());
            }
        }

        for (int itemId : quantitiesNew.keySet()) {
            int quantity = quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0);
            if (quantity != 0) {
                itemsDifference.put(new StorageItem(itemId, quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0)));
            }
        }

        for (int itemId : quantitiesBefore.keySet()) {
            if (!quantitiesNew.containsKey(itemId)) {
                itemsDifference.put(new StorageItem(itemId, -quantitiesBefore.get(itemId)));
            }
        }

        return itemsDifference;
    }

    public StorageItems getBankItemsDifference() {
        StorageItems itemsDifference = new StorageItems();

        Map<Integer, Integer> quantitiesNew = new HashMap<>();
        Map<Integer, Integer> quantitiesBefore = new HashMap<>();

        for (StorageItem itemNew : bank.getItems()) {
            if (quantitiesNew.containsKey(itemNew.itemId)) continue;
            quantitiesNew.put(itemNew.itemId, bank.count(itemNew.itemId));
        }

        for (StorageItem itemOld : previousBank.getItems()) {
            if (quantitiesBefore.containsKey(itemOld.itemId)) {
                quantitiesBefore.put(itemOld.itemId, quantitiesBefore.get(itemOld.itemId) + itemOld.getQuantity());
            } else {
                quantitiesBefore.put(itemOld.itemId, itemOld.getQuantity());
            }
        }

        for (int itemId : quantitiesNew.keySet()) {
            int quantity = quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0);
            if (quantity != 0) {
                itemsDifference.put(new StorageItem(itemId, quantitiesNew.get(itemId) - quantitiesBefore.getOrDefault(itemId, 0)));
            }
        }

        for (int itemId : quantitiesBefore.keySet()) {
            if (!quantitiesNew.containsKey(itemId)) {
                itemsDifference.put(new StorageItem(itemId, -quantitiesBefore.get(itemId)));
            }
        }

        return itemsDifference;
    }

    public void addConsumerToNextTickQueue(Runnable consumer) {
        nextTickQueue.add(consumer);
    }

    public void onChatMessage(CustomChatMessage event) {
        switch (event.type) {
            case GAMEMESSAGE:
            case DIALOG:
            case SPAM:
            case MESBOX:
                break;
            default:
                return;
        }

        int tick = client.getTickCount();
        if (tick != lastChatMessagesTick) {
            lastChatMessages = new ArrayList<>();
            lastChatMessagesTick = tick;
        }

        lastChatMessages.add(event.message);

        if (event.message.contains("The banker charges")) {
            Arrays.stream(chargedItems).forEach(chargedItem -> listenerOnChatMessage.trigger(event, chargedItem));
        } else {
            getInventoryAndEquipmentChargedItems().forEach(chargedItem -> listenerOnChatMessage.trigger(event, chargedItem));
        }
    }

    public void onHitSplatApplied(HitsplatApplied eventOriginal) {
        CustomHitsplatApplied event = new CustomHitsplatApplied(eventOriginal, client);

        if (event.byMe) {
            inCombatTicksRemainingDamageDoneToOthers = HIGHEST_MONSTER_ATTACK_SPEED;
        }

        if (event.toMe) {
            inCombatTicksRemainingDamageDoneToMe = HIGHEST_MONSTER_ATTACK_SPEED;
        }

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnHitsplatApplied.trigger(event, chargedItem);
        });
    }

    public void onGraphicChanged(CustomGraphicChanged graphicChanged) {
        if (graphicChanged.hasGraphicId(GraphicId.SPLASH)) {
            inCombatTicksRemainingDamageDoneToOthers = HIGHEST_MONSTER_ATTACK_SPEED;
        }

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnGraphicChanged.trigger(graphicChanged, chargedItem);
        });

        if (provider.config.showDebugIds()) {
            graphicChanged.showDebugIds(provider.chatMessageManager);
        }
    }

    public boolean isInCombat() {
        return inCombatTicksRemainingDamageDoneToOthers > 0;
    }

    public boolean isLockedInCombat() {
        return inCombatTicksRemainingDamageDoneToMe > 0;
    }

    public void onWidgetLoaded(WidgetLoaded event) {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnWidgetLoaded.trigger(event, chargedItem);
        });
    }

    public void onVarbitChanged(VarbitChanged event) {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnVarbitChanged.trigger(event, chargedItem);
            listenerOnVarbitsMapChanged.trigger(event, chargedItem);
        });

        // If server minutes are 0, it's a new day!
        if (event.getVarbitId() == VarbitID.CLOCK && client.getGameState() == GameState.LOGGED_IN && event.getValue() == 0) {
            checkForChargesReset();
        }
    }

    public void onAnimationChanged(AnimationChanged eventOriginal) {
        if (eventOriginal.getActor().getAnimation() == -1) return;
        CustomAnimationChanged event = new CustomAnimationChanged(eventOriginal);

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnAnimationChanged.trigger(event, chargedItem);
        });

        if (provider.config.showDebugIds()) {
            event.showDebugIds(provider.chatMessageManager);
        }
    }

    public void onMenuEntryAdded(MenuEntryAdded event) {
        if (event.getOption().equals("Cancel")) return;

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnMenuEntryAdded.trigger(event, chargedItem);
        });
    }

    public void onItemDespawned(ItemDespawned event) {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnItemPickup.trigger(event, chargedItem);
        });
    }

    public void onResetDaily(String date) {
        configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.date, date);

        if (provider.config.showDailyReset()) {
            provider.chatMessageManager.queue(QueuedMessage.builder()
                .type(ChatMessageType.CONSOLE)
                .runeLiteFormattedMessage("<colHIGHLIGHT>Daily item charges have been reset.")
                .build()
            );
        }

        Arrays.stream(chargedItems).forEach(chargedItem -> {
            listenerOnResetDaily.trigger(chargedItem);
        });
    }

    public void onUserAction() {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnUserAction.trigger(chargedItem);
        });
    }

    private List<Integer> scriptIdsToIgnore = Arrays.asList(
        44, 85, 100, 839, 900, 1004, 1005, 1045, 1445, 1972, 2100, 2101,
        2165, 2250, 2372, 2476, 2512, 2513, 3174, 3277, 3350, 3351, 4024,
        4029, 4482, 4517, 4518, 4666, 4667, 4668, 4669, 4671, 4672, 4716,
        4721, 4729, 4730, 4731, 4734, 5343, 5923, 5933, 5935, 5936, 5939,
        5943, 5944, 6015, 6016, 6063, 6152, 9625, 664
    );

    public void onScriptPreFired(ScriptPreFired eventOriginal) {
        if (scriptIdsToIgnore.contains(eventOriginal.getScriptId())) return;
        if (eventOriginal.getScriptEvent() == null) return;

        CustomScriptPreFired event = new CustomScriptPreFired(eventOriginal);
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnScriptPreFired.trigger(event, chargedItem);
        });
    }

    public void onCombat() {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnCombat.trigger(chargedItem);
        });
    }

    public void onMenuOpened(MenuOpened event) {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnMenuOpened.trigger(event, chargedItem);
        });
    }

    public void onGameStateChanged(GameStateChanged event) {
        if (event.getGameState() == GameState.LOGGING_IN) {
            checkForChargesReset();
        }

        if (event.getGameState() != GameState.LOGGED_IN) return;

        // Update config version to latest
        if (!provider.config.getVersion().equals(TicTac7xChargesImprovedPlugin.pluginVersion)) {
            configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.version, TicTac7xChargesImprovedPlugin.pluginVersion);

            // Send message about plugin updates for once.
            if (provider.config.showUpdatesMessage()) {
                provider.chatMessageManager.queue(QueuedMessage.builder()
                    .type(ChatMessageType.CONSOLE)
                    .runeLiteFormattedMessage(TicTac7xChargesImprovedPlugin.pluginMessage)
                    .build()
                );
            }
        }
    }

    private void checkForChargesReset() {
        String date = LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);

        if (!date.equals(provider.config.getResetDate())) {
            onResetDaily(date);
        }
    }

    public void onConfigChanged(ConfigChanged event) {
        if (event.getGroup().equals(TicTac7xChargesImprovedConfig.group) && event.getKey().equals(TicTac7xChargesImprovedConfig.debug_ids)) {
            provider.chatMessageManager.queue(QueuedMessage.builder()
                .type(ChatMessageType.CONSOLE)
                .runeLiteFormattedMessage(provider.config.showDebugIds()
                    ? "<colHIGHLIGHT>[Item Charges Improved] Debug information is now enabled."
                    : "<colHIGHLIGHT>[Item Charges Improved] Debug information is now disabled."
                ).build()
            );
        }
    }

    public boolean hasChatMessage(String message) {
        for (String lastChatMessage : lastChatMessages) {
            if (lastChatMessage.equals(message)) {
                return true;
            }
        }

        return false;
    }
}
