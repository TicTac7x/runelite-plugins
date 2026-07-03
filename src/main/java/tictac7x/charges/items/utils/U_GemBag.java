package tictac7x.charges.items.utils;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.store.Provider;

public class U_GemBag extends U_AbstractGemContainer {
    public U_GemBag(final Provider provider) {
        super(
                TicTac7xChargesImprovedConfig.gem_bag,
                ItemId.GEM_BAG,
                ItemId.GEM_BAG_OPEN,
                60,
                "gem bag",
                new StorableItem[]{
                        new StorableItem(ItemId.UNCUT_SAPPHIRE).checkName("Sapphire"),
                        new StorableItem(ItemId.UNCUT_EMERALD).checkName("Emerald"),
                        new StorableItem(ItemId.UNCUT_RUBY).checkName("Ruby"),
                        new StorableItem(ItemId.UNCUT_DIAMOND).checkName("Diamond"),
                        new StorableItem(ItemId.UNCUT_DRAGONSTONE).checkName("Dragonstone"),
                },
                provider
        );
    }
}
