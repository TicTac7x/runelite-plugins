package tictac7x.charges.item.triggers;

import net.runelite.api.widgets.*;
import java.util.*;
import java.util.function.*;
import java.util.regex.*;

public class OnWidgetLoaded extends TriggerBase {
    public int groupId;
    public int childId;
    public Optional<Integer> subChildId = Optional.empty();

    public Optional<Pattern> text = Optional.empty();
    public Optional<Consumer<Matcher>> matcherConsumer = Optional.empty();
    public Optional<Consumer<Widget>> widgetConsumer = Optional.empty();
    public Optional<Boolean> setDynamically = Optional.empty();

    public OnWidgetLoaded(int groupId, int childId) {
        this.groupId = groupId;
        this.childId = childId;
    }

    public OnWidgetLoaded(int groupId, int childId, int subChildId) {
        this.groupId = groupId;
        this.childId = childId;
        this.subChildId = Optional.of(subChildId);
    }

    public OnWidgetLoaded setDynamically() {
        this.setDynamically = Optional.of(true);
        return this;
    }

    public OnWidgetLoaded text(String text) {
        this.text = Optional.of(Pattern.compile(text));
        return this;
    }

    public OnWidgetLoaded matcherConsumer(Consumer<Matcher> consumer) {
        this.matcherConsumer = Optional.of(consumer);
        return this;
    }

    public OnWidgetLoaded widgetConsumer(Consumer<Widget> consumer) {
        this.widgetConsumer = Optional.of(consumer);
        return this;
    }
}
