package tictac7x.charges.item.listeners;

import net.runelite.api.events.*;
import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;
import java.util.regex.*;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.*;

public class ListenerOnWidgetLoaded extends ListenerBase {
    public ListenerOnWidgetLoaded(Provider provider) {
        super(provider);
    }

    public void trigger(WidgetLoaded event, ChargedItemBase chargedItem) {
        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, event)) continue;

            boolean triggerUsed = false;
            OnWidgetLoaded trigger = (OnWidgetLoaded) triggerBase;
            Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, trigger.groupId, trigger.childId, trigger.subChildId);
            if (!widget.isPresent()) continue;

            if (trigger.text.isPresent()) {
                String text = TicTac7xChargesImprovedPlugin.getCleanText(widget.get().getText());
                Matcher matcher = trigger.text.get().matcher(text);
                matcher.find();

                if (trigger.setDynamically.isPresent()) {
                    ((ChargedItem) chargedItem).setCharges(getNumberFromCommaString(matcher.group("charges")));
                    triggerUsed = true;
                }

                if (trigger.matcherConsumer.isPresent()) {
                    trigger.matcherConsumer.get().accept(matcher);
                    triggerUsed = true;
                }
            }

            if (trigger.widgetConsumer.isPresent()) {
                trigger.widgetConsumer.get().accept(widget.get());
                triggerUsed = true;
            }

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed && !trigger.multiTrigger) return;
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, WidgetLoaded event) {
        if (!(triggerBase instanceof OnWidgetLoaded)) return false;
        OnWidgetLoaded trigger = (OnWidgetLoaded) triggerBase;

        // Widget group check.
        if (event.getGroupId() != trigger.groupId) {
            return false;
        }

        // Widget existance check.
        Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, trigger.groupId, trigger.childId, trigger.subChildId);
        if (!widget.isPresent()) {
            return false;
        }

        // Text check.
        if (trigger.text.isPresent()) {
            Matcher matcher = trigger.text.get().matcher(TicTac7xChargesImprovedPlugin.getCleanText(widget.get().getText()));
            if (!matcher.find()) {
                return false;
            }
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
