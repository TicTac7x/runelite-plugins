package tictac7x.daily.dailies;

import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;
import tictac7x.daily.ids.ItemId;
import tictac7x.daily.ids.VarbitId;

public class BowStrings extends DailyInfobox {
    private final String tooltip = "Exchange flax to %d bow strings from the Flax Keeper at Seers Village";

    public BowStrings(final Provider provider) {
        super(TicTac7xDailyTasksConfig.bow_strings, ItemId.BOW_STRING, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showBowStrings() &&
            varbitEqualsOne(VarbitId.KANDARIN_DIARY_EASY_COMPLETE) &&
            !varbitEqualsOne(VarbitId.KANDARIN_FLAX_COLLECTED)
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
        final boolean easy   = varbitEqualsOne(VarbitId.KANDARIN_DIARY_EASY_COMPLETE);
        final boolean medium = varbitEqualsOne(VarbitId.KANDARIN_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = varbitEqualsOne(VarbitId.KANDARIN_DIARY_HARD_COMPLETE);
        final boolean elite  = varbitEqualsOne(VarbitId.KANDARIN_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) return 250;
        if (easy && medium && hard) return 120;
        if (easy && medium) return 60;
        if (easy) return 30;
        return 0;
    }
}
