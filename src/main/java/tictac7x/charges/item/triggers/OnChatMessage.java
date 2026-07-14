package tictac7x.charges.item.triggers;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;

public class OnChatMessage extends TriggerBase {
    public Pattern message;

    public Optional<Boolean> setDynamically = Optional.empty();
    public Optional<Boolean> increaseDynamically = Optional.empty();
    public Optional<Boolean> decreaseDynamically = Optional.empty();
    public Optional<Boolean> useDifference = Optional.empty();
    public Optional<Consumer<Matcher>> matcherConsumer = Optional.empty();
    public Optional<Consumer<String>> stringConsumer = Optional.empty();

    public OnChatMessage(String message) {
        this.message = Pattern.compile(message);
    }

    public TriggerBase setDynamicallyCharges() {
        this.setDynamically = Optional.of(true);
        return this;
    }

    public OnChatMessage matcherConsumer(Consumer<Matcher> consumer) {
        this.matcherConsumer = Optional.of(consumer);
        return this;
    }

    public OnChatMessage increaseDynamically() {
        this.increaseDynamically = Optional.of(true);
        return this;
    }

    public OnChatMessage setDifferenceCharges() {
        this.useDifference = Optional.of(true);
        return this;
    }

    public OnChatMessage stringConsumer(Consumer<String> consumer) {
        this.stringConsumer = Optional.of(consumer);
        return this;
    }

    public OnChatMessage decreaseDynamicallyCharges() {
        this.decreaseDynamically = Optional.of(true);
        return this;
    }

    public OnChatMessage onItemClick() {
        super.onItemClick();
        return this;
    }
}
