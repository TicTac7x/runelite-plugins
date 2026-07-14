package tictac7x.daily.dailies;

import net.runelite.api.gameval.*;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class PureEssence extends DailyInfobox {
    private final String tooltip = "Collect %d pure essence from Wizard Cromperty at East-Ardougne";

    public PureEssence(final Provider provider) {
        super(TicTac7xDailyTasksConfig.pure_essence, ItemID.BLANKRUNE_HIGH, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showPureEssence() &&
            varbitEqualsOne(VarbitID.ARDOUGNE_DIARY_EASY_COMPLETE) &&
            varbitEqualsOne(VarbitID.ARDOUGNE_DIARY_MEDIUM_COMPLETE) &&
            !varbitEqualsOne(VarbitID.ARDOUGNE_FREE_ESSENCE)
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getPureEssenceAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getPureEssenceAmount());
    }

    private int getPureEssenceAmount() {
        final boolean easy   = varbitEqualsOne(VarbitID.ARDOUGNE_DIARY_EASY_COMPLETE);
        final boolean medium = varbitEqualsOne(VarbitID.ARDOUGNE_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = varbitEqualsOne(VarbitID.ARDOUGNE_DIARY_HARD_COMPLETE);
        final boolean elite  = varbitEqualsOne(VarbitID.ARDOUGNE_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) return 250;
        if (easy && medium && hard) return 150;
        if (easy && medium) return 100;
        if (easy) return 0;
        return 0;
    }
}
