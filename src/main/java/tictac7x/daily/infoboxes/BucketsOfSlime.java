package tictac7x.daily.infoboxes;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Varbits;
import net.runelite.client.game.ItemManager;
import tictac7x.daily.DailyConfig;
import tictac7x.daily.DailyInfobox;
import tictac7x.daily.TicTac7xDailyPlugin;

public class BucketsOfSlime extends DailyInfobox {
    private final String tooltip = "Exchange %d bones to buckets of slime and bonemeal from Robin at Porty Phasmatys";

    public BucketsOfSlime(final Client client, final DailyConfig config, final ItemManager items, final TicTac7xDailyPlugin plugin) {
        super(DailyConfig.buckets_of_slime, items.getImage(ItemID.BUCKET_OF_SLIME), client, config, plugin);
    }

    @Override
    public boolean isShowing() {
        return (
            config.showBucketsOfSlime() &&
            plugin.isCompleted(Varbits.DIARY_MORYTANIA_EASY) &&
            plugin.isCompleted(Varbits.DIARY_MORYTANIA_MEDIUM) &&
            getBucketsOfSlimeAmount() > 0
        );
    }

    @Override
    public String getText() {
        return String.valueOf(getBucketsOfSlimeAmount());
    }

    @Override
    public String getTooltip() {
        return String.format(tooltip, getBucketsOfSlimeAmount());
    }

    private int getBucketsOfSlimeAmount() {
        int buckets_of_slime = 0;

        final boolean easy   = plugin.isCompleted(Varbits.DIARY_MORYTANIA_EASY);
        final boolean medium = plugin.isCompleted(Varbits.DIARY_MORYTANIA_MEDIUM);
        final boolean hard   = plugin.isCompleted(Varbits.DIARY_MORYTANIA_HARD);
        final boolean elite  = plugin.isCompleted(Varbits.DIARY_MORYTANIA_ELITE);

        if (easy && medium && hard && elite) { buckets_of_slime = 39; } else
        if (easy && medium && hard) { buckets_of_slime = 26; } else
        if (easy && medium) { buckets_of_slime = 13; } else
        if (easy) { buckets_of_slime = 0; }

        return buckets_of_slime - client.getVarbitValue(Varbits.DAILY_BONEMEAL_STATE);
    }
}
