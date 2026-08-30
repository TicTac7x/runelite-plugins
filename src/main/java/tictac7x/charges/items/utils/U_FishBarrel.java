package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.*;

import java.util.*;
import java.util.List;
import java.util.regex.*;

public class U_FishBarrel extends ChargedItemWithStorageEmptyable {
    private Optional<StorageItem> lastCaughtFish = Optional.empty();

    public U_FishBarrel(Provider provider) {
        super(TicTac7xChargesImprovedConfig.fish_barrel, ItemID.FISH_BARREL_CLOSED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FISH_BARREL_CLOSED),
            new TriggerItem(ItemID.FISH_BARREL_OPEN),
            new TriggerItem(ItemID.FISH_SACK_BARREL_CLOSED),
            new TriggerItem(ItemID.FISH_SACK_BARREL_OPEN),
        };

        storage = storage.setMaximumTotalQuantity(28).storableItems(
            // Small net
            new StorableItem(ItemID.RAW_SHRIMP).checkName("Shrimp"),
            new StorableItem(ItemID.RAW_ANCHOVIES).checkName("Anchovies"),
            new StorableItem(ItemID.RAW_MONKFISH).checkName("Monkfish"),

            // Big net
            new StorableItem(ItemID.RAW_MACKEREL).checkName("Mackerel"),
            new StorableItem(ItemID.RAW_COD).checkName("Cod"),
            new StorableItem(ItemID.RAW_BASS).checkName("Bass"),

            // Barbarian
            new StorableItem(ItemID.BRUT_SPAWNING_TROUT).checkName("Leaping trout"),
            new StorableItem(ItemID.BRUT_SPAWNING_SALMON).checkName("Leaping salmon"),
            new StorableItem(ItemID.BRUT_STURGEON).checkName("Leaping sturgeon"),

            // Rod
            new StorableItem(ItemID.RAW_SARDINE).checkName("Sardine"),
            new StorableItem(ItemID.RAW_HERRING).checkName("Herring"),
            new StorableItem(ItemID.RAW_TROUT).checkName("Trout"),
            new StorableItem(ItemID.RAW_PIKE).checkName("Pike"),
            new StorableItem(ItemID.MORT_SLIMEY_EEL).checkName("Slimy swamp eel", "Slimy eel"),
            new StorableItem(ItemID.RAW_SALMON).checkName("Salmon"),
            new StorableItem(ItemID.HUNTING_RAW_FISH_SPECIAL).checkName("Rainbow fish"),
            new StorableItem(ItemID.RAW_CAVE_EEL).checkName("Cave eel"),
            new StorableItem(ItemID.RAW_LAVA_EEL).checkName("Lava eel"),
            new StorableItem(ItemID.INFERNAL_EEL).checkName("Infernal eel"),
            new StorableItem(ItemID.RAW_ANGLERFISH).checkName("Anglerfish"),
            new StorableItem(ItemID.SNAKEBOSS_EEL).checkName("Sacred eel"),

            // Harpoon
            new StorableItem(ItemID.RAW_TUNA).checkName("Tuna"),
            new StorableItem(ItemID.RAW_SWORDFISH).checkName("Swordfish"),
            new StorableItem(ItemID.RAW_SHARK).checkName("Shark"),

            // Aerial
            new StorableItem(ItemID.AERIAL_FISHING_BLUEGILL).checkName("Bluegill"),
            new StorableItem(ItemID.AERIAL_FISHING_COMMON_TENCH).checkName("Common tench"),
            new StorableItem(ItemID.AERIAL_FISHING_MOTTLED_EEL).checkName("Mottled eel"),
            new StorableItem(ItemID.AERIAL_FISHING_GREATER_SIREN).checkName("Greater siren"),

            // Cage
            new StorableItem(ItemID.RAW_LOBSTER).checkName("Lobster"),
            new StorableItem(ItemID.RAW_DARK_CRAB).checkName("Dark crab"),

            // Sailing
            new StorableItem(ItemID.RAW_SWORDTIP_SQUID).checkName("Swordtip squid"),
            new StorableItem(ItemID.RAW_JUMBO_SQUID).checkName("Jumbo squid"),
            new StorableItem(ItemID.RAW_GIANT_KRILL).checkName("Giant krill"),
            new StorableItem(ItemID.RAW_HADDOCK).checkName("Haddock"),
            new StorableItem(ItemID.RAW_YELLOWFIN).checkName("Yellowfin"),
            new StorableItem(ItemID.RAW_HALIBUT).checkName("Halibut"),
            new StorableItem(ItemID.RAW_BLUEFIN).checkName("Bluefin"),
            new StorableItem(ItemID.RAW_MARLIN).checkName("Marlin"),

            // Other
            new StorableItem(ItemID.TBWT_RAW_KARAMBWAN).checkName("Karambwan"),
            new StorableItem(ItemID.RAW_SEATURTLE).checkName("Sea turtle"),
            new StorableItem(ItemID.RAW_MANTARAY).checkName("Manta ray")
        );

        this.triggers.addAll(List.of(
            // Check or empty already empty.
            new OnChatMessage("(Your|The) barrel is empty.").onItemClick().emptyStorage(),

            // Catch fish.
            new OnChatMessage("You catch (a|an|some) (?<fish>.+).?").matcherConsumer(m -> {
                lastCaughtFish = getStorageItemFromName(m.group("fish"), 1);
                storage.add(lastCaughtFish);
            }).requiredItem(ItemID.FISH_BARREL_OPEN, ItemID.FISH_SACK_BARREL_OPEN),

            // Extra fish.
            new OnChatMessage(".* enabled you to catch an extra fish.").requiredItem(ItemID.FISH_BARREL_OPEN, ItemID.FISH_SACK_BARREL_OPEN).consumer(() -> {
                storage.add(lastCaughtFish);
            }),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Check.
            new OnChatMessage("The barrel contains:").stringConsumer(s -> {
                storage.clear();

                Pattern pattern = Pattern.compile("(?<quantity>\\d+).x.(?<fish>.*?)(,|$)");
                Matcher matcher = pattern.matcher(s);

                while (matcher.find()) {
                    storage.put(getStorageItemFromName(matcher.group("fish"), Integer.parseInt(matcher.group("quantity"))));
                }
            }).onItemClick(),

            // Fill from inventory.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onMenuOption("Fill"),

            // Pick up.
            new OnItemPickup(storage.getStorableItems()).isByOne().requiredItem(ItemID.FISH_BARREL_OPEN, ItemID.FISH_SACK_BARREL_OPEN).pickUpToStorage(),

            // Use fish on barrel.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Empty to deposit box.
            new OnChatMessage("You empty the barrel.").onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).emptyStorage(),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }
}
