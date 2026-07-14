package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class J_GamesNecklace extends ChargedItem {
    public J_GamesNecklace(Provider provider) {
        super(TicTac7xChargesImprovedConfig.games_necklace, ItemID.NECKLACE_OF_MINIGAMES_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.NECKLACE_OF_MINIGAMES_1).fixedCharges(1),
            new TriggerItem(ItemID.NECKLACE_OF_MINIGAMES_2).fixedCharges(2),
            new TriggerItem(ItemID.NECKLACE_OF_MINIGAMES_3).fixedCharges(3),
            new TriggerItem(ItemID.NECKLACE_OF_MINIGAMES_4).fixedCharges(4),
            new TriggerItem(ItemID.NECKLACE_OF_MINIGAMES_5).fixedCharges(5),
            new TriggerItem(ItemID.NECKLACE_OF_MINIGAMES_6).fixedCharges(6),
            new TriggerItem(ItemID.NECKLACE_OF_MINIGAMES_7).fixedCharges(7),
            new TriggerItem(ItemID.NECKLACE_OF_MINIGAMES_8).fixedCharges(8),
        };
    }
}
