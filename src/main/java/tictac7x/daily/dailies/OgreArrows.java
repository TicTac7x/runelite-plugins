package tictac7x.daily.dailies;

import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;
import tictac7x.daily.ids.ItemId;
import tictac7x.daily.ids.VarbitId;

public class OgreArrows extends DailyInfobox {
    private final String tooltip = "Collect %d ogre arrows from Rantz near Feldip Hills cave";

    public OgreArrows(final Provider provider) {
        super(TicTac7xDailyTasksConfig.ogre_arrows, ItemId.OGRE_ARROW, 1000, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showOgreArrows() &&
            varbitEqualsOne(VarbitId.WESTERN_DIARY_EASY_COMPLETE) &&
            !varbitEqualsOne(VarbitId.WESTERN_RANTZ_OGRE_ARROWS_COLLECTED)
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getOgreArrowsAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getOgreArrowsAmount());
    }

    private int getOgreArrowsAmount() {
        final boolean easy   = varbitEqualsOne(VarbitId.WESTERN_DIARY_EASY_COMPLETE);
        final boolean medium = varbitEqualsOne(VarbitId.WESTERN_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = varbitEqualsOne(VarbitId.WESTERN_DIARY_HARD_COMPLETE);
        final boolean elite  = varbitEqualsOne(VarbitId.WESTERN_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) return 150;
        if (easy && medium && hard) return 100;
        if (easy && medium) return 50;
        if (easy) return 25;
        return 0;
    }
}
