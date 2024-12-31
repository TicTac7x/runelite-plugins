package tictac7x.charges.item.triggers;

import net.runelite.api.Skill;

public class OnStatChanged extends TriggerBase {
    public final Skill skill;

    public OnStatChanged(final Skill skill) {
        this.skill = skill;
    }
}
