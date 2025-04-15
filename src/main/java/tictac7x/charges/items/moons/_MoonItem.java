package tictac7x.charges.items.moons;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.store.Store;

public class _MoonItem extends ChargedItem {
    public _MoonItem(final String configKey, final int itemId, final Client client, final ClientThread clientThread, final ConfigManager configManager, final ItemManager itemManager, final InfoBoxManager infoBoxManager, final ChatMessageManager chatMessageManager, final Notifier notifier, final TicTac7xChargesImprovedConfig config, final Store store, final Gson gson) {
        super(TicTac7xChargesImprovedConfig.moons_gear + "_" + configKey, itemId, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
    }

    @Override
    protected String getChargesMinified(final int itemId) {
        switch (config.combatTimeDegradableStyle()) {
            case PERCENTAGE:
                return getChargesFromConfig() * 100 / 3000 + "%";
            case TIME:
                final double hours = (double) (getChargesFromConfig() * 90 * 600) / 1000 / 3600;
                return String.format("%.1fh", hours).replaceAll("\\.0", "");
            case CHARGES:
            default:
                return super.getChargesMinified(itemId);
        }
    }
}
