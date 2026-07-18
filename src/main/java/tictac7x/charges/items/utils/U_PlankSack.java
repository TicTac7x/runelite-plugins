package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class U_PlankSack extends ChargedItemWithStorageEmptyable {
    public U_PlankSack(Provider provider) {
        super(TicTac7xChargesImprovedConfig.plank_sack, ItemID.PLANK_SACK, provider);
        storage.setMaximumTotalQuantity(28).storableItems(
            new StorableItem(ItemID.WOODPLANK).checkName("Regular plank"),
            new StorableItem(ItemID.PLANK_OAK).checkName("Oak plank"),
            new StorableItem(ItemID.PLANK_TEAK).checkName("Teak plank"),
            new StorableItem(ItemID.PLANK_MAHOGANY).checkName("Mahogany plank"),
            new StorableItem(ItemID.PLANK_CAMPHOR).checkName("Camphor plank"),
            new StorableItem(ItemID.PLANK_IRONWOOD).checkName("Ironwood plank"),
            new StorableItem(ItemID.PLANK_ROSEWOOD).checkName("Rosewood plank")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.PLANK_SACK),
        };

        this.triggers.addAll(List.of(
            // Empty
            new OnChatMessage("Your sack is currently empty.").onItemClick().emptyStorage(),

            // Check
            new OnMenuOptionClicked("Check").consumer(() -> storage.clear()),
            new OnChatMessage("Regular planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemID.WOODPLANK, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Oak planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemID.PLANK_OAK, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Teak planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemID.PLANK_TEAK, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Mahogany planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemID.PLANK_MAHOGANY, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Camphor planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemID.PLANK_CAMPHOR, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Ironwood planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemID.PLANK_IRONWOOD, Integer.parseInt(m.group("charges")))),
            new OnChatMessage("Rosewood planks: (?<charges>.+)").matcherConsumer(m -> storage.put(ItemID.PLANK_ROSEWOOD, Integer.parseInt(m.group("charges")))),

            // Contents changed
            new OnVarbitsMapChanged(
                Map.of(
                    VarbitID.PLANK_SACK_PLAIN, ItemID.WOODPLANK,
                    VarbitID.PLANK_SACK_OAK, ItemID.PLANK_OAK,
                    VarbitID.PLANK_SACK_TEAK, ItemID.PLANK_TEAK,
                    VarbitID.PLANK_SACK_MAHOGANY, ItemID.PLANK_MAHOGANY,
                    VarbitID.PLANK_SACK_CAMPHOR, ItemID.PLANK_CAMPHOR,
                    VarbitID.PLANK_SACK_IRONWOOD, ItemID.PLANK_IRONWOOD,
                    VarbitID.PLANK_SACK_ROSEWOOD, ItemID.PLANK_ROSEWOOD
                )
            )
        ));
    }
}
