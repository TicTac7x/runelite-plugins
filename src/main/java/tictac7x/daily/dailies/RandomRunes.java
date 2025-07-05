package tictac7x.daily.dailies;

import net.runelite.api.ItemID;
import net.runelite.api.Varbits;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

import java.util.Random;

public class RandomRunes extends DailyInfobox {
    private final String tooltip = "Claim %d random runes from Lundail at Mage Arena bank";

    public RandomRunes(final Provider provider) {
        super(TicTac7xDailyTasksConfig.random_runes, provider.itemManager.getImage(getRandomRuneId()), provider);
    }

    static private int getRandomRuneId() {
        final int[] rune_ids = new int[]{
            ItemID.FIRE_RUNE,
            ItemID.WATER_RUNE,
            ItemID.AIR_RUNE,
            ItemID.EARTH_RUNE,
            ItemID.MIND_RUNE,
            ItemID.BODY_RUNE,
            ItemID.NATURE_RUNE,
            ItemID.CHAOS_RUNE,
            ItemID.LAW_RUNE,
            ItemID.COSMIC_RUNE,
            ItemID.DEATH_RUNE
        };
        final int random = new Random().nextInt(rune_ids.length);
        return rune_ids[random];
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showRandomRunes() &&
            isDiaryCompleted(Varbits.DIARY_WILDERNESS_EASY) &&
            !isDiaryCompleted(Varbits.DAILY_RUNES_COLLECTED)
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
        final boolean easy   = isDiaryCompleted(Varbits.DIARY_WILDERNESS_EASY);
        final boolean medium = isDiaryCompleted(Varbits.DIARY_WILDERNESS_MEDIUM);
        final boolean hard   = isDiaryCompleted(Varbits.DIARY_WILDERNESS_HARD);
        final boolean elite  = isDiaryCompleted(Varbits.DIARY_WILDERNESS_ELITE);

        if (easy && medium && hard && elite) return 200;
        if (easy && medium && hard) return 120;
        if (easy && medium) return 80;
        if (easy) return 40;
        return 0;
    }
}
