package tictac7x.daily.dailies;

import net.runelite.api.events.VarbitChanged;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;
import tictac7x.daily.ids.ItemId;
import tictac7x.daily.ids.VarbitId;

public class ExplorersRingAlchemy extends DailyInfobox {
    private final String tooltip = "You have %d alchemy uses left on your Explorers ring";

    public ExplorersRingAlchemy(final Provider provider) {
        super(TicTac7xDailyTasksConfig.explorers_ring_alchemy, ItemId.EXPLORERS_RING_EASY, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showExplorersRingAlchemy() &&
            varbitEqualsOne(VarbitId.LUMBRIDGE_DIARY_EASY_COMPLETE) &&
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
        return 30 - provider.client.getVarbitValue(VarbitId.EXPLORERS_RING_ALCHS_USED);
    }

    @Override
    public void onVarbitChanged(final VarbitChanged event) {
        switch (event.getVarbitId()) {
            case VarbitId.LUMBRIDGE_DIARY_EASY_COMPLETE:
            case VarbitId.LUMBRIDGE_DIARY_MEDIUM_COMPLETE:
            case VarbitId.LUMBRIDGE_DIARY_HARD_COMPLETE:
            case VarbitId.LUMBRIDGE_DIARY_ELITE_COMPLETE:
                setImage(provider.itemManager.getImage(getExplorerRingId()));
                provider.infoBoxManager.updateInfoBoxImage(this);
        }
    }

    private int getExplorerRingId() {
        final boolean easy   = varbitEqualsOne(VarbitId.LUMBRIDGE_DIARY_EASY_COMPLETE);
        final boolean medium = varbitEqualsOne(VarbitId.LUMBRIDGE_DIARY_MEDIUM_COMPLETE);
        final boolean hard   = varbitEqualsOne(VarbitId.LUMBRIDGE_DIARY_HARD_COMPLETE);
        final boolean elite  = varbitEqualsOne(VarbitId.LUMBRIDGE_DIARY_ELITE_COMPLETE);

        if (easy && medium && hard && elite) return ItemId.EXPLORERS_RING_ELITE;
        if (easy && medium && hard) return ItemId.EXPLORERS_RING_HARD;
        if (easy && medium) return ItemId.EXPLORERS_RING_MEDIUM;
        return ItemId.EXPLORERS_RING_EASY;
    }
}
