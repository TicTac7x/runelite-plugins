package tictac7x.storage.storageManagers;

import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.client.events.ConfigChanged;
import tictac7x.storage.TicTac7xStorageConfig;
import tictac7x.storage.storage.BankStorage;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.utils.Provider;
import tictac7x.storage.utils.WidgetId;

import java.util.Optional;

import static tictac7x.storage.TicTac7xStoragePlugin.getWidget;

public class LunarLootChest extends Storage {
    private final Provider provider;
    private final BankStorage bank;
    private Optional<Widget> lunarChestWidget = Optional.empty();

    public LunarLootChest(final int itemContainerId, final BankStorage bank, final Provider provider) {
        super(itemContainerId);
        this.provider = provider;
        this.bank = bank;
    }

    public void onMenuOptionClicked(final MenuOptionClicked event) {
        if (!lunarChestWidget.isPresent() || !event.getMenuOption().equals("Bank-all")) return;
        bank.depositItems(getItems());
    }

    public void onWidgetLoaded(final WidgetLoaded event) {
        if (event.getGroupId() == WidgetId.LUNAR_LOOT_CHEST[0]) {
            lunarChestWidget = getWidget(WidgetId.LUNAR_LOOT_CHEST, provider.client);
            updateWidget();
        }
    }

    public void onWidgetClosed(final WidgetClosed event) {
        if (event.getGroupId() == WidgetId.LUNAR_LOOT_CHEST[0]) {
            lunarChestWidget = Optional.empty();
        }
    }

    public void onConfigChanged(final ConfigChanged event) {
        if (event.getKey().equals(TicTac7xStorageConfig.lunar_chest_hide_close)) {
            updateWidget();
        }
    }

    private void updateWidget() {
        if (lunarChestWidget.isPresent()) {
            final Optional<Widget> close = Optional.ofNullable(lunarChestWidget.get().getStaticChildren()[0].getChild(11));
            if (close.isPresent()) {
                close.get().setHidden(provider.config.hideLunarChestClose());
            }
        }
    }
}
