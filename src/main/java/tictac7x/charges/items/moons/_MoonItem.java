package tictac7x.charges.items.moons;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.store.Provider;

public class _MoonItem extends ChargedItem {
    public _MoonItem(final String configKey, final int itemId, final Provider provider) {
        super(TicTac7xChargesImprovedConfig.moons_gear_ + configKey, itemId, provider);
    }

    @Override
    protected String getChargesMinified(final int itemId) {
        switch (provider.config.combatTimeDegradableStyle()) {
            case PERCENTAGE:
                return getChargesFromConfig() * 100 / 3000 + "%";
            case TIME:
                final double hours = (double) (getChargesFromConfig() * 90 * 600) / 1000 / 3600;
                return String.format("%.1fh", hours).replaceAll("\\.0", "");
            case CHARGES:
            default:
                return super.getChargesMinified(itemId);
        }
    }
}
