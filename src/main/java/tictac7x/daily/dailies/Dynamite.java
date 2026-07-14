package tictac7x.daily.dailies;

import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;
import tictac7x.daily.ids.ItemId;
import tictac7x.daily.ids.VarbitId;

public class Dynamite extends DailyInfobox {
    private final String tooltip = "Claim %d dynamite from Thirus at Lovakengj";

    public Dynamite(final Provider provider) {
        super(TicTac7xDailyTasksConfig.dynamite, ItemId.DYNAMITE, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showDynamite() &&
            varbitEqualsOne(VarbitId.KOUREND_DIARY_EASY_COMPLETE) &&
            varbitEqualsOne(VarbitId.KOUREND_DIARY_MEDIUM_COMPLETE) &&
            !varbitEqualsOne(VarbitId.KOUREND_DYNAMITE_COLLECTED)
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getDynamiteAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getDynamiteAmount());
    }

    private int getDynamiteAmount() {
        final boolean easy   = varbitEqualsOne(VarbitId.KOUREND_DIARY_EASY_COMPLETE);
        final boolean medium = varbitEqualsOne(VarbitId.KOUREND_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = varbitEqualsOne(VarbitId.KOUREND_DIARY_HARD_COMPLETE);
        final boolean elite  = varbitEqualsOne(VarbitId.KOUREND_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) return 80;
        if (easy && medium && hard) return 40;
        if (easy && medium) return 20;
        if (easy) return 0;
        return 0;
    }
}
