package tictac7x.charges.item.overlays;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBox;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;

import java.awt.Color;
import java.util.Optional;

public class ChargedItemInfobox extends InfoBox {
    private final ChargedItemBase chargedItem;
    private final ItemManager itemManager;
    private final InfoBoxManager infoBoxManager;
    private final TicTac7xChargesImprovedConfig config;
    private final ConfigManager configManager;

    private int itemId;

    public ChargedItemInfobox(
        final ChargedItemBase chargedItem,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ConfigManager configManager,
        final TicTac7xChargesImprovedConfig config,
        final TicTac7xChargesImprovedPlugin plugin
    ) {
        super(itemManager.getImage(chargedItem.itemId), plugin);
        this.chargedItem = chargedItem;
        this.itemManager = itemManager;
        this.infoBoxManager = infoBoxManager;
        this.configManager = configManager;
        this.config = config;
        this.itemId = chargedItem.itemId;
    }

    @Override
    public String getName() {
        return super.getName() + "_" + chargedItem.itemId;
    }

    @Override
    public String getText() {
        return chargedItem.getTotalCharges();
    }

    @Override
    public String getTooltip() {
        return chargedItem.getTooltip();
    }

    @Override
    public Color getTextColor() {
        return chargedItem.getTextColor();
    }

    @Override
    public boolean render() {
        updateInfobox();

        if (
            !config.showInfoboxes() ||
            !isChargedItemInfoboxEnabled() ||
            chargedItem.getCharges().equals("∞") && !config.showUnlimited() ||
            (!chargedItem.inInventory() && !chargedItem.inEquipment())
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
            setImage(itemManager.getImage(itemId));
            infoBoxManager.updateInfoBoxImage(this);
        }
    }

    private boolean isChargedItemInfoboxEnabled() {
        final Optional<String> visible = Optional.ofNullable(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, chargedItem.configKey + "_infobox"));

        if (visible.isPresent() && visible.get().equals("false")) {
            return false;
        }

        return true;
    }
}


