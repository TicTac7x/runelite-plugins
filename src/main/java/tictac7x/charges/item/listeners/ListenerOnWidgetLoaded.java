package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnWidgetLoaded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.ItemWithQuantity;

import java.util.Optional;
import java.util.regex.Matcher;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.getNumberFromCommaString;

public class ListenerOnWidgetLoaded extends ListenerBase {
    public ListenerOnWidgetLoaded(final Client client, final ItemManager itemManager, final ChargedItemBase chargedItem, final Notifier notifier, final TicTac7xChargesImprovedConfig config) {
        super(client, itemManager, chargedItem, notifier, config);
    }

    public void trigger(final WidgetLoaded event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;

            boolean triggerUsed = false;
            final OnWidgetLoaded trigger = (OnWidgetLoaded) triggerBase;
            final Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(client, trigger.groupId, trigger.childId, trigger.subChildId);
            if (!widget.isPresent()) continue;

            if (trigger.text.isPresent()) {
                final String text = TicTac7xChargesImprovedPlugin.getCleanText(widget.get().getText());
                final Matcher matcher = trigger.text.get().matcher(text);
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

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed && !trigger.multiTrigger) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final WidgetLoaded event) {
        if (!(triggerBase instanceof OnWidgetLoaded)) return false;
        final OnWidgetLoaded trigger = (OnWidgetLoaded) triggerBase;

        // Widget group check.
        if (event.getGroupId() != trigger.groupId) {
            return false;
        }

        // Widget existance check.
        final Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(client, trigger.groupId, trigger.childId, trigger.subChildId);
        if (!widget.isPresent()) {
            return false;
        }

        // Text check.
        if (trigger.text.isPresent()) {
            final Matcher matcher = trigger.text.get().matcher(TicTac7xChargesImprovedPlugin.getCleanText(widget.get().getText()));
            if (!matcher.find()) {
                return false;
            }
        }

        return super.isValidTrigger(trigger);
    }
}
