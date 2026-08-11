package tictac7x.charges.item.triggers;

import net.runelite.api.*;
import tictac7x.charges.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;

import java.util.*;

public class OnConfigMenuOpened extends OnMenuOpened {
    public OnConfigMenuOpened(Provider provider, String configKey) {
        menuConsumer(menuOpened -> {
            Menu menu = provider.client.getMenu();

            MenuEntry chargesMenuEntry = menu.createMenuEntry(2);
            chargesMenuEntry
                .setOption("Charges")
                .setTarget(menuOpened.getFirstEntry().getTarget())
                .setType(MenuAction.RUNELITE);

            Menu chargesSubMenu = chargesMenuEntry.createSubMenu();
            MenuEntry totalMenuEntry = chargesSubMenu.createMenuEntry(-1);
            totalMenuEntry
                .setOption("Show-total")
                .setTarget("")
                .setType(MenuAction.RUNELITE)
                .onClick((menuEntry) -> provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, configKey + TicTac7xChargesImprovedConfig._display, StorageDisplay.TOTAL));
            MenuEntry individualMenuEntry = chargesSubMenu.createMenuEntry(-1);
            individualMenuEntry
                .setOption("Show-individual")
                .setTarget("")
                .setType(MenuAction.RUNELITE)
                .onClick((menuEntry) -> provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, configKey + TicTac7xChargesImprovedConfig._display, StorageDisplay.INDIVIDUAL));

            List<MenuEntry> menuEntries = new ArrayList<>(Arrays.asList(menuOpened.getMenuEntries()));
            menuEntries.add(chargesMenuEntry);
            menuOpened.setMenuEntries(menuEntries.toArray(new MenuEntry[0]));
        });
    }
}
