package tictac7x.motherlode;

import net.runelite.api.Item;
import net.runelite.api.events.ItemContainerChanged;
import tictac7x.motherlode.ids.ItemContainerId;
import tictac7x.motherlode.ids.ItemId;

public class Inventory {
    private int paydirt = 0;
    private int otherItems = 0;
    private int goldenNuggets = 0;

    public void onItemContainerChanged(final ItemContainerChanged event) {
        if (event.getContainerId() != ItemContainerId.INVENTORY) return;

        int paydirt = 0;
        int otherItems = 0;

        for (final Item item : event.getItemContainer().getItems()) {
            if (item.getId() >= 0) {
                otherItems += item.getId() == ItemId.PAYDIRT ? 0 : 1;
                paydirt += item.getId() == ItemId.PAYDIRT ? 1 : 0;
            }
        }

        this.paydirt = paydirt;
        this.otherItems = otherItems;
        this.goldenNuggets = event.getItemContainer().count(ItemId.GOLDEN_NUGGET);
    }

    public int getPaydirt() {
        return paydirt;
    }

    public int getMaximumAvailablePayDirt() {
        return 28 - otherItems;
    }

    public int getGoldenNuggets() {
        return goldenNuggets;
    }
}
