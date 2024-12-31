package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.ItemActivity;
import tictac7x.charges.store.Store;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class J_EscapeCrystal extends ChargedItemWithStatus {
    private Instant instant = Instant.now();
    private boolean alertedAboutActivation = false;

    public J_EscapeCrystal(
        final Client client,
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(ChargesImprovedConfig.escape_crystal, ItemID.ESCAPE_CRYSTAL, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ESCAPE_CRYSTAL).quantityCharges().hideOverlay(),
        };

        this.triggers = new TriggerBase[]{
            // Activate / Deactivate.
            new OnVarbitChanged(14838).varbitValueConsumer(value -> {
                if (value == 1) {
                    activate();
                } else {
                    deactivate();
                }
            }),

            // Inactivity period from dialog when changed inactivity period.
            new OnChatMessage("The inactivity period for auto-activation (remains unchanged at|is now) (?<seconds>.+?)s.*").matcherConsumer(matcher -> {
                configManager.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.escape_crystal_inactivity_period, matcher.group("seconds"));
            }),

            // Inactivity period from game message when activating.
            new OnChatMessage("Your escape crystals will now auto-activate if you take damage after a (?<seconds>.+?) seconds.*").matcherConsumer(matcher -> {
                configManager.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.escape_crystal_inactivity_period, matcher.group("seconds"));
            }),

            // Inactivity period from widget.
            new OnWidgetLoaded(219, 1, 3).text("Set auto-activation inactivity period \\(in seconds\\)\\(current: (?<seconds>.+?)s\\)").matcherConsumer(matcher -> {
                configManager.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.escape_crystal_inactivity_period, matcher.group("seconds"));
            }),

            // Keyboard or mouse action resets idle timer.
            new OnUserAction().consumer(() -> {
                resetIdleTimer();
            })
        };
    }

    private boolean isInCombat() {
        return client.getLocalPlayer().getHealthScale() != -1;
    }

    private void resetIdleTimer() {
        instant = Instant.now();
        alertedAboutActivation = false;
    }

    private long getSecondsRemainingUntilActivation() {
        return Math.max(0, config.getEscapeCrystalInactivityPeriod() - Duration.between(instant, Instant.now()).toMillis() / 1000);
    }

    private boolean isAboutToActivate() {
        return isActivated() && isInCombat() && (getSecondsRemainingUntilActivation() <= config.getEscapeCrystalTimeRemainingWarning());
    }

    @Override
    public Color getTextColor() {
        return isAboutToActivate() ? Color.YELLOW : super.getTextColor();
    }

    @Override
    public String getCharges() {
        if (config.getEscapeCrystalStatus() == ItemActivity.DEACTIVATED || (!inInventory() && !inEquipment())) { return "∞"; }
        if (config.getEscapeCrystalInactivityPeriod() == Charges.UNKNOWN) { return "?"; }

        final long secondsRemainingUntilActivation = getSecondsRemainingUntilActivation();
        if (!alertedAboutActivation && isAboutToActivate()) {
            alertedAboutActivation = true;
            notifier.notify("Escape crystal is activating in " + secondsRemainingUntilActivation + " seconds.");
        }

        return secondsRemainingUntilActivation / 60 + ":" + String.format("%02d", secondsRemainingUntilActivation % 60);
    }
}
