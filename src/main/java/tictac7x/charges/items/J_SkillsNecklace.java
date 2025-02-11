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
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;

public class J_SkillsNecklace extends ChargedItem {
    public J_SkillsNecklace(
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
        super(TicTac7xChargesImprovedConfig.skills_necklace, ItemID.SKILLS_NECKLACE, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SKILLS_NECKLACE).fixedCharges(0),
            new TriggerItem(ItemID.SKILLS_NECKLACE1).fixedCharges(1),
            new TriggerItem(ItemID.SKILLS_NECKLACE2).fixedCharges(2),
            new TriggerItem(ItemID.SKILLS_NECKLACE3).fixedCharges(3),
            new TriggerItem(ItemID.SKILLS_NECKLACE4).fixedCharges(4),
            new TriggerItem(ItemID.SKILLS_NECKLACE5).fixedCharges(5),
            new TriggerItem(ItemID.SKILLS_NECKLACE6).fixedCharges(6),
        };

        this.triggers = new TriggerBase[] {
            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),
        };
    }
}
