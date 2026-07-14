package tictac7x.daily.dailies;

import net.runelite.api.gameval.*;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class ImplingJars extends DailyInfobox {
    private final String tooltip = "Buy %d impling jars from Elnock Inquisitor at Puro-Puro";

    public ImplingJars(final Provider provider) {
        super(TicTac7xDailyTasksConfig.impling_jars, ItemID.II_IMPLING_JAR, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showImplingJars() &&
            provider.client.getVarbitValue(VarbitID.II_DAILY_JARS_PURCHASED) < 10
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getRemainingImplingJarsAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getRemainingImplingJarsAmount());
    }

    private int getRemainingImplingJarsAmount() {
        return 10 - provider.client.getVarbitValue(VarbitID.II_DAILY_JARS_PURCHASED);
    }
}
