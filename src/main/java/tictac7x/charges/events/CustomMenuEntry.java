package tictac7x.charges.events;

import net.runelite.api.*;
import net.runelite.api.widgets.*;

import javax.annotation.*;
import java.util.function.Consumer;

public class CustomMenuEntry implements MenuEntry
{
    private final MenuEntry menuEntry;
    private final String option;

    public CustomMenuEntry(MenuEntry menuEntry, String option)
    {
        this.menuEntry = menuEntry;
        this.option = option;
    }

    @Override
    public String getOption()
    {
        return option;
    }

    @Override
    public MenuEntry setOption(String option)
    {
        menuEntry.setOption(option);
        return this;
    }

    @Override
    public String getTarget()
    {
        return menuEntry.getTarget();
    }

    @Override
    public MenuEntry setTarget(String target)
    {
        menuEntry.setTarget(target);
        return this;
    }

    @Override
    public int getIdentifier()
    {
        return menuEntry.getIdentifier();
    }

    @Override
    public MenuEntry setIdentifier(int identifier)
    {
        menuEntry.setIdentifier(identifier);
        return this;
    }

    @Override
    public MenuAction getType()
    {
        return menuEntry.getType();
    }

    @Override
    public MenuEntry setType(MenuAction type)
    {
        menuEntry.setType(type);
        return this;
    }

    @Override
    public int getParam0()
    {
        return menuEntry.getParam0();
    }

    @Override
    public MenuEntry setParam0(int param0)
    {
        menuEntry.setParam0(param0);
        return this;
    }

    @Override
    public int getParam1()
    {
        return menuEntry.getParam1();
    }

    @Override
    public MenuEntry setParam1(int param1)
    {
        menuEntry.setParam1(param1);
        return this;
    }

    @Override
    public boolean isForceLeftClick()
    {
        return menuEntry.isForceLeftClick();
    }

    @Override
    public MenuEntry setForceLeftClick(boolean forceLeftClick)
    {
        menuEntry.setForceLeftClick(forceLeftClick);
        return this;
    }

    @Override
    public int getWorldViewId()
    {
        return menuEntry.getWorldViewId();
    }

    @Override
    public MenuEntry setWorldViewId(int worldViewId)
    {
        menuEntry.setWorldViewId(worldViewId);
        return this;
    }

    @Override
    public boolean isDeprioritized()
    {
        return menuEntry.isDeprioritized();
    }

    @Override
    public MenuEntry setDeprioritized(boolean deprioritized)
    {
        menuEntry.setDeprioritized(deprioritized);
        return this;
    }

    @Override
    public MenuEntry onClick(Consumer<MenuEntry> callback)
    {
        menuEntry.onClick(callback);
        return this;
    }

    @Override
    public Consumer<MenuEntry> onClick()
    {
        return menuEntry.onClick();
    }

    @Override
    public boolean isItemOp()
    {
        return menuEntry.isItemOp();
    }

    @Override
    public int getItemOp()
    {
        return menuEntry.getItemOp();
    }

    @Override
    public int getItemId()
    {
        return menuEntry.getItemId();
    }

    @Override
    public MenuEntry setItemId(int itemId)
    {
        menuEntry.setItemId(itemId);
        return this;
    }

    @Nullable
    @Override
    public Widget getWidget()
    {
        return menuEntry.getWidget();
    }

    @Nullable
    @Override
    public NPC getNpc()
    {
        return menuEntry.getNpc();
    }

    @Nullable
    @Override
    public Player getPlayer()
    {
        return menuEntry.getPlayer();
    }

    @Nullable
    @Override
    public Actor getActor()
    {
        return menuEntry.getActor();
    }

    @Nullable
    @Override
    public Menu getSubMenu()
    {
        return menuEntry.getSubMenu();
    }

    @Nonnull
    @Override
    public Menu createSubMenu()
    {
        return menuEntry.createSubMenu();
    }

    @Override
    public void deleteSubMenu()
    {
        menuEntry.deleteSubMenu();
    }
}
