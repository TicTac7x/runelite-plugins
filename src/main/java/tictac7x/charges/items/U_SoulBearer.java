package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class U_SoulBearer extends ChargedItem {
    public U_SoulBearer(
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
        super(TicTac7xChargesImprovedConfig.soul_bearer, ItemID.SOUL_BEARER, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SOUL_BEARER),
            new TriggerItem(ItemID.DAMAGED_SOUL_BEARER).fixedCharges(0),
        };

        this.triggers = new TriggerBase[] {
            new OnChatMessage("You remove the runes from the soul bearer.").setFixedCharges(0),
            new OnChatMessage("(The|Your) soul bearer( now)? has one charge.").setFixedCharges(1),
            new OnChatMessage("Your soul bearer carries the ensouled heads to your bank. It has run out of charges.").notification().setFixedCharges(0),
            new OnChatMessage("The soul bearer has (?<charges>.+) charges?.").setDynamicallyCharges(),
            new OnChatMessage("You add .+ charges? to your soul bearer. It now has (?<charges>.+) charges?.").setDynamicallyCharges(),
            new OnChatMessage("Your soul bearer carries the ensouled heads to your bank. It has (?<charges>.+) charges? left.").setDynamicallyCharges()
        };
    }
}
