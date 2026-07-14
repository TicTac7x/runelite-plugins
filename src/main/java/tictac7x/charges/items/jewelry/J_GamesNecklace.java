package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class J_GamesNecklace extends ChargedItem {
    public J_GamesNecklace(Provider provider) {
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
