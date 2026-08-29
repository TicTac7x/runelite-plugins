package tictac7x.deposit_worn_items;

import net.runelite.api.Client;
import net.runelite.api.gameval.SpriteID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.util.ImageUtil;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class LockIconOverlay extends Overlay {
    @Inject
    private Client client;

    @Inject
    private TicTac7xDepositWornItemsConfig config;

    @Inject
    private SpriteManager spriteManager;

    private BufferedImage lockIcon = null;

    public LockIconOverlay() {
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPosition(OverlayPosition.DYNAMIC);
    }

    @Override
    public Dimension render(final Graphics2D graphics2D) {
        if (config.isDepositWornItemsEnabled()) return null;

        if (lockIcon == null) {
            lockIcon = spriteManager.getSprite(SpriteID.Bankbuttons._7, 0);
            if (lockIcon == null) return null;
            lockIcon = ImageUtil.resizeImage(lockIcon, 12, 14);
            lockIcon = ImageUtil.alphaOffset(lockIcon, -50);
        }

        @Nullable
        final Widget widgetBankDepositWornItems = client.getWidget(12, 49);

        @Nullable
        final Widget widgetDepositBoxDepositWornItems = client.getWidget(192, 30);

        if (widgetBankDepositWornItems != null) {
            graphics2D.drawImage(
                lockIcon,
                widgetBankDepositWornItems.getCanvasLocation().getX() + 4,
                widgetBankDepositWornItems.getCanvasLocation().getY() + widgetBankDepositWornItems.getHeight() - lockIcon.getHeight() - 5,
                null
            );
        }

        if (widgetDepositBoxDepositWornItems != null) {
            graphics2D.drawImage(
                lockIcon,
                widgetDepositBoxDepositWornItems.getCanvasLocation().getX() + 3,
                widgetDepositBoxDepositWornItems.getCanvasLocation().getY() + widgetDepositBoxDepositWornItems.getHeight() - lockIcon.getHeight() - 5,
                null
            );
        }

        return null;
    }
}
