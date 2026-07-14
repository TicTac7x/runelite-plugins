package tictac7x.daily.dailies;

import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.*;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class KingdomOfMiscellania extends DailyInfobox {
    private final String percentageFormat = "%d%%";
    private final ZoneId timezone = ZoneId.of("UTC");
    private final String tooltip = "You need to work harder to increase your kingdom of Miscellania favor: " + percentageFormat;

    private final int FAVOR_MAX = 127;
    private final double FAVOR_LOST_MODIFIER_WITHOUT_ROYAL_TROUBLE = 0.975;
    private final double FAVOR_LOST_MODIFIER_WITH_ROYAL_TROUBLE = 0.99;
    private final int[] MISCELLANIA_REGIONS = new int[]{10044, 10300};

    public KingdomOfMiscellania(final Provider provider) {
        super(TicTac7xDailyTasksConfig.kingdom_of_miscellania_percentage, ItemID.CASKET, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showKingdomOfMiscellania() &&
            Quest.THRONE_OF_MISCELLANIA.getState(provider.client) == QuestState.FINISHED &&
            getFavorPercentage() <= provider.config.showKingdomOfMiscellaniaFavor()
        );
    }

    @Override
    public String getText() {
        return String.format(percentageFormat, getFavorPercentage());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getFavorPercentage());
    }

    @Override
    public void onVarbitChanged(final VarbitChanged event) {
        if (event.getVarbitId() != VarbitID.MISC_APPROVAL) return;
        if (provider.client.getLocalPlayer() == null) return;
        if (Arrays.stream(MISCELLANIA_REGIONS).noneMatch(region -> region == provider.client.getLocalPlayer().getWorldLocation().getRegionID())) return;

        provider.configManager.setConfiguration(TicTac7xDailyTasksConfig.group, TicTac7xDailyTasksConfig.kingdom_of_miscellania_favor_date, LocalDate.now(timezone).toString());
        provider.configManager.setConfiguration(TicTac7xDailyTasksConfig.group, TicTac7xDailyTasksConfig.kingdom_of_miscellania_favor, event.getValue());
    }

    private int getFavorPercentage() {
        try {
            final LocalDate now = LocalDate.now(timezone);
            final LocalDate visited = LocalDate.parse(provider.config.getKingdomOfMiscellaniaFavorDate());
            final long days = Math.abs(ChronoUnit.DAYS.between(now, visited));

            int favor = provider.config.getKingdomOfMiscellaniaFavor();
            for (int i = 0; i < days; i++) {
                favor = (int) Math.round(favor *
                    (Quest.ROYAL_TROUBLE.getState(provider.client) == QuestState.FINISHED
                        ? FAVOR_LOST_MODIFIER_WITH_ROYAL_TROUBLE
                        : FAVOR_LOST_MODIFIER_WITHOUT_ROYAL_TROUBLE)
                );
            }

            return favor * 100 / FAVOR_MAX;
        } catch (final Exception ignored) {
            return 0;
        }
    }
}
