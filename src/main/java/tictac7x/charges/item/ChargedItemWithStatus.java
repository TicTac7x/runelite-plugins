package tictac7x.charges.item;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.store.ItemActivity;
import tictac7x.charges.store.Store;

import java.awt.Color;
import java.util.Optional;

public class ChargedItemWithStatus extends ChargedItem {

    public ChargedItemWithStatus(String configKey, int itemId, Client client, ClientThread clientThread, ConfigManager configManager, ItemManager itemManager, InfoBoxManager infoBoxManager, ChatMessageManager chatMessageManager, Notifier notifier, TicTac7xChargesImprovedConfig config, Store store, final Gson gson) {
        super(configKey, itemId, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
    }

    public boolean isDeactivated() {
        final Optional<String> status = Optional.ofNullable(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, getConfigStatusKey()));

        if (!status.isPresent()) {
            return false;
        }

        return status.get().equals(ItemActivity.DEACTIVATED.toString());
    }

    public boolean isActivated() {
        final Optional<String> status = Optional.ofNullable(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, getConfigStatusKey()));

        if (!status.isPresent()) {
            return false;
        }

        return status.get().equals(ItemActivity.ACTIVATED.toString());
    }

    public String getConfigStatusKey() {
        return configKey + "_status";
    }

    public void deactivate() {
        setActivity(ItemActivity.DEACTIVATED);
    }

    public void activate() {
        setActivity(ItemActivity.ACTIVATED);
    }

    private void setActivity(final ItemActivity status) {
        configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, getConfigStatusKey(), status);
    }

    @Override
    public Color getTextColor() {
        if (isActivated()) {
            return config.getColorActivated();
        }

        if (isDeactivated()) {
            return config.getColorEmpty();
        }

        return super.getTextColor();
    }
}
