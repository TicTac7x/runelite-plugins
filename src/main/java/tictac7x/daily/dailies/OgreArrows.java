package tictac7x.daily.dailies;

import net.runelite.api.ItemID;
import net.runelite.api.Varbits;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class OgreArrows extends DailyInfobox {
    private final String tooltip = "Collect %d ogre arrows from Rantz near Feldip Hills cave";

    public OgreArrows(final Provider provider) {
        super(TicTac7xDailyTasksConfig.ogre_arrows, provider.itemManager.getImage(ItemID.OGRE_ARROW, 1000, false), provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showOgreArrows() &&
            isDiaryCompleted(Varbits.DIARY_WESTERN_EASY) &&
            !isDiaryCompleted(Varbits.DAILY_ARROWS_STATE)
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
        final boolean easy   = isDiaryCompleted(Varbits.DIARY_WESTERN_EASY);
        final boolean medium = isDiaryCompleted(Varbits.DIARY_WESTERN_MEDIUM);
        final boolean hard   = isDiaryCompleted(Varbits.DIARY_WESTERN_HARD);
        final boolean elite  = isDiaryCompleted(Varbits.DIARY_WESTERN_ELITE);

        if (easy && medium && hard && elite) return 150;
        if (easy && medium && hard) return 100;
        if (easy && medium) return 50;
        if (easy) return 25;
        return 0;
    }
}
