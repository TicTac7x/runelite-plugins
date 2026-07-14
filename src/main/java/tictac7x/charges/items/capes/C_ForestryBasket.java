package tictac7x.charges.items.capes;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.utils.*;

import java.util.*;

import static tictac7x.charges.store.ids.ItemContainerId.*;

public class C_ForestryBasket extends ChargedItemWithStorage {
    private String menuOptionEmptyLogsToBank = "Empty-logs-to-bank";

    private C_ForestryKit forestryKit;
    private C_LogBasket logBasket;

    public C_ForestryBasket(Provider provider) {
        super(TicTac7xChargesImprovedConfig.forestry_basket, ItemId.FORESTRY_BASKET, provider);

        forestryKit = new C_ForestryKit(TicTac7xChargesImprovedConfig.forestry_basket, ItemId.FORESTRY_BASKET, ItemId.FORESTRY_BASKET_OPEN, storage, provider);
        logBasket = new C_LogBasket(TicTac7xChargesImprovedConfig.forestry_basket, ItemId.FORESTRY_BASKET, ItemId.FORESTRY_BASKET_OPEN, storage, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FORESTRY_BASKET),
            new TriggerItem(ItemId.FORESTRY_BASKET_OPEN),
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
            new OnItemContainerChanged(INVENTORY).onMenuOption("Forestry kit").onItemClick().fillStorageFromInventory(),

            // Fill logs from inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Log basket").onItemClick().fillStorageFromInventory(),

            // Empty logs to inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Empty basket").onItemClick().emptyStorageToInventory(),

            // Empty logs from check dialog.
            new OnItemContainerChanged(INVENTORY).onWidgetMenuAction(new WidgetMenuAction("Yes", 0, "Empty the logs from the forestry basket into your inventory?")).emptyStorageToInventory(),

            // Empty logs to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption(menuOptionEmptyLogsToBank)
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
