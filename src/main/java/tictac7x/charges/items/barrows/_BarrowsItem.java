package tictac7x.charges.items.barrows;

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
import tictac7x.charges.customEvents.CustomMenuOptionClicked;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Store;

public class _BarrowsItem extends ChargedItem {
    private final int totalRepairCost;

    public _BarrowsItem(
        final String itemName,
        final int totalRepairCost,
        final int itemId,
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ChatMessageManager chatMessageManager,
        final Notifier notifier,
        final TicTac7xChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(
            TicTac7xChargesImprovedConfig.barrows_gear + "_" + itemName.toLowerCase().replaceAll("'", "").replaceAll(" ", "_"),
            itemId,
            client,
            clientThread,
            configManager,
            itemManager,
            infoBoxManager,
            chatMessageManager,
            notifier,
            config,
            store,
            gson
        );
        this.totalRepairCost = totalRepairCost;

        this.triggers = new TriggerBase[]{
            new OnChatMessage(itemName + ": (?<percentage>.+)% remaining until the next degradation.").matcherConsumer((m) -> {
                final int percentage = Integer.parseInt(m.group("percentage"));

                for (final CustomMenuOptionClicked menuOptionClicked : store.menuOptionsClicked) {
                    if (menuOptionClicked.target.contains("100")) {
                        setCharges(1000 - (percentage * 250 / 100));
                    } else if (menuOptionClicked.target.contains("75")) {
                        setCharges(750 - (percentage * 250 / 100));
                    } else if (menuOptionClicked.target.contains("50")) {
                        setCharges(500 - (percentage * 250 / 100));
                    } else if (menuOptionClicked.target.contains("25")) {
                        setCharges(250 - (percentage * 250 / 100));
                    }
                }
            }),

            new OnCombat(90).isEquipped().decreaseCharges(1),
        };
    }

    @Override
    protected String getChargesMinified(final int itemId) {
        switch (config.combatTimeDegradableStyle()) {
            case PERCENTAGE:
                return getChargesFromConfig() * 100 / 1000 + "%";
            case TIME:
                final double hours = (double) (getChargesFromConfig() * 90 * 600) / 1000 / 3600;
                return String.format("%.1fh", hours).replaceAll("\\.0", "");
            case CHARGES:
            default:
                return super.getChargesMinified(itemId);
        }
    }
}
