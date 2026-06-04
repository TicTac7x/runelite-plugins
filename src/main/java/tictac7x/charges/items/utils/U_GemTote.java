package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class U_GemTote extends U_AbstractGemContainer{
    public U_GemTote(final Provider provider) {
        super(
                TicTac7xChargesImprovedConfig.gem_tote,
                ItemId.GEM_TOTE,
                ItemId.GEM_TOTE_OPEN,
                20,
                new StorableItem[]{
                        new StorableItem(ItemId.UNCUT_OPAL).checkName("Opal"),
                        new StorableItem(ItemId.UNCUT_JADE).checkName("Jade"),
                        new StorableItem(ItemId.UNCUT_RED_TOPAZ).checkName("Red topaz"),
                },
                provider
        );
    }
}

