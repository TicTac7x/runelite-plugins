package tictac7x.charges.item.triggers;

import net.runelite.api.*;

public class OnStatChanged extends TriggerBase {
    public Skill skill;

    public OnStatChanged(Skill skill) {
        this.skill = skill;
    }
}
