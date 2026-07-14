package tictac7x.daily.dailies;

import net.runelite.api.gameval.*;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;

public class HerbBoxes extends DailyInfobox {
    private final String tooltip = "Buy %d herb boxes from Nightmare Zone rewards shop.";

    public HerbBoxes(final Provider provider) {
        super(TicTac7xDailyTasksConfig.herb_boxes, ItemID.NZONE_HERBBOX, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showHerbBoxes() &&
            provider.client.getVarbitValue(VarbitID.IRONMAN) == 0 && // 0 - REGULAR ACCOUNT
            getHerbBoxesAmount() > 0
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getHerbBoxesAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getHerbBoxesAmount());
    }

    private int getHerbBoxesAmount() {
        return 15 - provider.client.getVarbitValue(VarbitID.NZONE_HERBBOXES_PURCHASED);
    }
}
