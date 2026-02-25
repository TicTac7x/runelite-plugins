package tictac7x.charges.items.moons;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.store.Provider;

import java.util.List;
import java.util.regex.Pattern;

public class _MoonItem extends ChargedItem {
    public _MoonItem(final String checkName, final int itemId, final Provider provider) {
        super(TicTac7xChargesImprovedConfig.moons_gear + "_" + checkName.toLowerCase().replaceAll("\\s", "_"), itemId, provider);

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your " + Pattern.quote(checkName) + "( only)? has (?<charges>.+) charges? (remaining|left).").setDynamicallyCharges(),

            // In combat.
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
