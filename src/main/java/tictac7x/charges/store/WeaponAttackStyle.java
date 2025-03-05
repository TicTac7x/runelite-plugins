package tictac7x.charges.store;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.EnumID;
import net.runelite.api.ParamID;
import net.runelite.api.StructComposition;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;

/**
 * Get a generalised weapon style (melee/magic/ranged) from the current attack style.
 *
 * @see net.runelite.client.plugins.attackstyles.AttackStylesPlugin
 */
@Slf4j
public class WeaponAttackStyle {
    private final Client client;
    private int[] weaponStyleStructs;

    public WeaponAttackStyle(Client client) {
        this.client = client;
    }

    public CombatStyle getCombatStyle() {
        final int currentAttackStyleVarbit = client.getVarpValue(VarPlayer.ATTACK_STYLE);
        final int currentEquippedWeaponTypeVarbit = client.getVarbitValue(Varbits.EQUIPPED_WEAPON_TYPE);
        int weaponStyleEnum = client.getEnum(EnumID.WEAPON_STYLES).getIntValue(currentEquippedWeaponTypeVarbit);

        weaponStyleStructs = client.getEnum(weaponStyleEnum).getIntVals();

        AttackStyle attackStyle = getAttackStyle(currentAttackStyleVarbit);
        CombatStyle selectedWeaponStyle = getWeaponFromAttackStyle(attackStyle);

        log.debug("Weapon style: {}", selectedWeaponStyle);

        return selectedWeaponStyle;
    }

    private AttackStyle getAttackStyle(int attackStyleVarbit) {
        // Get selected weapon attack style
        StructComposition attackStyleStruct = client.getStructComposition(weaponStyleStructs[attackStyleVarbit]);
        String attackStyleName = attackStyleStruct.getStringValue(ParamID.ATTACK_STYLE_NAME);

        // Get selected attack style
        return AttackStyle.valueOf(attackStyleName.toUpperCase());
    }

    private CombatStyle getWeaponFromAttackStyle(AttackStyle attackStyle) {
        switch (attackStyle) {
            case ACCURATE:
            case AGGRESSIVE:
            case CONTROLLED:
                return CombatStyle.MELEE;
            case RANGING:
            case LONGRANGE:
                return CombatStyle.RANGED;
            case CASTING:
            case DEFENSIVE_CASTING:
                return CombatStyle.MAGIC;
            // "Defensive" is shared between melee and magic
            // We can look at the first attack style to determine which one is in use
            case DEFENSIVE:
                AttackStyle firstAttackStyle = getAttackStyle(0);
                log.debug("Defensive, maybe melee or magic: {}", firstAttackStyle);

                return firstAttackStyle != AttackStyle.DEFENSIVE
                    ? getWeaponFromAttackStyle(firstAttackStyle)
                    : CombatStyle.UNKNOWN;
            default:
                return CombatStyle.UNKNOWN;
        }
    }

    @Getter
    enum AttackStyle {
        ACCURATE("Accurate"),
        AGGRESSIVE("Aggressive"),
        DEFENSIVE("Defensive"),
        CONTROLLED("Controlled"),
        RANGING("Ranging"),
        LONGRANGE("Longrange"),
        CASTING("Casting"),
        DEFENSIVE_CASTING("Defensive Casting"),
        OTHER("Other");

        private final String name;

        AttackStyle(String name)
        {
            this.name = name;
        }
    }
}
