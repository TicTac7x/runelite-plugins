package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class U_GemSack extends U_AbstractGemContainer {
    public U_GemSack(final Provider provider) {
        super(
                TicTac7xChargesImprovedConfig.gem_sack,
                ItemId.GEM_SACK,
                ItemId.GEM_SACK_OPEN,
                60,
                new StorableItem[]{
                        new StorableItem(ItemId.UNCUT_SAPPHIRE).checkName("Sapphire"),
                        new StorableItem(ItemId.UNCUT_EMERALD).checkName("Emerald"),
                        new StorableItem(ItemId.UNCUT_RUBY).checkName("Ruby"),
                        new StorableItem(ItemId.UNCUT_DIAMOND).checkName("Diamond"),
                        new StorableItem(ItemId.UNCUT_DRAGONSTONE).checkName("Dragonstone"),
                        new StorableItem(ItemId.UNCUT_OPAL).checkName("Opal"),
                        new StorableItem(ItemId.UNCUT_JADE).checkName("Jade"),
                        new StorableItem(ItemId.UNCUT_RED_TOPAZ).checkName("Red topaz"),
                },
                provider
        );
    }
}