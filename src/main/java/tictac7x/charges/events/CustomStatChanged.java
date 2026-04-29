package tictac7x.charges.events;

import net.runelite.api.Skill;
import net.runelite.api.events.StatChanged;
import tictac7x.charges.store.Store;

public class CustomStatChanged {
    public final Skill skill;
    public final int level;
    public final int xp;
    public final int xpDrop;

    public CustomStatChanged(final StatChanged event, final Store store) {
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
