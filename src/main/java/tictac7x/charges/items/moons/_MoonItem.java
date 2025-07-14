package tictac7x.charges.items.moons;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.store.Provider;

public class _MoonItem extends ChargedItem {
    public _MoonItem(final String configKey, final int itemId, final Provider provider) {
        super(TicTac7xChargesImprovedConfig.moons_gear + "_" + configKey, itemId, provider);
    }

    @Override
    public String getChargesString(final int itemId) {
        final int charges = getCharges(itemId);

        switch (provider.config.combatTimeDegradableStyle()) {
            case PERCENTAGE:
                return charges * 100 / 3000 + "%";
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
        return TicTac7xChargesImprovedConfig.moons_gear;
    }
}
