package tictac7x.motherlode;

import net.runelite.api.Item;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import tictac7x.motherlode.ids.ItemContainerId;
import tictac7x.motherlode.ids.ItemId;

public class Bank {
    private final ConfigManager configManager;
    private final TicTac7xMotherlodeConfig config;

    public Bank(final ConfigManager configManager, final TicTac7xMotherlodeConfig config) {
        this.configManager = configManager;
        this.config = config;
    }

    public int getGoldenNuggets() {
        return config.getBankGoldenNuggets();
    }

    public void onItemContainerChanged(final ItemContainerChanged event) {
        if (event.getContainerId() != ItemContainerId.BANK) return;

        for (final Item item : event.getItemContainer().getItems()) {
            if (item.getId() == ItemId.GOLDEN_NUGGET) {
                setGoldenNuggets(item.getQuantity());
                return;
            }
        }
    }

    public void depositGoldenNuggets(final int quantity) {
        setGoldenNuggets(config.getBankGoldenNuggets() + quantity);
    }

    private void setGoldenNuggets(final int quantity) {
        configManager.setConfiguration(TicTac7xMotherlodeConfig.group, TicTac7xMotherlodeConfig.bank_golden_nuggets, quantity);
    }
}
