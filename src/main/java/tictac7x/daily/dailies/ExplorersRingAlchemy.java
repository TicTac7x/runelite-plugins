package tictac7x.daily.dailies;

import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.VarbitID;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class ExplorersRingAlchemy extends DailyInfobox {
    private final String tooltip = "You have %d alchemy uses left on your Explorers ring";

    public ExplorersRingAlchemy(final Provider provider) {
        super(TicTac7xDailyTasksConfig.explorers_ring_alchemy, provider.itemManager.getImage(ItemID.LUMBRIDGE_RING_EASY), provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showExplorersRingAlchemy() &&
            isDiaryCompleted(VarbitID.LUMBRIDGE_DIARY_EASY_COMPLETE) &&
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
        return 30 - provider.client.getVarbitValue(VarbitID.CHARGES_ALCHEMISTS_AMULET_QUANTITY);
    }

    @Override
    public void onVarbitChanged(final VarbitChanged event) {
        switch (event.getVarbitId()) {
            case VarbitID.LUMBRIDGE_DIARY_EASY_COMPLETE:
            case VarbitID.LUMBRIDGE_DIARY_MEDIUM_COMPLETE:
            case VarbitID.LUMBRIDGE_DIARY_HARD_COMPLETE:
            case VarbitID.LUMBRIDGE_DIARY_ELITE_COMPLETE:
                setImage(provider.itemManager.getImage(getExplorerRingId()));
        }
    }

    private int getExplorerRingId() {
        final boolean easy   = provider.client.getVarbitValue(VarbitID.LUMBRIDGE_DIARY_EASY_COMPLETE) == 1;
        final boolean medium = provider.client.getVarbitValue(VarbitID.LUMBRIDGE_DIARY_MEDIUM_COMPLETE) == 1;
        final boolean hard   = provider.client.getVarbitValue(VarbitID.LUMBRIDGE_DIARY_HARD_COMPLETE) == 1;
        final boolean elite  = provider.client.getVarbitValue(VarbitID.LUMBRIDGE_DIARY_ELITE_COMPLETE) == 1;

        if (easy && medium && hard && elite) return ItemID.LUMBRIDGE_RING_ELITE;
        if (easy && medium && hard) return ItemID.LUMBRIDGE_RING_HARD;
        if (easy && medium) return ItemID.LUMBRIDGE_RING_MEDIUM;
        return ItemID.LUMBRIDGE_RING_EASY;
    }
}
