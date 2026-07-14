package tictac7x.charges.events;

import net.runelite.api.*;
import net.runelite.api.events.*;
import tictac7x.charges.store.*;

public class CustomStatChanged {
    public Skill skill;
    public int level;
    public int xp;
    public int xpDrop;

    public CustomStatChanged(StatChanged event, Store store) {
        this.skill = event.getSkill();
        this.level = event.getLevel();
        this.xp = event.getXp();
        this.xpDrop = store.getSkillXp(event.getSkill()).isPresent()
            ? event.getXp() - store.getSkillXp(event.getSkill()).get()
            : 0;
    }

    @Override
    public String toString() {
        return "STAT CHANGED | " + skill.getName() +
			", level: " + level +
			", total xp: " + xp +
            ", xp drop: " + xpDrop;
    }
}
