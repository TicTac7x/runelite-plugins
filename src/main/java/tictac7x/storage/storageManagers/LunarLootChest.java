package tictac7x.storage.storageManagers;

import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import tictac7x.storage.storage.BankStorage;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.utils.WidgetId;

import java.util.Optional;

import static tictac7x.storage.TicTac7xStoragePlugin.getWidget;

public class LunarLootChest extends Storage {
    private final Client client;
    private final BankStorage bank;

    public LunarLootChest(final int itemContainerId, final BankStorage bank, final Client client) {
        super(itemContainerId);
        this.client = client;
        this.bank = bank;
    }

    public void onMenuOptionClicked(final MenuOptionClicked event) {
        if (!event.getMenuOption().equals("Bank-all")) return;

        final Optional<Widget> widget = getWidget(WidgetId.LUNAR_LOOT_CHEST, client);
        if (!widget.isPresent() || widget.get().isHidden()) return;

        bank.depositItems(getItems());
    }
}
