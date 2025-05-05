package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.VarbitID;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.ChargeId;
import tictac7x.charges.store.ids.ItemId;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public class J_EscapeCrystal extends ChargedItemWithStatus {
    private Instant instantToTeleport = Instant.now();
    private boolean alertedAboutActivation = false;

    public J_EscapeCrystal(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.escape_crystal, ItemId.ESCAPE_CRYSTAL, provider);

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
                provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.escape_crystal_inactivity_period, value);
            }),

            // Keyboard or mouse action resets idle timer.
            new OnUserAction().consumer(this::resetIdleTimer),
        };
    }

    private void resetIdleTimer() {
        instantToTeleport = Instant.now().plusMillis(provider.config.getEscapeCrystalInactivityPeriod() * 600L + 600L);

        provider.store.addConsumerToNextTickQueue(() -> {
            alertedAboutActivation = false;
        });
    }

    private long getTimeRemainingUntilActivation() {
        switch (provider.config.getEscapeCrystalTimeRemainingUnit()) {
            case SECONDS:
                return Math.max(0, (Duration.between(Instant.now(), instantToTeleport).toMillis()) / 1000);
            case TICKS:
            default:
                return Math.max(0, (Duration.between(Instant.now(), instantToTeleport).toMillis()) / 600);
        }
    }

    private boolean isAboutToActivate() {
        return provider.config.getEscapeCrystalTimeRemainingWarning() > 0 && isActivated() && provider.store.isLockedInCombat() && getTimeRemainingUntilActivation() <= provider.config.getEscapeCrystalTimeRemainingWarning();
    }

    @Override
    public Color getTextColor(int itemId) {
        return getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        return isAboutToActivate() ? Color.YELLOW : super.getTotalTextColor();
    }

    @Override
    public String getChargesString(final int itemId) {
        return getTotalChargesString();
    }

    @Override
    public String getTotalChargesString() {
        if (provider.config.getEscapeCrystalStatus() == TicTac7xChargesImprovedConfig.ItemActivity.DEACTIVATED || (!inInventory() && !inEquipment())) { return TicTac7xChargesImprovedPlugin.getChargesMinified(ChargeId.UNLIMITED); }
        if (provider.config.getEscapeCrystalInactivityPeriod() == ChargeId.UNKNOWN) { return TicTac7xChargesImprovedPlugin.getChargesMinified(ChargeId.UNKNOWN); }

        final long timeRemainingUntilActivation = getTimeRemainingUntilActivation();
        if (!alertedAboutActivation && isAboutToActivate()) {
            alertedAboutActivation = true;
            provider.notifier.notify("Escape crystal is activating in " + timeRemainingUntilActivation + (provider.config.getEscapeCrystalTimeRemainingUnit() == TicTac7xChargesImprovedConfig.EscapeCrystalTimeRemainingUnit.SECONDS ? " seconds." : " ticks."));
        }

        switch (provider.config.getEscapeCrystalTimeRemainingUnit()) {
            case SECONDS:
                return timeRemainingUntilActivation / 60 + ":" + String.format("%02d", timeRemainingUntilActivation % 60);
            case TICKS:
            default:
                return String.valueOf(timeRemainingUntilActivation);
        }
    }
}
