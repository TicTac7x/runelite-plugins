package tictac7x.daily.dailies;

import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;
import tictac7x.daily.ids.ItemId;
import tictac7x.daily.ids.VarbitId;

import java.util.Random;

public class RandomRunes extends DailyInfobox {
    private final String tooltip = "Claim %d random runes from Lundail at Mage Arena bank";

    public RandomRunes(final Provider provider) {
        super(TicTac7xDailyTasksConfig.random_runes, getRandomRuneId(), provider);
    }

    static private int getRandomRuneId() {
        final int[] rune_ids = new int[]{
            ItemId.MIND_RUNE,
            ItemId.BODY_RUNE,
            ItemId.COSMIC_RUNE,
            ItemId.NATURE_RUNE,
            ItemId.LAW_RUNE,
            ItemId.CHAOS_RUNE,
            ItemId.DEATH_RUNE,
        };
        final int random = new Random().nextInt(rune_ids.length);
        return rune_ids[random];
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showRandomRunes() &&
            varbitEqualsOne(VarbitId.WILDERNESS_DIARY_EASY_COMPLETE) &&
            !varbitEqualsOne(VarbitId.WILDERNESS_LUNDAIL_RUNES_COLLECTED)
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getRandomRunesAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getRandomRunesAmount());
    }

    private int getRandomRunesAmount() {
        final boolean easy   = varbitEqualsOne(VarbitId.WILDERNESS_DIARY_EASY_COMPLETE);
        final boolean medium = varbitEqualsOne(VarbitId.WILDERNESS_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = varbitEqualsOne(VarbitId.WILDERNESS_DIARY_HARD_COMPLETE);
        final boolean elite  = varbitEqualsOne(VarbitId.WILDERNESS_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) return 200;
        if (easy && medium && hard) return 120;
        if (easy && medium) return 80;
        if (easy) return 40;
        return 0;
    }
}
