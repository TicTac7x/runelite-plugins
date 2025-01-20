package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnXpDrop;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class W_BryophytasStaff extends ChargedItem {
    public W_BryophytasStaff(
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
        super(TicTac7xChargesImprovedConfig.bryophytas_staff, ItemID.BRYOPHYTAS_STAFF, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BRYOPHYTAS_STAFF_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.BRYOPHYTAS_STAFF)
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("The nature staff has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Save a nature rune.
            new OnChatMessage("Your staff saved you a nature rune.").increaseCharges(1),

            // Charge.
            new OnChatMessage("Your Bryophyta's staff now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Regular spellbook.
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Bones to Bananas",
                "Low Level Alchemy",
                "Superheat Item",
                "High Level Alchemy"
            ).decreaseCharges(1),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Make sets").onMenuTarget(
                "Emerald bolts (e)"
            ).decreaseCharges(1),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Bind",
                "Bones to Peaches"
            ).decreaseCharges(2),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Snare"
            ).decreaseCharges(3),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Entangle"
            ).decreaseCharges(4),


            // Arcuus spellbook.
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Dark Lure",
                "Harmony Island Teleport"
            ).decreaseCharges(1),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Degrime",
                "Ward of Arceuus"
            ).decreaseCharges(2),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Reanimate").onMenuTarget(
                "Basic Reanimation"
            ).decreaseCharges(2),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Reanimate").onMenuTarget(
                "Adept Reanimation",
                "Expert Reanimation"
            ).decreaseCharges(3),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Reanimate").onMenuTarget(
                "Master Reanimation"
            ).decreaseCharges(4),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Resurrect").onMenuTarget(
                "Resurrect Crops"
            ).decreaseCharges(12),

            // Lunar spellbook.
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Tan Leather",
                "Plank Make",
                "Energy Transfer"
            ).decreaseCharges(1),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Spin Flax",
                "Fertile Soil"
            ).decreaseCharges(2),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Geomancy"
            ).decreaseCharges(3),
        };
    }
}
