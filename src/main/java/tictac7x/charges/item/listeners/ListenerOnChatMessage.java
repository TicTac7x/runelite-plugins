package tictac7x.charges.item.listeners;

import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.regex.*;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.getNumberFromCommaString;

public class ListenerOnChatMessage extends ListenerBase {
    public ListenerOnChatMessage(Provider provider) {
        super(provider);
    }

    public void trigger(CustomChatMessage event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;
            boolean triggerUsed = false;
            OnChatMessage trigger = (OnChatMessage) triggerBase;

            Matcher matcher = trigger.message.matcher(event.message);
            matcher.find();

            if (trigger.setDynamically.isPresent() && (chargedItem instanceof ChargedItem)) {
                ((ChargedItem) chargedItem).setCharges(getNumberFromCommaString(matcher.group("charges")));
                triggerUsed = true;
            }

            if (trigger.increaseDynamically.isPresent() && (chargedItem instanceof ChargedItem)) {
                ((ChargedItem) chargedItem).increaseCharges(getNumberFromCommaString(matcher.group("charges")));
                triggerUsed = true;
            }

            if (trigger.decreaseDynamically.isPresent() && (chargedItem instanceof ChargedItem)) {
                ((ChargedItem) chargedItem).decreaseCharges(getNumberFromCommaString(matcher.group("charges")));
                triggerUsed = true;
            }

            if (trigger.useDifference.isPresent() && (chargedItem instanceof ChargedItem)) {
                ((ChargedItem) chargedItem).setCharges(getNumberFromCommaString(matcher.group("total")) - getNumberFromCommaString(matcher.group("used")));
                triggerUsed = true;
            }

            if (trigger.matcherConsumer.isPresent()) {
                trigger.matcherConsumer.get().accept(matcher);
                triggerUsed = true;
            }

            if (trigger.stringConsumer.isPresent()) {
                trigger.stringConsumer.get().accept(event.message);
                triggerUsed = true;
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) {
                afterTrigger(trigger);
                return;
            }
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomChatMessage event) {
        if (!(triggerBase instanceof OnChatMessage)) return false;
        OnChatMessage trigger = (OnChatMessage) triggerBase;

        // Message check.
        Matcher matcher = trigger.message.matcher(event.message);
        if (!matcher.find()) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
