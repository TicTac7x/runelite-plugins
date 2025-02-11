package tictac7x.charges.item.triggers;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnChatMessage extends TriggerBase {
    public final Pattern message;

    public Optional<Boolean> setDynamically = Optional.empty();
    public Optional<Boolean> increaseDynamically = Optional.empty();
    public Optional<Boolean> decreaseDynamically = Optional.empty();
    public Optional<Boolean> useDifference = Optional.empty();
    public Optional<Consumer<Matcher>> matcherConsumer = Optional.empty();
    public Optional<Boolean> notification = Optional.empty();
    public Optional<Consumer<String>> stringConsumer = Optional.empty();

    public OnChatMessage(final String message) {
        this.message = Pattern.compile(message);
    }

    public TriggerBase setDynamicallyCharges() {
        this.setDynamically = Optional.of(true);
        return this;
    }

    public OnChatMessage matcherConsumer(final Consumer<Matcher> consumer) {
        this.matcherConsumer = Optional.of(consumer);
        return this;
    }

    public OnChatMessage notification() {
        this.notification = Optional.of(true);
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

    public OnChatMessage stringConsumer(final Consumer<String> consumer) {
        this.stringConsumer = Optional.of(consumer);
        return this;
    }

    public OnChatMessage decreaseDynamicallyCharges() {
        this.decreaseDynamically = Optional.of(true);
        return this;
    }
}
