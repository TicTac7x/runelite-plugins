package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_GamesNecklace extends ChargedItem {
    public J_GamesNecklace(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.games_necklace, ItemId.GAMES_NECKLACE_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.GAMES_NECKLACE_1).fixedCharges(1),
            new TriggerItem(ItemId.GAMES_NECKLACE_2).fixedCharges(2),
            new TriggerItem(ItemId.GAMES_NECKLACE_3).fixedCharges(3),
            new TriggerItem(ItemId.GAMES_NECKLACE_4).fixedCharges(4),
            new TriggerItem(ItemId.GAMES_NECKLACE_5).fixedCharges(5),
            new TriggerItem(ItemId.GAMES_NECKLACE_6).fixedCharges(6),
            new TriggerItem(ItemId.GAMES_NECKLACE_7).fixedCharges(7),
            new TriggerItem(ItemId.GAMES_NECKLACE_8).fixedCharges(8),
        };
    }
}
