package tictac7x.charges.item.triggers;

import tictac7x.charges.store.utils.*;

import java.util.*;
import java.util.concurrent.*;

public class OnMenuEntryAdded extends TriggerBase {
    public Optional<String> menuEntryOption;
    public Optional<Boolean> hide = Optional.empty();
    public Optional<String> replaceOption = Optional.empty();
    public Optional<ReplaceTarget[]> replaceTargets = Optional.empty();
    public Optional<int[]> replaceImpostorIds = Optional.empty();
    public Optional<DynamicReplaceTarget> replaceTargetDynamically = Optional.empty();
    public Optional<Callable<String>> replaceOptionConsumer = Optional.empty();

    public OnMenuEntryAdded() {
        this.menuEntryOption = Optional.empty();
    }

    public OnMenuEntryAdded(String option) {
        this.menuEntryOption = Optional.of(option);
    }

    public OnMenuEntryAdded hide() {
        this.hide = Optional.of(true);
        return this;
    }

    public OnMenuEntryAdded replaceOption(String option) {
        this.replaceOption = Optional.of(option);
        return this;
    }

    public OnMenuEntryAdded replaceTarget(String target, String replace) {
        return replaceTargets(new ReplaceTarget(target, replace));
    }

    public OnMenuEntryAdded replaceTargetDynamically(String target, Callable<String> dynamicTarget) {
        this.replaceTargetDynamically = Optional.of(new DynamicReplaceTarget(target, dynamicTarget));
        return this;
    }

    public OnMenuEntryAdded replaceTargets(ReplaceTarget ...targets) {
        this.replaceTargets = Optional.of(targets);
        return this;
    }

    public OnMenuEntryAdded isReplaceImpostorId(int ...impostorIds) {
        this.replaceImpostorIds = Optional.of(impostorIds);
        return this;
    }

    public OnMenuEntryAdded replaceOptionConsumer(Callable<String> consumer) {
        this.replaceOptionConsumer = Optional.of(consumer);
        return this;
    }
}
