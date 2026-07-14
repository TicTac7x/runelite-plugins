package tictac7x.charges.items.weapons;

import net.runelite.api.*;
import net.runelite.api.gameval.ItemID;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_BryophytasStaff extends ChargedItem {
    public W_BryophytasStaff(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bryophytas_staff, ItemID.NATURE_STAFF_CHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.NATURE_STAFF_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.NATURE_STAFF_CHARGED)
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("The nature staff has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Save a nature rune.
            new OnChatMessage("Your staff saved you a nature rune.").increaseCharges(1),

            // Charge.
            new OnChatMessage("Your Bryophyta's staff now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Auto-charge.
            new OnAutoChargeMessage("Bryophyta's staff", "Nature rune", 1, this),

            // Regular spellbook.
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Bones to Bananas",
                "Low Level Alchemy",
                "Superheat Item",
                "High Level Alchemy"
            ).decreaseCharges(1),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Make sets").onMenuTarget(
                "Emerald bolts (e)"
            ).decreaseCharges(1),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Bind",
                "Bones to Peaches"
            ).decreaseCharges(2),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Snare"
            ).decreaseCharges(3),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Entangle"
            ).decreaseCharges(4),


            // Arcuus spellbook.
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Dark Lure",
                "Harmony Island Teleport"
            ).decreaseCharges(1),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Degrime",
                "Ward of Arceuus"
            ).decreaseCharges(2),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Reanimate").onMenuTarget(
                "Basic Reanimation"
            ).decreaseCharges(2),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Reanimate").onMenuTarget(
                "Adept Reanimation",
                "Expert Reanimation"
            ).decreaseCharges(3),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Reanimate").onMenuTarget(
                "Master Reanimation"
            ).decreaseCharges(4),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Resurrect").onMenuTarget(
                "Resurrect Crops"
            ).decreaseCharges(12),

            // Lunar spellbook.
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Tan Leather",
                "Plank Make",
                "Energy Transfer"
            ).decreaseCharges(1),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Spin Flax",
                "Fertile Soil"
            ).decreaseCharges(2),
            new OnXpDrop(Skill.MAGIC).isEquipped().onMenuOption("Cast").onMenuTarget(
                "Geomancy"
            ).decreaseCharges(3)
        ));
    }
}
