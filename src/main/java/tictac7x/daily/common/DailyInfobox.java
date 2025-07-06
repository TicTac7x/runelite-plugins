package tictac7x.daily.common;

import net.runelite.api.events.VarbitChanged;
import net.runelite.client.ui.overlay.infobox.InfoBox;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class DailyInfobox extends InfoBox {
    protected final Provider provider;
    protected final String id;

    public DailyInfobox(final String id, final BufferedImage image, final Provider provider) {
        super(image, provider.plugin);
        this.id = id;
        this.provider = provider;
    }

    abstract public boolean isShowing();

    abstract public String getText();

    abstract public String getTooltip();

    @Override
    public String getName() {
        return super.getName() + "_" + this.id;
    }

    @Override
    public boolean render() {
        return isShowing();
    }

    @Override
    public Color getTextColor() {
        return Color.red;
    }

    public void onVarbitChanged(final VarbitChanged event) {}

    public boolean varbitEqualsOne(final int varbit) {
        return provider.client.getVarbitValue(varbit) == 1;
    }
}
