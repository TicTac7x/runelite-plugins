package tictac7x.charges.item.triggers;

import tictac7x.charges.store.ids.*;

import java.util.*;

public class TriggerItem {
    public int itemId;

    public Optional<Boolean> quantityCharges = Optional.empty();
    public Optional<Boolean> hideOverlay = Optional.empty();
    public Optional<Boolean> needsToBeEquipped = Optional.empty();
    public Optional<Integer> maxCharges = Optional.empty();
    public Optional<Integer> fixedCharges = Optional.empty();

    public TriggerItem(int itemId) {
        this.itemId = itemId;
    }

    public TriggerItem fixedCharges(int charges) {
        this.fixedCharges = Optional.of(charges);
        return this;
    }

    public TriggerItem unlimitedCharges() {
        this.fixedCharges = Optional.of(ChargeId.UNLIMITED);
        return this;
    }

    public TriggerItem quantityCharges() {
        this.quantityCharges = Optional.of(true);
        return this;
    }

    public TriggerItem hideOverlay() {
        this.hideOverlay = Optional.of(true);
        return this;
    }

    public TriggerItem needsToBeEquipped() {
        this.needsToBeEquipped = Optional.of(true);
        return this;
    }

    public TriggerItem maxCharges(int charges) {
        this.maxCharges = Optional.of(charges);
        return this;
    }
}
