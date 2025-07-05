package tictac7x.daily.dailies;

import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.VarbitID;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class Dynamite extends DailyInfobox {
    private final String tooltip = "Claim %d dynamite from Thirus at Lovakengj";

    public Dynamite(final Provider provider) {
        super(TicTac7xDailyTasksConfig.dynamite, provider.itemManager.getImage(ItemID.LOVAKENGJ_DYNAMITE_FUSED), provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showDynamite() &&
            isDiaryCompleted(VarbitID.KOUREND_DIARY_EASY_COMPLETE) &&
            isDiaryCompleted(VarbitID.KOUREND_DIARY_MEDIUM_COMPLETE) &&
            !isDiaryCompleted(VarbitID.KOUREND_FREE_DYNAMITE)
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
        final boolean easy   = isDiaryCompleted(VarbitID.KOUREND_DIARY_EASY_COMPLETE);
        final boolean medium = isDiaryCompleted(VarbitID.KOUREND_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = isDiaryCompleted(VarbitID.KOUREND_DIARY_HARD_COMPLETE);
        final boolean elite  = isDiaryCompleted(VarbitID.KOUREND_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) return 80;
        if (easy && medium && hard) return 40;
        if (easy && medium) return 20;
        if (easy) return 0;
        return 0;
    }
}
