package tictac7x.daily.dailies;

import net.runelite.api.gameval.*;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class BucketsOfSlime extends DailyInfobox {
    private final String tooltip = "Exchange %d bones to buckets of slime and bonemeal from Robin at Porty Phasmatys";

    public BucketsOfSlime(final Provider provider) {
        super(TicTac7xDailyTasksConfig.buckets_of_slime, ItemID.BUCKET_ECTOPLASM, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showBucketsOfSlime() &&
            varbitEqualsOne(VarbitID.MORYTANIA_DIARY_EASY_COMPLETE) &&
            varbitEqualsOne(VarbitID.MORYTANIA_DIARY_MEDIUM_COMPLETE) &&
            getRemainingBucketsOfSlimeAmount() > 0
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getRemainingBucketsOfSlimeAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getRemainingBucketsOfSlimeAmount());
    }

    private int getRemainingBucketsOfSlimeAmount() {
        int buckets_of_slime = 0;

        final boolean easy   = varbitEqualsOne(VarbitID.MORYTANIA_DIARY_EASY_COMPLETE);
        final boolean medium = varbitEqualsOne(VarbitID.MORYTANIA_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = varbitEqualsOne(VarbitID.MORYTANIA_DIARY_HARD_COMPLETE);
        final boolean elite  = varbitEqualsOne(VarbitID.MORYTANIA_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) { buckets_of_slime = 39; } else
        if (easy && medium && hard) { buckets_of_slime = 26; } else
        if (easy && medium) { buckets_of_slime = 13; } else
        if (easy) { buckets_of_slime = 0; }

        return buckets_of_slime - provider.client.getVarbitValue(VarbitID.MORYTANIA_SLIME_CLAIMED);
    }
}
