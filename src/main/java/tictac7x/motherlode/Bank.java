package tictac7x.motherlode;

import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import tictac7x.motherlode.ids.ItemContainerId;
import tictac7x.motherlode.ids.ItemId;

public class Bank {
    private final ConfigManager configManager;
    private final TicTac7xMotherlodeConfig config;
    private final ItemManager itemManager;

    public Bank(final ConfigManager configManager, final TicTac7xMotherlodeConfig config, final ItemManager itemManager) {
        this.configManager = configManager;
        this.config = config;
        this.itemManager = itemManager;
    }

    public int getGoldenNuggets() {
        return config.getBankGoldenNuggets();
    }

    public void onItemContainerChanged(final ItemContainerChanged event) {
        if (event.getContainerId() != ItemContainerId.BANK) return;

        for (final Item item : event.getItemContainer().getItems()) {
            if (itemManager.canonicalize(item.getId()) == ItemId.GOLDEN_NUGGET) {
                final ItemComposition itemComposition = itemManager.getItemComposition(item.getId());
                setGoldenNuggets(itemComposition.getPlaceholderTemplateId() != -1 ? 0 : item.getQuantity());
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
