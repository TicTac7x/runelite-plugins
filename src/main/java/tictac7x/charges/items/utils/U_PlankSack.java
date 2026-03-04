package tictac7x.charges.items.utils;

import net.runelite.api.gameval.VarbitID;
import tictac7x.charges.item.ChargedItemWithStorageEmptyable;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.List;
import java.util.Map;

public class U_PlankSack extends ChargedItemWithStorageEmptyable {
    public U_PlankSack(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.plank_sack, ItemId.PLANK_SACK, provider);
        storage.setMaximumTotalQuantity(28).storableItems(
            new StorableItem(ItemId.PLANK).checkName("Regular plank"),
            new StorableItem(ItemId.OAK_PLANK).checkName("Oak plank"),
            new StorableItem(ItemId.TEAK_PLANK).checkName("Teak plank"),
            new StorableItem(ItemId.MAHOGANY_PLANK).checkName("Mahogany plank"),
            new StorableItem(ItemId.CAMPHOR_PLANK).checkName("Camphor plank"),
            new StorableItem(ItemId.IRONWOOD_PLANK).checkName("Ironwood plank"),
            new StorableItem(ItemId.ROSEWOOD_PLANK).checkName("Rosewood plank")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.PLANK_SACK),
        };

        this.triggers.addAll(List.of(
            // Empty
            new OnChatMessage("Your sack is currently empty.").onItemClick().emptyStorage(),

            // Check
            new OnMenuOptionClicked("Check").consumer(() -> storage.clear()),
            new OnChatMessage("Regular planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemId.PLANK, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Oak planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemId.OAK_PLANK, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Teak planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemId.TEAK_PLANK, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Mahogany planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemId.MAHOGANY_PLANK, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Camphor planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemId.CAMPHOR_PLANK, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Ironwood planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemId.IRONWOOD_PLANK, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Rosewood planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemId.ROSEWOOD_PLANK, Integer.parseInt(m.group("charges")))),

            // Contents changed
            new OnVarbitsMapChanged(
                Map.of(
                    VarbitID.PLANK_SACK_PLAIN, ItemId.PLANK,
                    VarbitID.PLANK_SACK_OAK, ItemId.OAK_PLANK,
                    VarbitID.PLANK_SACK_TEAK, ItemId.TEAK_PLANK,
                    VarbitID.PLANK_SACK_MAHOGANY, ItemId.MAHOGANY_PLANK,
                    VarbitID.PLANK_SACK_CAMPHOR, ItemId.CAMPHOR_PLANK,
                    VarbitID.PLANK_SACK_IRONWOOD, ItemId.IRONWOOD_PLANK,
                    VarbitID.PLANK_SACK_ROSEWOOD, ItemId.ROSEWOOD_PLANK
                )
            )
        ));
    }
}
