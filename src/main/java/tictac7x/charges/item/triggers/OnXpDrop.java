package tictac7x.charges.item.triggers;

import net.runelite.api.Skill;

import java.util.Optional;
import java.util.function.Consumer;

public class OnXpDrop extends TriggerBase {
    public final Skill skill;
    public Optional<Integer> amount = Optional.empty();
    public Optional<Consumer<Integer>> xpAmountConsumer = Optional.empty();

    public OnXpDrop(final Skill skill) {
        this.skill = skill;
    }

    public OnXpDrop(final Skill skill, final int amount) {
        this.skill = skill;
        this.amount = Optional.of(amount);
    }

    public OnXpDrop xpAmountConsumer(final Consumer<Integer> xpAmountConsumer) {
        this.xpAmountConsumer = Optional.of(xpAmountConsumer);
        return this;
    }
}
