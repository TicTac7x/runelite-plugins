package tictac7x.charges.events;

import net.runelite.api.*;
import net.runelite.api.events.*;
import tictac7x.charges.store.*;

public class CustomStatChanged {
    public Skill skill;
    public int level;
    public int xpTotal;
    public int xpDrop;

    public CustomStatChanged(Skill skill, int level, int xpTotal, int xpDrop) {
        this.skill = skill;
        this.level = level;
        this.xpTotal = xpTotal;
        this.xpDrop = xpDrop;
    }

    @Override
    public String toString() {
        return "STAT CHANGED | " + skill.getName() +
			", level: " + level +
			", total xp: " + xpTotal +
            ", xp drop: " + xpDrop;
    }
}
