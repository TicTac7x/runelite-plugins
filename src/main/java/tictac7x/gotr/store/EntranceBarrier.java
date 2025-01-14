package tictac7x.gotr.store;

import net.runelite.api.MenuEntry;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;

public class EntranceBarrier {
    private final TicTac7xGotrImprovedConfig config;
    private final Player player;

    public EntranceBarrier(final TicTac7xGotrImprovedConfig config, final Player player) {
        this.config = config;
        this.player = player;
    }

    public void onMenuEntryAdded(final MenuEntry menuEntry) {
        if (config.preventEntranceBarrierQuickLeave() && player.inGotr() &&
            menuEntry.getTarget().contains("Barrier") &&
            menuEntry.getOption().contains("Quick-pass")
        ) {
            menuEntry.setOption("Deprioritized quick-pass");
        }
    }
}
