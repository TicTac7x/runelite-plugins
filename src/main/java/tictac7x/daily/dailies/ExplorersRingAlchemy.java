package tictac7x.daily.dailies;

import net.runelite.api.ItemID;
import net.runelite.api.Varbits;
import net.runelite.api.events.VarbitChanged;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class ExplorersRingAlchemy extends DailyInfobox {
    private final String tooltip = "You have %d alchemy uses left on your Explorers ring";

    public ExplorersRingAlchemy(final Provider provider) {
        super(TicTac7xDailyTasksConfig.explorers_ring_alchemy, provider.itemManager.getImage(ItemID.EXPLORERS_RING_1), provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showExplorersRingAlchemy() &&
            isDiaryCompleted(Varbits.DIARY_LUMBRIDGE_EASY) &&
            getRemainingAlchemyUses() > 0
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getRemainingAlchemyUses());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getRemainingAlchemyUses());
    }

    private int getRemainingAlchemyUses() {
        return 30 - provider.client.getVarbitValue(Varbits.EXPLORER_RING_ALCHS);
    }

    @Override
    public void onVarbitChanged(final VarbitChanged event) {
        switch (event.getVarbitId()) {
            case Varbits.DIARY_LUMBRIDGE_EASY:
            case Varbits.DIARY_LUMBRIDGE_MEDIUM:
            case Varbits.DIARY_LUMBRIDGE_HARD:
            case Varbits.DIARY_LUMBRIDGE_ELITE:
                setImage(provider.itemManager.getImage(getExplorerRingId()));
                provider.infoBoxManager.updateInfoBoxImage(this);
        }
    }

    private int getExplorerRingId() {
        final boolean easy   = provider.client.getVarbitValue(Varbits.DIARY_LUMBRIDGE_EASY) == 1;
        final boolean medium = provider.client.getVarbitValue(Varbits.DIARY_LUMBRIDGE_MEDIUM) == 1;
        final boolean hard   = provider.client.getVarbitValue(Varbits.DIARY_LUMBRIDGE_HARD) == 1;
        final boolean elite  = provider.client.getVarbitValue(Varbits.DIARY_LUMBRIDGE_ELITE) == 1;

        if (easy && medium && hard && elite) return ItemID.EXPLORERS_RING_4;
        if (easy && medium && hard) return ItemID.EXPLORERS_RING_3;
        if (easy && medium) return ItemID.EXPLORERS_RING_2;
        return ItemID.EXPLORERS_RING_1;
    }
}
