package tictac7x.charges.item.triggers;

import tictac7x.charges.store.AdvancedMenuEntry;

import java.util.Optional;
import java.util.function.Consumer;

public class OnMenuOptionClicked extends TriggerBase {
    public final String option;
    public Optional<Consumer<AdvancedMenuEntry>> menuOptionConsumer = Optional.empty();

    public OnMenuOptionClicked(final String option) {
        this.option = option;
    }

    public OnMenuOptionClicked menuOptionConsumer(final Consumer<AdvancedMenuEntry> consumer) {
        this.menuOptionConsumer = Optional.of(consumer);
        return this;
    }
}
