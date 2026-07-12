package tictac7x.charges.store;

import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.events.*;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.listeners.*;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.storage.StorageItems;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.GraphicId;
import tictac7x.charges.store.ids.ItemContainerId;
import tictac7x.charges.store.ids.VarbitId;
import tictac7x.charges.store.utils.WidgetMenuAction;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Store {
    private final Client client;
    private final ItemManager itemManager;
    private final ConfigManager configManager;
    private Provider provider;
    private final ZoneId timezone = ZoneId.of("Europe/London");

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
    public Optional<CustomMenuOptionClicked> previousMenuOptionClicked = Optional.empty();
    public final List<CustomMenuOptionClicked> menuOptionsClicked = new ArrayList<>();
    private final List<CustomWidgetMenuOptionClicked> widgetMenuActionsClicked = new ArrayList<>();
    private final Map<Skill, Integer> skillsXp = new HashMap<>();

    final Pattern withdrawPattern = Pattern.compile("Withdraw-(?<amount>.+)");

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
    private ListenerOnGameTick listenerOnGameTick;

    public Store(final Client client, final ItemManager itemManager, final ConfigManager configManager) {
        this.client = client;
        this.itemManager = itemManager;
        this.configManager = configManager;
    }

    public Store addProvider(final Provider provider) {
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
        listenerOnGameTick = new ListenerOnGameTick(provider);

        return this;
    }

    public List<String> getLastChatMessages() {
        return lastChatMessages;
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
    
    private Stream<ChargedItemBase> getInventoryAndEquipmentChargedItems() {
        return Arrays.stream(chargedItems).filter(ChargedItemBase::inInventoryOrEquipment);
    }

    public void onStatChanged(final StatChanged eventOriginal) {
        final CustomStatChanged event = new CustomStatChanged(eventOriginal, this);
        skillsXp.put(event.skill, event.xp);

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnStatChanged.trigger(event, chargedItem);
            listenerOnXpDrop.trigger(event, chargedItem);
        });
    }

    private void onItemContainerChanged(final CustomItemContainerChanged event) {
        runNextGameTickQueue();

        if (
            event.getContainerId() == ItemContainerId.BANK ||
            event.getContainerId() == ItemContainerId.INVENTORY ||
            event.getContainerId() == ItemContainerId.EQUIPMENT
        ) {
            // Update inventory, save previous items.
            if (event.getContainerId() == ItemContainerId.INVENTORY) {
                previousInventory = inventory;
                inventory = event;
            } else if (event.getContainerId() == ItemContainerId.EQUIPMENT) {
                equipment = event;
            } else if (event.getContainerId() == ItemContainerId.BANK) {
                previousBank = bank;
                bank = event;

                final StringBuilder storageStringBuilder = new StringBuilder();
                for (final StorageItem item : event.getItems()) {
                    storageStringBuilder.append(item.itemId).append(",");
                }
                final String storageString = storageStringBuilder.toString().replaceAll(",$", "");
                configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.storage_bank, storageString);
            }

            updateChargedItemsPrimaryId(event.getContainerId() == ItemContainerId.BANK);
        }

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> listenerOnItemContainerChanged.trigger(event, chargedItem));
    }

    public void onItemContainerChanged(final ItemContainerChanged eventOriginal) {
        final CustomItemContainerChanged event = new CustomItemContainerChanged(eventOriginal, itemManager);
        onItemContainerChanged(event);
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
            for (final StorageItem item : inventory.getItems()) {
                for (final TriggerItem triggerItem : chargedItem.items) {
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
            for (final StorageItem item : equipment.getItems()) {
                for (final TriggerItem triggerItem : chargedItem.items) {
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

    public void onMenuOptionClicked(final CustomMenuOptionClicked customMenuOptionClicked) {
        if (
            // Menu option not found.
            customMenuOptionClicked.option.isEmpty() ||
            // Not menu.
            customMenuOptionClicked.target.isEmpty() && (
                !customMenuOptionClicked.option.contains("Buy-") &&
                !customMenuOptionClicked.option.equals("Continue") &&
                !customMenuOptionClicked.option.equals("Yes") &&
                customMenuOptionClicked.eventId != 65540 && // Special event check for log basket
                customMenuOptionClicked.eventId != 65538 && // Special event check for forestry basket
                customMenuOptionClicked.eventId != 131074 && // Special event check for forestry basket
                customMenuOptionClicked.eventId != 131076 // Special event check for forestry basket
            ) ||
            // Cancel option.
            customMenuOptionClicked.actionName.equals("CANCEL") ||
            // RuneLite specific action.
            customMenuOptionClicked.actionName.equals("RUNELITE")
        ) return;

        checkBankWithdraw(customMenuOptionClicked);

        // Gametick changed, clear previous menu entries since they are no longer valid.
        if (gametick >= gametick_before + 2) {
            gametick = 0; gametick_before = 0;
            menuOptionsClicked.clear();
        }

        // Save menu option and target for other triggers to use.
        menuOptionsClicked.add(customMenuOptionClicked);

        if (
            previousMenuOptionClicked.isPresent() &&
            previousMenuOptionClicked.get().option.equals("Use") &&
            customMenuOptionClicked.option.equals("Use") &&
            customMenuOptionClicked.target.contains("->")
        ) {
            customMenuOptionClicked.assignUsedItemId(previousMenuOptionClicked.get().itemId);
        }

        this.previousMenuOptionClicked = Optional.of(customMenuOptionClicked);

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnMenuOptionClicked.trigger(customMenuOptionClicked, chargedItem);
            listenerOnItemUsed.trigger(customMenuOptionClicked, chargedItem);
        });
    }

    public void onWidgetMenuOptionClicked(final CustomWidgetMenuOptionClicked customWidgetMenuOptionClicked) {
        // Gametick changed, clear previous widget menu entries since they are no longer valid.
        if (gametick >= gametick_before + 2) {
            gametick = 0; gametick_before = 0;
            widgetMenuActionsClicked.clear();
        }

        widgetMenuActionsClicked.add(customWidgetMenuOptionClicked);
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
        gametick++;

        // Keep only last menu entry.
        if (menuOptionsClicked.size() > 1) {
            final CustomMenuOptionClicked lastMenuEntry = menuOptionsClicked.get(menuOptionsClicked.size() - 1);
            menuOptionsClicked.clear();
            menuOptionsClicked.add(lastMenuEntry);
        }

        if (isInCombat()) {
            onCombat();
        }

        inCombatTicksRemainingDamageDoneToOthers = Math.max(0, inCombatTicksRemainingDamageDoneToOthers - 1);
        inCombatTicksRemainingDamageDoneToMe = Math.max(0, inCombatTicksRemainingDamageDoneToMe - 1);
    }

    public boolean inMenuTargets(final int ...itemIds) {
        for (final int itemId : itemIds) {
            for (final CustomMenuOptionClicked customMenuOptionClicked : menuOptionsClicked) {
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
            storeableItemIds[i] = storageItems[i].itemId;
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

    public boolean notInWidgetMenuActions(final WidgetMenuAction widgetMenuAction) {
        for (final CustomWidgetMenuOptionClicked widgetMenuOptionClicked : widgetMenuActionsClicked) {
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
            if (storageItem.itemId == itemId) {
                quantity += storageItem.getQuantity();
            }
        }

        return quantity;
    }

    public int getEquipmentItemQuantity(final int itemId) {
        int quantity = 0;

        for (final StorageItem item : equipment.getItems()) {
            if (item.itemId == itemId) {
                quantity += item.getQuantity();
            }
        }

        return quantity;
    }

    public int getPreviousInventoryItemQuantity(final int itemId) {
        int quantity = 0;

        for (final StorageItem storageItem : previousInventory.getItems()) {
            if (storageItem.itemId == itemId) {
                quantity += storageItem.getQuantity();
            }
        }

        return quantity;
    }

    public boolean inventoryContainsItem(final int itemId) {
        for (final StorageItem storageItem : inventory.getItems()) {
            if (storageItem.itemId == itemId) {
                return true;
            }
        }

        return false;
    }

    public boolean equipmentContainsItem(final int ...itemIds) {
        for (final StorageItem equipmentItem : equipment.getItems()) {
            for (final int itemId : itemIds) {
                if (equipmentItem.itemId == itemId) {
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
            if (item.itemId == itemId) {
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
            if (quantitiesNew.containsKey(itemNew.itemId)) continue;
            quantitiesNew.put(itemNew.itemId, inventory.count(itemNew.itemId));
        }

        for (final StorageItem itemOld : previousInventory.getItems()) {
            if (quantitiesBefore.containsKey(itemOld.itemId)) {
                quantitiesBefore.put(itemOld.itemId, quantitiesBefore.get(itemOld.itemId) + itemOld.getQuantity());
            } else {
                quantitiesBefore.put(itemOld.itemId, itemOld.getQuantity());
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
            if (quantitiesNew.containsKey(itemNew.itemId)) continue;
            quantitiesNew.put(itemNew.itemId, bank.count(itemNew.itemId));
        }

        for (final StorageItem itemOld : previousBank.getItems()) {
            if (quantitiesBefore.containsKey(itemOld.itemId)) {
                quantitiesBefore.put(itemOld.itemId, quantitiesBefore.get(itemOld.itemId) + itemOld.getQuantity());
            } else {
                quantitiesBefore.put(itemOld.itemId, itemOld.getQuantity());
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

    public void onChatMessage(final ChatMessage eventOriginal) {
        final CustomChatMessage event = new CustomChatMessage(eventOriginal);

        switch (event.type) {
            case GAMEMESSAGE:
            case DIALOG:
            case SPAM:
            case MESBOX:
                break;
            default:
                return;
        }

        final int tick = client.getTickCount();
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

    public void onHitSplatApplied(final HitsplatApplied eventOriginal) {
        final CustomHitsplatApplied event = new CustomHitsplatApplied(eventOriginal, client);

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

    public void onGraphicChanged(final GraphicChanged event) {
        final CustomGraphicChanged graphicChanged = new CustomGraphicChanged(event);
        if (!graphicChanged.isLocalPlayer(client)) return;

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

    public void onWidgetLoaded(final WidgetLoaded event) {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnWidgetLoaded.trigger(event, chargedItem);
        });
    }

    public void onVarbitChanged(final VarbitChanged event) {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnVarbitChanged.trigger(event, chargedItem);
            listenerOnVarbitsMapChanged.trigger(event, chargedItem);
        });

        // If server minutes are 0, it's a new day!
        if (event.getVarbitId() == VarbitId.MINUTES && client.getGameState() == GameState.LOGGED_IN && event.getValue() == 0) {
            checkForChargesReset();
        }
    }

    public void onAnimationChanged(final AnimationChanged eventOriginal) {
        if (eventOriginal.getActor().getAnimation() == -1) return;
        final CustomAnimationChanged event = new CustomAnimationChanged(eventOriginal);

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnAnimationChanged.trigger(event, chargedItem);
        });

        if (provider.config.showDebugIds()) {
            event.showDebugIds(provider.chatMessageManager);
        }
    }

    public void onMenuEntryAdded(final MenuEntryAdded event) {
        if (event.getOption().equals("Cancel")) return;

        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnMenuEntryAdded.trigger(event, chargedItem);
        });
    }

    public void onItemDespawned(final ItemDespawned event) {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnItemPickup.trigger(event, chargedItem);
        });
    }

    public void onResetDaily(final String date) {
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

    private final List<Integer> scriptIdsToIgnore = Arrays.asList(
        44, 85, 100, 839, 900, 1004, 1005, 1045, 1445, 1972, 2100, 2101,
        2165, 2250, 2372, 2476, 2512, 2513, 3174, 3277, 3350, 3351, 4024,
        4029, 4482, 4517, 4518, 4666, 4667, 4668, 4669, 4671, 4672, 4716,
        4721, 4729, 4730, 4731, 4734, 5343, 5923, 5933, 5935, 5936, 5939,
        5943, 5944, 6015, 6016, 6063, 6152, 9625, 664
    );

    public void onScriptPreFired(final ScriptPreFired eventOriginal) {
        if (scriptIdsToIgnore.contains(eventOriginal.getScriptId())) return;
        if (eventOriginal.getScriptEvent() == null) return;

        final CustomScriptPreFired event = new CustomScriptPreFired(eventOriginal);
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnScriptPreFired.trigger(event, chargedItem);
        });
    }

    public void onCombat() {
        getInventoryAndEquipmentChargedItems().forEach(chargedItem -> {
            listenerOnCombat.trigger(chargedItem);
        });
    }

    public void onGameStateChanged(final GameStateChanged event) {
        if (event.getGameState() == GameState.LOGGING_IN) {
            checkForChargesReset();
        }

        if (event.getGameState() != GameState.LOGGED_IN) return;

        // Send message about plugin updates for once.
        if (!provider.config.getVersion().equals(TicTac7xChargesImprovedPlugin.pluginVersion)) {
            configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.version, TicTac7xChargesImprovedPlugin.pluginVersion);
            provider.chatMessageManager.queue(QueuedMessage.builder()
                .type(ChatMessageType.CONSOLE)
                .runeLiteFormattedMessage(TicTac7xChargesImprovedPlugin.pluginMessage)
                .build()
            );
        }
    }

    private void checkForChargesReset() {
        final String date = LocalDateTime.now(timezone).format(DateTimeFormatter.ISO_LOCAL_DATE);

        if (!date.equals(provider.config.getResetDate())) {
            onResetDaily(date);
        }
    }

    public void onConfigChanged(final ConfigChanged event) {
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

    public boolean hasChatMessage(final String message) {
        for (final String lastChatMessage : lastChatMessages) {
            if (lastChatMessage.equals(message)) {
                return true;
            }
        }

        return false;
    }
}
