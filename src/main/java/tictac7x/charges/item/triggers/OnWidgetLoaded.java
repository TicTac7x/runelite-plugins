package tictac7x.charges.item.triggers;

import net.runelite.api.widgets.Widget;
import tictac7x.charges.store.ItemWithQuantity;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnWidgetLoaded extends TriggerBase {
    public final int groupId;
    public final int childId;
    public Optional<Integer> subChildId = Optional.empty();

    public Optional<Pattern> text = Optional.empty();
    public Optional<Consumer<Matcher>> matcherConsumer = Optional.empty();
    public Optional<Consumer<Widget>> widgetConsumer = Optional.empty();
    public Optional<Boolean> setDynamically = Optional.empty();

    public OnWidgetLoaded(final int groupId, final int childId) {
        this.groupId = groupId;
        this.childId = childId;
    }

    public OnWidgetLoaded(final int groupId, final int childId, final int subChildId) {
        this.groupId = groupId;
        this.childId = childId;
        this.subChildId = Optional.of(subChildId);
    }

    public OnWidgetLoaded setDynamically() {
        this.setDynamically = Optional.of(true);
        return this;
    }

    public OnWidgetLoaded text(final String text) {
        this.text = Optional.of(Pattern.compile(text));
        return this;
    }

    public OnWidgetLoaded matcherConsumer(final Consumer<Matcher> consumer) {
        this.matcherConsumer = Optional.of(consumer);
        return this;
    }

    public OnWidgetLoaded widgetConsumer(final Consumer<Widget> consumer) {
        this.widgetConsumer = Optional.of(consumer);
        return this;
    }
}
