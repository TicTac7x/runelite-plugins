package tictac7x.charges.item.triggers;

import tictac7x.charges.events.*;

import java.util.*;
import java.util.function.*;

public class OnMenuOptionClicked extends TriggerBase {
    public String[] options;
    public Optional<String[]> target = Optional.empty();
    public Optional<Consumer<CustomMenuOptionClicked>> menuOptionConsumer = Optional.empty();
    public Optional<Integer> hasItemId = Optional.empty();

    public OnMenuOptionClicked(String ...options) {
        this.options = options;
    }

    public OnMenuOptionClicked menuOptionConsumer(Consumer<CustomMenuOptionClicked> consumer) {
        this.menuOptionConsumer = Optional.of(consumer);
        return this;
    }

    public OnMenuOptionClicked hasItemId(int itemId) {
        this.hasItemId = Optional.of(itemId);
        return this;
    }

    public OnMenuOptionClicked target(String... target) {
        this.target = Optional.of(target);
        return this;
    }
}
