package tictac7x.charges.items.barrows;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.events.CustomMenuOptionClicked;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.store.Provider;

import java.util.List;

public class _BarrowsItem extends ChargedItem {
    public _BarrowsItem(
            final String itemName,
            final int itemId,
            final Provider provider
            ) {
        super(
            TicTac7xChargesImprovedConfig.barrows_gear + "_" + itemName.toLowerCase().replaceAll("'", "").replaceAll(" ", "_"),
            itemId,
            provider
        );

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage(itemName + ": (?<percentage>.+)% remaining until the next degradation.").matcherConsumer((m) -> {
                final int percentage = Integer.parseInt(m.group("percentage"));
                final int chargesUsedInCurrentTier = (100 - percentage) * 250 / 100;

                for (final CustomMenuOptionClicked menuOptionClicked : provider.store.menuOptionsClicked) {
                    if (menuOptionClicked.target.contains(provider.itemManager.getItemComposition(itemId).getName())) {
                        final int currentTierMaxCharges = Integer.parseInt(menuOptionClicked.target.replaceAll("\\D", "")) * 10;
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
    public String getChargesString(final int itemId) {
        return getLongChargesString(itemId);
    }

    @Override
    public String getLongChargesString(final int itemId) {
        final int charges = getCharges(itemId);

        switch (provider.config.combatTimeDegradableStyle()) {
            case PERCENTAGE:
                return charges * 100 / 1000 + "%";
            case TIME:
                final double hours = (double) (charges * 90 * 600) / 1000 / 3600;
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
