package tictac7x.charges.items.weapons.tridents;

import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;
import java.util.regex.*;

public abstract class _Trident extends ChargedItem {
    public _Trident(
        String config,
        int itemId,
        int itemIdUncharged,
        Optional<Integer> itemIdFull,
        String itemName,
        int attackGraphicId,
        Provider provider
    ) {
        super(config, itemId, provider);

        final List<TriggerItem> items = new ArrayList<>();
        items.add(new TriggerItem(itemIdUncharged).fixedCharges(0));
        items.add(new TriggerItem(itemId));
        if (itemIdFull.isPresent()) {
            items.add(new TriggerItem(itemIdFull.get()).fixedCharges(2500));
        }
        this.items = items.toArray(TriggerItem[]::new);

        String itemNameRegex = Pattern.quote(itemName);

        this.triggers.addAll(List.of(
            // Ran out of charges.
            new OnChatMessage("Your " + itemNameRegex + " has run out of charges.").setFixedCharges(0),

            // Check, one charge left.
            new OnChatMessage("Your " + itemNameRegex + " has one charge.").setFixedCharges(1),

            // Check for charges and warning when low.
            new OnChatMessage("Your " + itemNameRegex + "( only)? has (?<charges>.+) charges( left)?.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add .* charges? to the " + itemNameRegex + ". New total: (?<charges>.+)").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(attackGraphicId).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage(itemNameRegex, "Death rune", 1, this)
        ));
    }
}