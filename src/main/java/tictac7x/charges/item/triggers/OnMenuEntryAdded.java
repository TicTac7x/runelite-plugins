package tictac7x.charges.item.triggers;

import java.util.*;
import java.util.concurrent.*;

public class OnMenuEntryAdded extends TriggerBase {
    public Optional<String> menuEntryOption;
    public Optional<int[]> menuEntryIdentifier;
    public Optional<Boolean> hide = Optional.empty();
    public Optional<String> replaceOption = Optional.empty();
    public Optional<Callable<String>> replaceOptionConsumer = Optional.empty();

    public OnMenuEntryAdded() {
        this.menuEntryOption = Optional.empty();
        this.menuEntryIdentifier = Optional.empty();
    }

    public OnMenuEntryAdded(String option) {
        this.menuEntryOption = Optional.of(option);
        this.menuEntryIdentifier = Optional.empty();
    }

    public OnMenuEntryAdded(String option, int ... identifiers) {
        this.menuEntryOption = Optional.of(option);
        this.menuEntryIdentifier = Optional.of(identifiers);
    }

    public OnMenuEntryAdded hide() {
        this.hide = Optional.of(true);
        return this;
    }

    public OnMenuEntryAdded replaceOption(String option) {
        this.replaceOption = Optional.of(option);
        return this;
    }

    public OnMenuEntryAdded replaceOptionConsumer(Callable<String> consumer) {
        this.replaceOptionConsumer = Optional.of(consumer);
        return this;
    }

    public OnMenuEntryAdded onMenuEntryIdentifier(int ...menuOptionIdentifiers) {
        this.menuEntryIdentifier = Optional.of(menuOptionIdentifiers);
        return this;
    }
}
