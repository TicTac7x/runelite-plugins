package tictac7x.daily.dailies;

import net.runelite.api.ItemID;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.Varbits;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class BucketsOfSand extends DailyInfobox {
    private final int AMOUNT_BUCKETS_OF_SAND = 84;
    private final String tooltip = "Collect %d buckets of sand from Bert at Yanille";

    public BucketsOfSand(final Provider provider) {
        super(TicTac7xDailyTasksConfig.buckets_of_sand, provider.itemManager.getImage(ItemID.BUCKET_OF_SAND), provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showBucketsOfSand() &&
            provider.client.getVarbitValue(Varbits.ACCOUNT_TYPE) != 2 && // 2 - ULTIMATE IRONMAN
            Quest.THE_HAND_IN_THE_SAND.getState(provider.client) == QuestState.FINISHED &&
            !isDiaryCompleted(Varbits.DAILY_SAND_COLLECTED)
        );
    }

    @Override
    public String getText() {
        return String.valueOf(AMOUNT_BUCKETS_OF_SAND);
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, AMOUNT_BUCKETS_OF_SAND);
    }
}
