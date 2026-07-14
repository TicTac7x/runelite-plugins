package tictac7x.charges.items.capes;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.utils.*;

import java.util.*;

public class C_ForestryBasket extends ChargedItemWithStorage {
    private String menuOptionEmptyLogsToBank = "Empty-logs-to-bank";

    private C_ForestryKit forestryKit;
    private C_LogBasket logBasket;

    public C_ForestryBasket(Provider provider) {
        super(TicTac7xChargesImprovedConfig.forestry_basket, ItemID.FORESTRY_BASKET_CLOSED, provider);

        forestryKit = new C_ForestryKit(TicTac7xChargesImprovedConfig.forestry_basket, ItemID.FORESTRY_BASKET_CLOSED, ItemID.FORESTRY_BASKET_OPEN, storage, provider);
        logBasket = new C_LogBasket(TicTac7xChargesImprovedConfig.forestry_basket, ItemID.FORESTRY_BASKET_CLOSED, ItemID.FORESTRY_BASKET_OPEN, storage, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FORESTRY_BASKET_CLOSED),
            new TriggerItem(ItemID.FORESTRY_BASKET_OPEN),
        };

        this.triggers.addAll(List.of(
            // Replace "Use" with proper "Empty/Fill".
            new OnMenuEntryAdded("Use").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Empty container applies only to logs.
            new OnChatMessage("You empty all of your containers into the bank.").consumer(() -> logBasket.emptyStorage())
        ));

        this.triggers.addAll(forestryKit.triggers);
        this.triggers.addAll(logBasket.triggers);

        this.triggers.addAll(List.of(
            // Fill kit from inventory.
            new OnItemContainerChanged(InventoryID.INV).onMenuOption("Forestry kit").onItemClick().fillStorageFromInventory(),

            // Fill logs from inventory.
            new OnItemContainerChanged(InventoryID.INV).onMenuOption("Log basket").onItemClick().fillStorageFromInventory(),

            // Empty logs to inventory.
            new OnItemContainerChanged(InventoryID.INV).onMenuOption("Empty basket").onItemClick().emptyStorageToInventory(),

            // Empty logs from check dialog.
            new OnItemContainerChanged(InventoryID.INV).onWidgetMenuAction(new WidgetMenuAction("Yes", 0, "Empty the logs from the forestry basket into your inventory?")).emptyStorageToInventory(),

            // Empty logs to bank.
            new OnItemContainerChanged(InventoryID.BANK).emptyStorageToBank().onMenuOption(menuOptionEmptyLogsToBank)
        ));
    }

    private String getMenuOptionForUse() {
        if (logBasket.hasLogsInStorage()) {
            return menuOptionEmptyLogsToBank;
        } else if (forestryKit.hasLeavesInForestryKit()) {
            return forestryKit.menuOptionEmptyLeavesToBank;
        } else {
            return forestryKit.menuOptionFillLeavesFromBank;
        }
    }

    @Override
    public String getTooltip() {
        return super.getTooltip();
    }
}
