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
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ReplaceTarget;
import tictac7x.charges.store.Store;

import static tictac7x.charges.store.ItemContainerId.BANK;
import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_HerbSack extends ChargedItemWithStorage {
    public U_HerbSack(
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
        super(TicTac7xChargesImprovedConfig.herb_sack, ItemID.HERB_SACK, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        storage = storage.setMaximumIndividualQuantity(30).storableItems(
            new StorableItem(ItemID.GRIMY_GUAM_LEAF).checkName("Guam leaf"),
            new StorableItem(ItemID.GRIMY_MARRENTILL).checkName("Marrentill"),
            new StorableItem(ItemID.GRIMY_TARROMIN).checkName("Tarromin"),
            new StorableItem(ItemID.GRIMY_HARRALANDER).checkName("Harralander"),
            new StorableItem(ItemID.GRIMY_RANARR_WEED).checkName("Ranarr weed"),
            new StorableItem(ItemID.GRIMY_TOADFLAX).checkName("Toadflax"),
            new StorableItem(ItemID.GRIMY_IRIT_LEAF).checkName("Irit leaf"),
            new StorableItem(ItemID.GRIMY_AVANTOE).checkName("Avantoe"),
            new StorableItem(ItemID.GRIMY_KWUARM).checkName("Kwuarm"),
            new StorableItem(ItemID.GRIMY_HUASCA).checkName("Huasca"),
            new StorableItem(ItemID.GRIMY_SNAPDRAGON).checkName("Snapdragon"),
            new StorableItem(ItemID.GRIMY_CADANTINE).checkName("Cadantine"),
            new StorableItem(ItemID.GRIMY_LANTADYME).checkName("Lantadyme"),
            new StorableItem(ItemID.GRIMY_DWARF_WEED).checkName("Dwarf weed"),
            new StorableItem(ItemID.GRIMY_TORSTOL).checkName("Torstol")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.HERB_SACK),
            new TriggerItem(ItemID.OPEN_HERB_SACK),
        };
        this.triggers = new TriggerBase[] {
            // Check or empty.
            new OnChatMessage("The herb sack is empty.").emptyStorage(),

            // Pickup.
            new OnChatMessage("You put the Grimy (?<herb>.+) herb into your herb sack.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("herb")), 1);
            }),

            // Check.
            new OnChatMessage("You look in your herb sack and see:").emptyStorage(),
            new OnChatMessage("(?<quantity>.+) x Grimy (?<herb>.+)").matcherConsumer(m -> {
                storage.put(getStorageItemFromName(m.group("herb")), Integer.parseInt(m.group("quantity")));
            }),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption("Empty"),

            // Empty from deposit box.
            new OnMenuOptionClicked("Empty").isWidgetVisible(192, 1).emptyStorage(),

            // Pick guam leaf.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Guam herbs").onMenuImpostor(26828, 39816).addToStorage(ItemID.GRIMY_GUAM_LEAF),
            new OnMenuEntryAdded().isReplaceImpostorId(
                26824, 26825, 26826, 26827, 26828,
                39812, 39813, 39814, 39815, 39816
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Guam herbs"),
                new ReplaceTarget("Herb patch", "Guam herbs")
            ).onMenuTarget("Herbs"),

            // Pick marrentill.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Marrentill herbs").onMenuImpostor(39751, 39821).addToStorage(ItemID.GRIMY_MARRENTILL),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39747, 39748, 39749, 39750, 39751,
                39816, 39817, 39818, 39819, 39821
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Marrentill herbs"),
                new ReplaceTarget("Herb patch", "Marrentill herbs")
            ).onMenuTarget("Herbs"),

            // Pick tarromin.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Tarromin herbs").onMenuImpostor(39756, 39826).addToStorage(ItemID.GRIMY_TARROMIN),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39752, 39753, 39754, 39755, 39756,
                39822, 39823, 39824, 39825, 39826
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Tarromin herbs"),
                new ReplaceTarget("Herb patch", "Tarromin herbs")
            ).onMenuTarget("Herbs"),

            // Pick harralander.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Harralander herbs").onMenuImpostor(39761, 39831).addToStorage(ItemID.GRIMY_HARRALANDER),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39757, 39758, 39759, 39760, 39761,
                39827, 39828, 39829, 39830, 39831
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Harralander herbs"),
                new ReplaceTarget("Herb patch", "Harralander herbs")
            ).onMenuTarget("Herbs"),

            // Pick ranarr.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Ranarr weed herbs").onMenuImpostor(39766, 39836).addToStorage(ItemID.GRIMY_RANARR_WEED),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39762, 39763, 39764, 39765, 39766,
                39832, 39833, 39834, 39835, 39836
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Ranarr weed herbs"),
                new ReplaceTarget("Herb patch", "Ranarr weed herbs")
            ).onMenuTarget("Herbs"),

            // Pick irit leaf.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Irit leaf herbs").onMenuImpostor(39771, 39841).addToStorage(ItemID.GRIMY_IRIT_LEAF),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39767, 39768, 39769, 39770, 39771,
                39837, 39838, 39839, 39840, 39841
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Irit leaf herbs"),
                new ReplaceTarget("Herb patch", "Irit leaf herbs")
            ).onMenuTarget("Herbs"),

            // Pick avantoe.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Avantoe herbs").onMenuImpostor(39776, 39846).addToStorage(ItemID.GRIMY_AVANTOE),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39772, 39773, 39774, 39775, 39776,
                39842, 39843, 39844, 39845, 39846
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Avantoe herbs"),
                new ReplaceTarget("Herb patch", "Avantoe herbs")
            ).onMenuTarget("Herbs"),

            // Pick toadflax.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Toadflax herbs").onMenuImpostor(39781, 39851).addToStorage(ItemID.GRIMY_TOADFLAX),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39777, 39778, 39779, 39780, 39781,
                39847, 39848, 39849, 39850, 39851
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Toadflax herbs"),
                new ReplaceTarget("Herb patch", "Toadflax herbs")
            ).onMenuTarget("Herbs"),

            // Pick kwuarm.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Kwuarm herbs").onMenuImpostor(39786, 39856).addToStorage(ItemID.GRIMY_KWUARM),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39782, 39783, 39784, 39785, 39786,
                39852, 39853, 39854, 39855, 39856
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Kwuarm herbs"),
                new ReplaceTarget("Herb patch", "Kwuarm herbs")
            ).onMenuTarget("Herbs"),

            // Pick huasca.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Huasca herbs").onMenuImpostor(39786, 39856).addToStorage(ItemID.GRIMY_HUASCA),
            new OnMenuEntryAdded().isReplaceImpostorId(
                55342, 55343, 55344, 55345, 55346,
                55347, 55348, 55349, 55350, 55351
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Huasca herbs"),
                new ReplaceTarget("Herb patch", "Huasca herbs")
            ).onMenuTarget("Herbs"),

            // Pick cadantine.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Cadantine herbs").onMenuImpostor(39791, 39861).addToStorage(ItemID.GRIMY_CADANTINE),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39787, 39788, 39789, 39790, 39791,
                39857, 39858, 39859, 39860, 39861
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Cadantine herbs"),
                new ReplaceTarget("Herb patch", "Cadantine herbs")
            ).onMenuTarget("Herbs"),

            // Pick lantadyme.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Lantadyme herbs").onMenuImpostor(39796, 39866).addToStorage(ItemID.GRIMY_LANTADYME),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39792, 39793, 39794, 39795, 39796,
                39862, 39863, 39864, 39865, 39866
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Lantadyme herbs"),
                new ReplaceTarget("Herb patch", "Lantadyme herbs")
            ).onMenuTarget("Herbs"),

            // Pick dwarf weed.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Dwarf weed herbs").onMenuImpostor(39801, 39871).addToStorage(ItemID.GRIMY_DWARF_WEED),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39797, 39798, 39799, 39800, 39801,
                39867, 39868, 39869, 39870, 39871
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Dwarf weed herbs"),
                new ReplaceTarget("Herb patch", "Dwarf weed herbs")
            ).onMenuTarget("Herbs"),

            // Pick torstol.
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Torstol herbs").onMenuImpostor(39806, 39876).addToStorage(ItemID.GRIMY_TORSTOL),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39802, 39803, 39804, 39805, 39806,
                39872, 39873, 39874, 39875, 39876
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Torstol herbs"),
                new ReplaceTarget("Herb patch", "Torstol herbs")
            ).onMenuTarget("Herbs"),

            // Pick snapdragon
            new OnXpDrop(Skill.FARMING).requiredItem(ItemID.OPEN_HERB_SACK).onMenuOption("Pick").onMenuTarget("Herbs", "Snapdragon herbs").onMenuImpostor(39811, 39881).addToStorage(ItemID.GRIMY_SNAPDRAGON),
            new OnMenuEntryAdded().isReplaceImpostorId(
                39807, 39808, 39809, 39810, 39811,
                39877, 39878, 39879, 39880, 39881
            ).replaceTargets(
                new ReplaceTarget("Herbs", "Snapdragon herbs"),
                new ReplaceTarget("Herb patch", "Snapdragon herbs")
            ).onMenuTarget("Herbs"),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide()
        };
    }
}
