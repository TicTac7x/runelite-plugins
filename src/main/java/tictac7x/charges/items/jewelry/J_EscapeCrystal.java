package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.ids.*;

import java.awt.*;
import java.time.*;
import java.util.List;

public class J_EscapeCrystal extends ChargedItemWithStatus {
    private Instant instantToTeleport = Instant.now();
    private boolean alertedAboutActivation = false;
    private boolean inGauntletWithEscapeCrystal = false;

    public J_EscapeCrystal(Provider provider) {
        super(TicTac7xChargesImprovedConfig.escape_crystal, ItemID.TOB_TELEPORT, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.TOB_TELEPORT).quantityCharges().hideOverlay(),
        };

        this.triggers.addAll(List.of(
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

            // Keyboard or mouse actions.
            new OnUserAction().consumer(() -> {
                resetIdleTimer();
            }),

            // Enter Gauntlet detection.
            new OnMenuOptionClicked("Enter", "Enter-corrupted").onMenuTarget("The Gauntlet").consumer(() -> {
                if (provider.store.inventoryContainsItem(ItemID.TOB_TELEPORT)) {
                    inGauntletWithEscapeCrystal = true;
                } else if (provider.store.equipmentContainsItem(ItemID.TOB_TELEPORT)) {
                    provider.notifier.notify("Escape crystal disabled, because it was not in the inventory!");
                    inGauntletWithEscapeCrystal = false;
                } else {
                    inGauntletWithEscapeCrystal = false;
                }
            }),

            // Leave Gauntlet detection.
            new OnChatMessage("(You leave the Gauntlet.|Your reward awaits you in the nearby chest.)").consumer(() -> {
                inGauntletWithEscapeCrystal = false;
            }),

            new OnGameTick().consumer(() -> {
                long timeRemainingUntilActivation = getTimeRemainingUntilActivation();
                if (!alertedAboutActivation && isAboutToActivate()) {
                    alertedAboutActivation = true;
                    provider.notifier.notify("Escape crystal is activating in " + timeRemainingUntilActivation + (provider.config.getEscapeCrystalTimeRemainingUnit() == TicTac7xChargesImprovedConfig.EscapeCrystalTimeRemainingUnit.SECONDS ? " seconds." : " ticks."));
                }
            })
        ));
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
        return provider.config.getEscapeCrystalTimeRemainingWarning() > 0 && isActivated() && getTimeRemainingUntilActivation() <= provider.config.getEscapeCrystalTimeRemainingWarning();
    }

    @Override
    public boolean inInventory() {
        return super.inInventory() || inGauntletWithEscapeCrystal;
    }

    @Override
    public Color getTextColor(int itemId) {
        return getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        return isAboutToActivate() ? Color.YELLOW : isActivated() ? provider.config.getColorActivated() : provider.config.getColorEmpty();
    }

    @Override
    public String getChargesString(int itemId) {
        return getLongChargesString(itemId);
    }

    @Override
    public String getLongChargesString(int itemId) {
        return getTotalChargesString();
    }

    @Override
    public String getTotalChargesString() {
        if (
            provider.config.getEscapeCrystalStatus() == TicTac7xChargesImprovedConfig.ItemActivity.DEACTIVATED
            || !inInventoryOrEquipment()
        ) {
            return TicTac7xChargesImprovedPlugin.getChargesMinified(ChargeId.UNLIMITED);
        }

        if (provider.config.getEscapeCrystalInactivityPeriod() == ChargeId.UNKNOWN) {
            return TicTac7xChargesImprovedPlugin.getChargesMinified(ChargeId.UNKNOWN);
        }

        long timeRemainingUntilActivation = getTimeRemainingUntilActivation();
        switch (provider.config.getEscapeCrystalTimeRemainingUnit()) {
            case SECONDS:
                return timeRemainingUntilActivation / 60 + ":" + String.format("%02d", timeRemainingUntilActivation % 60);
            case TICKS:
            default:
                return String.valueOf(timeRemainingUntilActivation);
        }
    }
}
