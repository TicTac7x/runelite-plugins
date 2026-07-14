package tictac7x.charges.items.moons;

import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;
import java.util.regex.*;

public class _MoonItem extends ChargedItem {
    public _MoonItem(String checkName, int itemId, Provider provider) {
        super(TicTac7xChargesImprovedConfig.moons_gear + "_" + checkName.toLowerCase().replaceAll("\\s", "_"), itemId, provider);

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your " + Pattern.quote(checkName) + "( only)? has (?<charges>.+) charges? (remaining|left).").setDynamicallyCharges(),

            // In combat.
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
                return charges * 100 / 3000 + "%";
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
        return TicTac7xChargesImprovedConfig.moons_gear;
    }
}
