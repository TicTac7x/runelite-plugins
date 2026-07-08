package tictac7x.charges.item;

import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.store.Provider;

public class ChargedItemWithStorageEmptyable extends ChargedItemWithStorage {
    public ChargedItemWithStorageEmptyable(final String configKey, final int itemId, final Provider provider) {
        super(configKey, itemId, provider);

        this.triggers.add(
            new OnChatMessage("You empty all of your containers into the bank.").emptyStorage()
        );
    }
}
