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
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.CombatStyle;
import tictac7x.charges.store.HitsplatTarget;
import tictac7x.charges.store.Store;

public class J_AmuletOfBloodFury extends ChargedItem {
    public J_AmuletOfBloodFury(
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
        super(TicTac7xChargesImprovedConfig.amulet_of_blood_fury, ItemID.AMULET_OF_BLOOD_FURY, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.AMULET_OF_BLOOD_FURY),
        };

        this.triggers = new TriggerBase[]{
            // Creation
            new OnChatMessage("You have successfully created an Amulet of blood fury.").setFixedCharges(10000),

            // Check.
            new OnChatMessage("Your Amulet of blood fury (will work|can perform) for (?<charges>.+) more hits?.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You have successfully added .+ hits? to your Amulet of blood fury. It will now work for (?<charges>.+) more hits?.").setDynamicallyCharges(),

            // Take damage.
            new OnHitsplatApplied(HitsplatTarget.ENEMY).combatStyle(CombatStyle.MELEE).isEquipped().decreaseCharges(1),
        };
    }
}
