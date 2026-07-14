package tictac7x.charges.items.shields;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.*;

public class S_KharedstMemoirs extends ChargedItem {
    public S_KharedstMemoirs(Provider provider) {
        super(TicTac7xChargesImprovedConfig.kharedsts_memoirs, ItemID.VEOS_KHAREDSTS_MEMOIRS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.VEOS_KHAREDSTS_MEMOIRS),
            new TriggerItem(ItemID.BOOK_OF_THE_DEAD)
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("You add an entry to Kharedst's Memoirs.").increaseCharges(20),

            // Teleport.
            new OnChatMessage("((Kharedst's Memoirs)|(The Book of the Dead)) now has (?<charges>.+) (memories|memory) remaining.").setDynamicallyCharges(),

            // Check empty.
            new OnChatMessage("((Kharedst's Memoirs)|(The Book of the Dead)) holds no charges.").setFixedCharges(0),

            // Check.
            new OnChatMessage("On the inside of the cover a message is displayed in dark ink. It reads: (?<charges>.+)/.+? (memories|memory) remain.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("((Kharedst's Memoirs)|(The Book of the Dead)) now has (?<charges>.+) charges.").setDynamicallyCharges(),

            // Try to charge book of the dead when already full.
            new OnChatMessage("The Book of the Dead is already fully charged.").setFixedCharges(250),

            // Try to charge kharedst memoirs when already full.
            new OnChatMessage("Kharedst's Memoirs is already fully charged.").setFixedCharges(100),

            // Common menu entries.
            new OnMenuEntryAdded("Reminisce").replaceOption("Teleport"),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }
}
