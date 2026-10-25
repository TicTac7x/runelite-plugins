package tictac7x.charges.item.triggers;

import net.runelite.api.*;

import java.util.*;
import java.util.function.*;

public class OnXpDrop extends TriggerBase {
    public Skill skill;
    public Optional<Integer> amount = Optional.empty();
    public Optional<Consumer<Integer>> amountConsumer = Optional.empty();

    public OnXpDrop(Skill skill) {
        this.skill = skill;
    }

    public OnXpDrop(Skill skill, int amount) {
        this.skill = skill;
        this.amount = Optional.of(amount);
    }

    public OnXpDrop amountConsumer(Consumer<Integer> consumer) {
        this.amountConsumer = Optional.of(consumer);
        return this;
    }
}
