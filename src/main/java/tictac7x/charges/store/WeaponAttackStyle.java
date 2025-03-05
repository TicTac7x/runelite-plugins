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
 * @see net.runelite.client.plugins.attackstyles.AttackStylesPlugin
 */
@Slf4j
public class WeaponAttackStyle {
    private final Client client;

    public WeaponAttackStyle(final Client client) {
        this.client = client;
    }

    public CombatStyle getCombatStyle() {
        final int currentAttackStyleVarbit = client.getVarpValue(VarPlayer.ATTACK_STYLE);
        final int currentEquippedWeaponTypeVarbit = client.getVarbitValue(Varbits.EQUIPPED_WEAPON_TYPE);
        final int weaponStyleEnum = client.getEnum(EnumID.WEAPON_STYLES).getIntValue(currentEquippedWeaponTypeVarbit);
        final int[] weaponStyleStructs = client.getEnum(weaponStyleEnum).getIntVals();

        final AttackStyle attackStyle = getAttackStyle(currentAttackStyleVarbit, weaponStyleStructs);
        return getWeaponFromAttackStyle(attackStyle, weaponStyleStructs);
    }

    private AttackStyle getAttackStyle(int attackStyleVarbit, final int [] weaponStyleStructs) {
        // Get selected weapon attack style
        StructComposition attackStyleStruct = client.getStructComposition(weaponStyleStructs[attackStyleVarbit]);
        String attackStyleName = attackStyleStruct.getStringValue(ParamID.ATTACK_STYLE_NAME);

        // Get selected attack style
        return AttackStyle.valueOf(attackStyleName.toUpperCase());
    }

    private CombatStyle getWeaponFromAttackStyle(final AttackStyle attackStyle, final int[] weaponStyleStructs) {
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
                final AttackStyle firstAttackStyle = getAttackStyle(0, weaponStyleStructs);

                return firstAttackStyle != AttackStyle.DEFENSIVE
                    ? getWeaponFromAttackStyle(firstAttackStyle, weaponStyleStructs)
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

        AttackStyle(String name) {
            this.name = name;
        }
    }
}
