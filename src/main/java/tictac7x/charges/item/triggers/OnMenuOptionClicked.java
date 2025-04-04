package tictac7x.charges.item.triggers;

import tictac7x.charges.customEvents.CustomMenuOptionClicked;

import java.util.Optional;
import java.util.function.Consumer;

public class OnMenuOptionClicked extends TriggerBase {
    public final String[] options;
    public Optional<Consumer<CustomMenuOptionClicked>> menuOptionConsumer = Optional.empty();
    public Optional<Integer> hasItemId = Optional.empty();

    public OnMenuOptionClicked(final String ...options) {
        this.options = options;
    }

    public OnMenuOptionClicked menuOptionConsumer(final Consumer<CustomMenuOptionClicked> consumer) {
        this.menuOptionConsumer = Optional.of(consumer);
        return this;
    }

    public OnMenuOptionClicked hasItemId(final int itemId) {
        this.hasItemId = Optional.of(itemId);
        return this;
    }
}
