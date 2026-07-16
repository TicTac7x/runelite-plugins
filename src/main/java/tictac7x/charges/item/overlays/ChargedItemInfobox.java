package tictac7x.charges.item.overlays;

import net.runelite.client.ui.overlay.infobox.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.store.*;

import java.awt.*;
import java.util.*;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.INFINITE_SYMBOL;

public class ChargedItemInfobox extends InfoBox {
    private Provider provider;
    private ChargedItemBase chargedItem;

    private int itemId;

    public ChargedItemInfobox(
        Provider provider,
        ChargedItemBase chargedItem
    ) {
        super(provider.itemManager.getImage(chargedItem.itemId), provider.plugin);
        this.provider = provider;
        this.chargedItem = chargedItem;
        this.itemId = chargedItem.itemId;
    }

    @Override
    public String getName() {
        return super.getName() + "_" + chargedItem.getInfoboxName();
    }

    @Override
    public String getText() {
        return chargedItem.getTotalChargesString();
    }

    @Override
    public String getTooltip() {
        return chargedItem.getTooltip();
    }

    @Override
    public Color getTextColor() {
        return chargedItem.getTotalTextColor();
    }

    @Override
    public boolean render() {
        updateInfobox();

        if (
            !provider.config.showInfoboxes() ||
            !isChargedItemInfoboxEnabled() ||
            !chargedItem.inInventoryOrEquipment() ||
            chargedItem.getChargesString(itemId).equals(INFINITE_SYMBOL) && !provider.config.showUnlimited()
        ) {
            return false;
        }

        return true;
    }

    private void updateInfobox() {
        if (itemId != chargedItem.itemId) {
            // Update infobox id to keep track of correct item.
            itemId = chargedItem.itemId;

            // Update infobox image.
            setImage(provider.itemManager.getImage(itemId));
            provider.infoBoxManager.updateInfoBoxImage(this);
        }
    }

    private boolean isChargedItemInfoboxEnabled() {
        String configKey = chargedItem.getConfigKey() + TicTac7xChargesImprovedConfig._infobox;
        Optional<String> visible = Optional.ofNullable(provider.configManager.getConfiguration(configKey));
        return visible.isPresent() && visible.get().equals("true");
    }
}


