package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class U_ArchivistsFolio extends ChargedItem {
    public U_ArchivistsFolio(Provider provider) {
        super(TicTac7xChargesImprovedConfig.archivists_folio, ItemID.ARCHIVISTS_FOLIO, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ARCHIVISTS_FOLIO_EMPTY).fixedCharges(0),
            new TriggerItem(ItemID.ARCHIVISTS_FOLIO),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("The folio has one charge.").setFixedCharges(1),
            new OnChatMessage("The folio has (?<charges>.+) charges.").setDynamicallyCharges(),

            // Charge (1 ascension shard = 5 charges).
            new OnChatMessage("You charge your Archivist's folio with .+ It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Teleport.
            new OnChatMessage("You open the folio and read the words.").decreaseCharges(1)
        ));
    }
}
