package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class U_JarGenerator extends ChargedItem {
    public U_JarGenerator(Provider provider) {
        super(TicTac7xChargesImprovedConfig.jar_generator, ItemID.II_JAR_GENERATOR, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.II_JAR_GENERATOR)
        };

        this.triggers.addAll(List.of(
            // Check or use.
            new OnChatMessage("You have (?<charges>.+) charges left in your jar generator.").setDynamicallyCharges(),

            // Crumbles.
            new OnChatMessage("Your jar generator runs out of charges and disappears.").setFixedCharges(100)
        ));
    }
}
