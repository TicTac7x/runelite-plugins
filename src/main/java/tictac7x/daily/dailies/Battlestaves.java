package tictac7x.daily.dailies;

import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;
import tictac7x.daily.ids.ItemId;
import tictac7x.daily.ids.VarbitId;

public class Battlestaves extends DailyInfobox {
    private final String tooltip = "Buy %d battlestaves from Zaff at Varrock for %d,000 coins";

    public Battlestaves(final Provider provider) {
        super(TicTac7xDailyTasksConfig.battlestaves, ItemId.BATTLESTAFF, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showBattlestaves() &&
            !varbitEqualsOne(VarbitId.VARROCK_ZAFF_BATTLESTAVES_COLLECTED)
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getRemainingBattlestavesAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getRemainingBattlestavesAmount(), getRemainingBattlestavesAmount() * 7);
    }

    private int getRemainingBattlestavesAmount() {
        final boolean easy   = varbitEqualsOne(VarbitId.VARROCK_DIARY_EASY_COMPLETE);
        final boolean medium = varbitEqualsOne(VarbitId.VARROCK_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = varbitEqualsOne(VarbitId.VARROCK_DIARY_HARD_COMPLETE);
        final boolean elite  = varbitEqualsOne(VarbitId.VARROCK_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) return 120;
        if (easy && medium && hard) return 60;
        if (easy && medium) return 30;
        if (easy) return 15;
        return 5;
    }
}
