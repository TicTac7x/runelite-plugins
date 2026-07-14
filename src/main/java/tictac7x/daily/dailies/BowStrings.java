package tictac7x.daily.dailies;

import net.runelite.api.gameval.*;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class BowStrings extends DailyInfobox {
    private final String tooltip = "Exchange flax to %d bow strings from the Flax Keeper at Seers Village";

    public BowStrings(final Provider provider) {
        super(TicTac7xDailyTasksConfig.bow_strings, ItemID.BOW_STRING, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showBowStrings() &&
            varbitEqualsOne(VarbitID.KANDARIN_DIARY_EASY_COMPLETE) &&
            provider.client.getVarbitValue(VarbitID.SEERS_FREE_FLAX) == 0
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getRemainingBowStringsAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getRemainingBowStringsAmount());
    }

    private int getRemainingBowStringsAmount() {
        final boolean easy   = varbitEqualsOne(VarbitID.KANDARIN_DIARY_EASY_COMPLETE);
        final boolean medium = varbitEqualsOne(VarbitID.KANDARIN_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = varbitEqualsOne(VarbitID.KANDARIN_DIARY_HARD_COMPLETE);
        final boolean elite  = varbitEqualsOne(VarbitID.KANDARIN_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) return 250;
        if (easy && medium && hard) return 120;
        if (easy && medium) return 60;
        if (easy) return 30;
        return 0;
    }
}
