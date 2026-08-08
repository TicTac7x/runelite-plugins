package tictac7x.motherlode;

import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import tictac7x.motherlode.ids.ItemContainerId;
import tictac7x.motherlode.ids.ItemId;
import tictac7x.motherlode.ids.VarbitId;

import java.util.*;

public class Motherlode {
    private final Client client;
    private final ClientThread clientThread;
    private final Notifier notifier;
    private final TicTac7xMotherlodeConfig config;
    private final Bank bank;
    private final Inventory inventory;
    private final Sack sack;
    private final Hopper hopper;
    private boolean notifiedToStopMining = false;
    private int goldenNuggetsBefore = 0;
    private int goldenNuggetsSession = 0;

    public Motherlode(final Client client, final ClientThread clientThread, final Notifier notifier, final TicTac7xMotherlodeConfig config, final Bank bank, final Inventory inventory, final Sack sack, final Hopper hopper) {
        this.client = client;
        this.clientThread = clientThread;
        this.notifier = notifier;
        this.config = config;
        this.bank = bank;
        this.inventory = inventory;
        this.sack = sack;
        this.hopper = hopper;
    }

    public int getDepositedPaydirt() {
        return sack.getPaydirt() + hopper.getPaydirt();
    }

    public int getSpaceLeftToDeposit() {
        return sack.getSize() - sack.getPaydirt() - hopper.getPaydirt();
    }

    public int getNeededPaydirt() {
        final int spaceRemaining = sack.getSize() - getDepositedPaydirt();
        if (!config.optimizeDeposits() && spaceRemaining < inventory.getMaximumAvailablePayDirt()) {
            return 0;
        }

        return Math.min(
            spaceRemaining,
            inventory.getMaximumAvailablePayDirt()
        ) - inventory.getPaydirt();
    }

    public boolean shouldStopMining() {
        return (
            getSpaceLeftToDeposit() == 0 ||
            getNeededPaydirt() < 0 ||
            getNeededPaydirt() == 0 && inventory.getPaydirt() < inventory.getMaximumAvailablePayDirt()
        );
    }

    public boolean shouldDepositPaydirt() {
        return getNeededPaydirt() == 0 && inventory.getPaydirt() > 0;
    }

    public int getGoldenNuggetsTotal() {
        return bank.getGoldenNuggets() + inventory.getGoldenNuggets();
    }

    public int getDepositsLeft() {
        final int spaceRemaining = sack.getSize() - getDepositedPaydirt();

        if (!config.optimizeDeposits() && spaceRemaining < inventory.getMaximumAvailablePayDirt()) {
            return 0;
        }

        return inventory.getMaximumAvailablePayDirt() == 0 ? 0 : (int) Math.ceil((double) getSpaceLeftToDeposit() / inventory.getMaximumAvailablePayDirt());
    }

    public int getInventoryPaydirt() {
        return inventory.getPaydirt();
    }

    public int getSackSize() {
        return sack.getSize();
    }

    public boolean sackCanBeMoreThanFull() {
        return inventory.getMaximumAvailablePayDirt() > getSpaceLeftToDeposit();
    }

    public int getGoldenNuggetsSession() {
        return goldenNuggetsSession;
    }

    public void onItemContainerChanged(final ItemContainerChanged event) {
        if (!notifiedToStopMining && event.getContainerId() == ItemContainerId.INVENTORY && shouldStopMining() && config.notifyToStopMining()) {
            notifier.notify("Stop mining! Sack will be too full.");
            notifiedToStopMining = true;
        }

        Optional<Widget> depositBoxWidget = Optional.ofNullable(client.getWidget(192, 0));
        if (depositBoxWidget.isPresent() && depositBoxWidget.get().isHidden() == false) {
            bank.depositGoldenNuggets(goldenNuggetsBefore - inventory.getGoldenNuggets());
        }
        goldenNuggetsBefore = inventory.getGoldenNuggets();
    }

    public void onVarbitChanged(final VarbitChanged event) {
        if (event.getVarbitId() == VarbitId.MOTHERLODE_SACK_PAYDIRT) {
            notifiedToStopMining = false;
        }
    }

    public IndexedObjectSet<? extends Player> getPlayers() {
        return client.getTopLevelWorldView().players();
    }
}
