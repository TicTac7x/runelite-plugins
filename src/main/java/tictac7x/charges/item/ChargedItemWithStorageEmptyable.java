package tictac7x.charges.item;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ChargedItemWithStorageEmptyable extends ChargedItemWithStorage {
    public ChargedItemWithStorageEmptyable(String configKey, int itemId, Provider provider) {
        super(configKey, itemId, provider);

        this.triggers.add(
            new OnChatMessage("You empty all of your containers into the bank.").emptyStorage()
        );
    }
}
