package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_SkillsNecklace extends ChargedItem {
    public J_SkillsNecklace(Provider provider) {
        super(TicTac7xChargesImprovedConfig.skills_necklace, ItemID.JEWL_NECKLACE_OF_SKILLS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.JEWL_NECKLACE_OF_SKILLS).fixedCharges(0),
            new TriggerItem(ItemID.JEWL_NECKLACE_OF_SKILLS_1).fixedCharges(1),
            new TriggerItem(ItemID.JEWL_NECKLACE_OF_SKILLS_2).fixedCharges(2),
            new TriggerItem(ItemID.JEWL_NECKLACE_OF_SKILLS_3).fixedCharges(3),
            new TriggerItem(ItemID.JEWL_NECKLACE_OF_SKILLS_4).fixedCharges(4),
            new TriggerItem(ItemID.JEWL_NECKLACE_OF_SKILLS_5).fixedCharges(5),
            new TriggerItem(ItemID.JEWL_NECKLACE_OF_SKILLS_6).fixedCharges(6),
        };

        this.triggers.addAll(List.of(
            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport")
        ));
    }
}
