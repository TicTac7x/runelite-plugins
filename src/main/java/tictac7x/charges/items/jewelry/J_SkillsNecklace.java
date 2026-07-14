package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_SkillsNecklace extends ChargedItem {
    public J_SkillsNecklace(Provider provider) {
        super(TicTac7xChargesImprovedConfig.skills_necklace, ItemId.SKILLS_NECKLACE_0, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SKILLS_NECKLACE_0).fixedCharges(0),
            new TriggerItem(ItemId.SKILLS_NECKLACE_1).fixedCharges(1),
            new TriggerItem(ItemId.SKILLS_NECKLACE_2).fixedCharges(2),
            new TriggerItem(ItemId.SKILLS_NECKLACE_3).fixedCharges(3),
            new TriggerItem(ItemId.SKILLS_NECKLACE_4).fixedCharges(4),
            new TriggerItem(ItemId.SKILLS_NECKLACE_5).fixedCharges(5),
            new TriggerItem(ItemId.SKILLS_NECKLACE_6).fixedCharges(6),
        };

        this.triggers.addAll(List.of(
            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport")
        ));
    }
}
