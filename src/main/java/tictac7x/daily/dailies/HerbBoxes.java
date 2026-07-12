package tictac7x.daily.dailies;

import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.common.DailyInfobox;
import tictac7x.daily.common.Provider;
import tictac7x.daily.ids.ItemId;
import tictac7x.daily.ids.VarbitId;

public class HerbBoxes extends DailyInfobox {
    private final String tooltip = "Buy %d herb boxes from Nightmare Zone rewards shop.";

    public HerbBoxes(final Provider provider) {
        super(TicTac7xDailyTasksConfig.herb_boxes, ItemId.HERB_BOX, provider);
    }

    @Override
    public boolean isShowing() {
        return (
            provider.config.showHerbBoxes() &&
            provider.client.getVarbitValue(VarbitId.ACCOUNT_TYPE) == 0 && // 0 - REGULAR ACCOUNT
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
        return 15 - provider.client.getVarbitValue(VarbitId.NIGHTMAREZONE_HERB_BOXES_PURCHASED);
    }
}
