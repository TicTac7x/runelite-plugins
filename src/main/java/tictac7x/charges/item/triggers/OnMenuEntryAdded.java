package tictac7x.charges.item.triggers;

import tictac7x.charges.store.DynamicReplaceTarget;
import tictac7x.charges.store.ReplaceTarget;

import java.util.Optional;
import java.util.concurrent.Callable;

public class OnMenuEntryAdded extends TriggerBase {
    public final Optional<String> menuEntryOption;
    public Optional<Boolean> hide = Optional.empty();
    public Optional<String> replaceOption = Optional.empty();
    public Optional<ReplaceTarget[]> replaceTargets = Optional.empty();
    public Optional<int[]> replaceImpostorIds = Optional.empty();
    public Optional<DynamicReplaceTarget> replaceTargetDynamically = Optional.empty();
    public Optional<Callable<String>> replaceOptionConsumer = Optional.empty();

    public OnMenuEntryAdded() {
        this.menuEntryOption = Optional.empty();
    }

    public OnMenuEntryAdded(final String option) {
        this.menuEntryOption = Optional.of(option);
    }

    public OnMenuEntryAdded hide() {
        this.hide = Optional.of(true);
        return this;
    }

    public OnMenuEntryAdded replaceOption(final String option) {
        this.replaceOption = Optional.of(option);
        return this;
    }

    public OnMenuEntryAdded replaceTarget(final String target, final String replace) {
        return replaceTargets(new ReplaceTarget(target, replace));
    }

    public OnMenuEntryAdded replaceTargetDynamically(final String target, final Callable<String> dynamicTarget) {
        this.replaceTargetDynamically = Optional.of(new DynamicReplaceTarget(target, dynamicTarget));
        return this;
    }

    public OnMenuEntryAdded replaceTargets(final ReplaceTarget ...targets) {
        this.replaceTargets = Optional.of(targets);
        return this;
    }

    public OnMenuEntryAdded isReplaceImpostorId(final int ...impostorIds) {
        this.replaceImpostorIds = Optional.of(impostorIds);
        return this;
    }

    public OnMenuEntryAdded replaceOptionConsumer(Callable<String> consumer) {
        this.replaceOptionConsumer = Optional.of(consumer);
        return this;
    }
}
