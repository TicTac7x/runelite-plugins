package tictac7x.charges.items.barrows;

import tictac7x.charges.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.List;

public class _BarrowsItem extends ChargedItem {
    public _BarrowsItem(
            String itemName,
            int itemId,
            Provider provider
            ) {
        super(
            TicTac7xChargesImprovedConfig.barrows_gear + "_" + itemName.toLowerCase().replace("'", "").replace(" ", "_"),
            itemId,
            provider
        );

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage(itemName + ": (?<percentage>.+)% remaining until the next degradation.").matcherConsumer((m) -> {
                int percentage = Integer.parseInt(m.group("percentage"));
                int chargesUsedInCurrentTier = (100 - percentage) * 250 / 100;

                for (CustomMenuOptionClicked menuOptionClicked : provider.store.menuOptionsClicked) {
                    if (menuOptionClicked.target.contains(provider.itemManager.getItemComposition(itemId).name)) {
                        int currentTierMaxCharges = Integer.parseInt(menuOptionClicked.target.replaceAll("\\D", "")) * 10;
                        setCharges(currentTierMaxCharges - chargesUsedInCurrentTier);
                        return;
                    }
                }
            }),

            // Degrade in combat.
            new OnCombat(90).isEquipped().decreaseCharges(1)
        ));
    }

    @Override
    public String getChargesString(int itemId) {
        return getLongChargesString(itemId);
    }

    @Override
    public String getLongChargesString(int itemId) {
        int charges = getCharges(itemId);

        switch (provider.config.combatTimeDegradableStyle()) {
            case PERCENTAGE:
                return charges * 100 / 1000 + "%";
            case TIME:
                double hours = (double) (charges * 90 * 600) / 1000 / 3600;
                return String.format("%.1fh", hours).replaceAll("\\.0", "");
            case CHARGES:
            default:
                return super.getChargesString(itemId);
        }
    }

    @Override
    public String getTotalChargesString() {
        return getChargesString(itemId);
    }

    @Override
    public String getConfigKey() {
        return TicTac7xChargesImprovedConfig.barrows_gear;
    }
}
