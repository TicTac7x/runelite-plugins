package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class J_EscapeCrystal extends ChargedItemWithStatus {
    private Instant instantToTeleport = Instant.now();
    private boolean alertedAboutActivation = false;

    public J_EscapeCrystal(
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ChatMessageManager chatMessageManager,
        final Notifier notifier,
        final TicTac7xChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(TicTac7xChargesImprovedConfig.escape_crystal, ItemId.ESCAPE_CRYSTAL, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ESCAPE_CRYSTAL).quantityCharges().hideOverlay(),
        };

        this.triggers = new TriggerBase[]{
            // Activate / Deactivate.
            new OnVarbitChanged(VarbitID.TELEPORT_CRYSTAL_AFK_MODE).varbitValueConsumer(value -> {
                if (value == 1) {
                    activate();
                } else {
                    deactivate();
                }
            }),

            // Inactivity timer.
            new OnVarbitChanged(VarbitID.TELEPORT_CRYSTAL_AFK_DELAY).varbitValueConsumer((value) -> {
                configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.escape_crystal_inactivity_period, value);
            }),

            // Keyboard or mouse action resets idle timer.
            new OnUserAction().consumer(this::resetIdleTimer),
        };
    }

    private void resetIdleTimer() {
        instantToTeleport = Instant.now().plusMillis(config.getEscapeCrystalInactivityPeriod() * 600L + 600L);

        store.addConsumerToNextTickQueue(() -> {
            alertedAboutActivation = false;
        });
    }

    private long getTimeRemainingUntilActivation() {
        switch (config.getEscapeCrystalTimeRemainingUnit()) {
            case SECONDS:
                return Math.max(0, (Duration.between(Instant.now(), instantToTeleport).toMillis()) / 1000);
            case TICKS:
            default:
                return Math.max(0, (Duration.between(Instant.now(), instantToTeleport).toMillis()) / 600);
        }
    }

    private boolean isAboutToActivate() {
        return config.getEscapeCrystalTimeRemainingWarning() > 0 && isActivated() && store.isLockedInCombat() && getTimeRemainingUntilActivation() <= config.getEscapeCrystalTimeRemainingWarning();
    }

    @Override
    public Color getTextColor() {
        return isAboutToActivate() ? Color.YELLOW : super.getTextColor();
    }

    @Override
    public String getCharges(final int itemId) {
        if (config.getEscapeCrystalStatus() == ItemActivity.DEACTIVATED || (!inInventory() && !inEquipment())) { return "\u221E"; }
        if (config.getEscapeCrystalInactivityPeriod() == Charges.UNKNOWN) { return "?"; }

        final long timeRemainingUntilActivation = getTimeRemainingUntilActivation();
        if (!alertedAboutActivation && isAboutToActivate()) {
            alertedAboutActivation = true;
            notifier.notify("Escape crystal is activating in " + timeRemainingUntilActivation + (config.getEscapeCrystalTimeRemainingUnit() == EscapeCrystalTimeRemainingUnit.SECONDS ? " seconds." : " ticks."));
        }

        switch (config.getEscapeCrystalTimeRemainingUnit()) {
            case SECONDS:
                return timeRemainingUntilActivation / 60 + ":" + String.format("%02d", timeRemainingUntilActivation % 60);
            case TICKS:
            default:
                return String.valueOf(timeRemainingUntilActivation);
        }
    }
}
