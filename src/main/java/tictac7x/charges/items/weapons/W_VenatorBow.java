package tictac7x.charges.items.weapons;

import java.awt.Color;

import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class W_VenatorBow extends ChargedItemWithStorage {
    public W_VenatorBow(final Provider provider) {
        this(TicTac7xChargesImprovedConfig.venator_bow, ItemId.VENATOR_BOW, provider, new TriggerItem[]{
            new TriggerItem(ItemId.VENATOR_BOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.VENATOR_BOW)
        });
    }

    public W_VenatorBow(final String configKey, final int itemId, final Provider provider, final TriggerItem[] items) {
        super(configKey, itemId, provider);

        this.items = items;

        this.storage.storableItems(
            new StorableItem(ItemId.ANCIENT_ESSENCE)
        );

        this.triggers.addAll(List.of(
            // Charging the bow with essence - Check to see if the bow is already fully charged.
            new OnChatMessage("venator bow is already fully charged.$").onItemClick().consumer(() -> {
                storage.clearAndPut(ItemId.ANCIENT_ESSENCE, 50000);
            }),

            // Charging the bow with essence - For charging your echo venator bow, as of March 2026, the game doesn't explicitly say "echo venator bow", but I'll include it just in case
            new OnChatMessage("You use (?<addedCharges>.+) ancient essence to charge your (echo )?venator bow. It now has (?<charges>.+) charges.").onItemClick().matcherConsumer(m -> {
                storage.clearAndPut(ItemId.ANCIENT_ESSENCE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
            }),

            // Uncharge (you can only uncharge ALL charges at once)
            new OnChatMessage("You fully uncharge your (echo )?venator bow, regaining (?<charges>.+) ancient essence in the process.").consumer(() -> {
                provider.clientThread.invokeLater(() -> {
                    Widget objectboxItemWidget = provider.client.getWidget(InterfaceID.Objectbox.ITEM);
                    // Check against the uncharged item since the item displayed on the widget will be the uncharged version
                    if (objectboxItemWidget != null && objectboxItemWidget.getItemId() == this.itemId) {
                        storage.clear();
                    }
                });
            }),

            // Check.
            new OnChatMessage("Your (echo )?venator bow has (?<charges>.+) charges? remaining.").onItemClick().matcherConsumer(m -> {
                storage.clearAndPut(ItemId.ANCIENT_ESSENCE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
            }),

            // Attack.
            new OnGraphicChanged(2289).isEquipped().consumer(() -> {
                storage.remove(ItemId.ANCIENT_ESSENCE, 1);
            })
        ));
    }

    @Override
    public Color getTextColor(final int itemId) {
        return this.getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        if (this.storage.getStorage().count(ItemId.ANCIENT_ESSENCE) == 0) {
            return provider.config.getColorEmpty();
        }

        return super.getTotalTextColor();
    }
}
