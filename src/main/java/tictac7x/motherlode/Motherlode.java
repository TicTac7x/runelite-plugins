package tictac7x.motherlode;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.Varbits;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;

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
        return Math.min(
            sack.getSize() - sack.getDepositedPaydirt(),
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
        return bank.getGoldenNuggets();
    }

    public int getDepositsLeft() {
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
        if (!notifiedToStopMining && event.getContainerId() == InventoryID.INVENTORY.getId() && shouldStopMining() && config.notifyToStopMining()) {
            notifier.notify("Stop mining! Sack will be too full.");
            notifiedToStopMining = true;
        }
    }

    public void onVarbitChanged(final VarbitChanged event) {
        if (event.getVarbitId() == Varbits.SACK_NUMBER) {
            notifiedToStopMining = false;
        }
    }

    public void onChatMessage(final ChatMessage event) {
        if (event.getType() == ChatMessageType.MESBOX && event.getMessage().contains("You collect your ore from the sack.")) {
            depositFoundGoldenNuggetsToBank();
        }
    }

    private void depositFoundGoldenNuggetsToBank() {
        final ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory == null) return;

        for (final Item item : inventory.getItems()) {
            if (item.getId() == ItemID.GOLDEN_NUGGET) {
                goldenNuggetsBefore = item.getQuantity();
                break;
            }
        }

        clientThread.invokeLater(() -> {
            final ItemContainer inventoryNextTick = client.getItemContainer(InventoryID.INVENTORY);
            if (inventoryNextTick == null) return;

            for (final Item item : inventoryNextTick.getItems()) {
                if (item.getId() == ItemID.GOLDEN_NUGGET) {
                    final int quantity = item.getQuantity() - goldenNuggetsBefore;
                    bank.depositGoldenNuggets(quantity);
                    goldenNuggetsSession += quantity;
                    break;
                }
            }
        });
    }
}
