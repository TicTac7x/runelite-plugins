package tictac7x.charges;

import java.awt.Color;
import java.util.function.Supplier;

public class MyConfig {
    private final Supplier<Boolean> showDebugIds;
    private final Supplier<String> getVersion;
    private final Supplier<Boolean> showDailyReset;
    private final Supplier<String> getResetDate;
    private final Supplier<Color> getColorUnknown;
    private final Supplier<Color> getColorEmpty;
    private final Supplier<Color> getColorActivated;
    private final Supplier<Color> getColorDefault;
    private final Supplier<Boolean> hideDestroyMenuEntries;
    private final Supplier<Boolean> showOverlays;
    private final Supplier<Boolean> showUnlimited;
    private final Supplier<Boolean> showBankOverlays;
    private final Supplier<Boolean> showOverlaysOnlyInBank;
    private final Supplier<TicTac7xChargesImprovedConfig.ItemOverlayLocation> itemOverlayLocation;
    private final Supplier<Boolean> showStorageTooltips;
    private final Supplier<Boolean> showInfoboxes;
    private final Supplier<TicTac7xChargesImprovedConfig.CombatTimeDegradableStyle> combatTimeDegradableStyle;
    private final Supplier<Integer> getEscapeCrystalInactivityPeriod;
    private final Supplier<TicTac7xChargesImprovedConfig.EscapeCrystalTimeRemainingUnit> getEscapeCrystalTimeRemainingUnit;
    private final Supplier<Integer> getEscapeCrystalTimeRemainingWarning;
    private final Supplier<TicTac7xChargesImprovedConfig.ItemActivity> getEscapeCrystalStatus;
    private final Supplier<Color> get4DoseColor;
    private final Supplier<Color> get3DoseColor;
    private final Supplier<Color> get2DoseColor;
    private final Supplier<Color> get1DoseColor;
    private final Supplier<Integer> getColossalPouchDecayCount;

    public MyConfig(
        Supplier<Boolean> showDebugIds,
        Supplier<String> getVersion,
        Supplier<Boolean> showDailyReset,
        Supplier<String> getResetDate,
        Supplier<Color> getColorUnknown,
        Supplier<Color> getColorEmpty,
        Supplier<Color> getColorActivated,
        Supplier<Color> getColorDefault,
        Supplier<Boolean> hideDestroyMenuEntries,
        Supplier<Boolean> showOverlays,
        Supplier<Boolean> showUnlimited,
        Supplier<Boolean> showBankOverlays,
        Supplier<Boolean> showOverlaysOnlyInBank,
        Supplier<TicTac7xChargesImprovedConfig.ItemOverlayLocation> itemOverlayLocation,
        Supplier<Boolean> showStorageTooltips,
        Supplier<Boolean> showInfoboxes,
        Supplier<TicTac7xChargesImprovedConfig.CombatTimeDegradableStyle> combatTimeDegradableStyle,
        Supplier<Integer> getEscapeCrystalInactivityPeriod,
        Supplier<TicTac7xChargesImprovedConfig.EscapeCrystalTimeRemainingUnit> getEscapeCrystalTimeRemainingUnit,
        Supplier<Integer> getEscapeCrystalTimeRemainingWarning,
        Supplier<TicTac7xChargesImprovedConfig.ItemActivity> getEscapeCrystalStatus,
        Supplier<Color> get4DoseColor,
        Supplier<Color> get3DoseColor,
        Supplier<Color> get2DoseColor,
        Supplier<Color> get1DoseColor,
        Supplier<Integer> getColossalPouchDecayCount
    ) {
        this.showDebugIds = showDebugIds;
        this.getVersion = getVersion;
        this.showDailyReset = showDailyReset;
        this.getResetDate = getResetDate;
        this.getColorUnknown = getColorUnknown;
        this.getColorEmpty = getColorEmpty;
        this.getColorActivated = getColorActivated;
        this.getColorDefault = getColorDefault;
        this.hideDestroyMenuEntries = hideDestroyMenuEntries;
        this.showOverlays = showOverlays;
        this.showUnlimited = showUnlimited;
        this.showBankOverlays = showBankOverlays;
        this.showOverlaysOnlyInBank = showOverlaysOnlyInBank;
        this.itemOverlayLocation = itemOverlayLocation;
        this.showStorageTooltips = showStorageTooltips;
        this.showInfoboxes = showInfoboxes;
        this.combatTimeDegradableStyle = combatTimeDegradableStyle;
        this.getEscapeCrystalInactivityPeriod = getEscapeCrystalInactivityPeriod;
        this.getEscapeCrystalTimeRemainingUnit = getEscapeCrystalTimeRemainingUnit;
        this.getEscapeCrystalTimeRemainingWarning = getEscapeCrystalTimeRemainingWarning;
        this.getEscapeCrystalStatus = getEscapeCrystalStatus;
        this.get4DoseColor = get4DoseColor;
        this.get3DoseColor = get3DoseColor;
        this.get2DoseColor = get2DoseColor;
        this.get1DoseColor = get1DoseColor;
        this.getColossalPouchDecayCount = getColossalPouchDecayCount;
    }

    public boolean showDebugIds() {
        return showDebugIds.get();
    }

    public String getVersion() {
        return getVersion.get();
    }

    public boolean showDailyReset() {
        return showDailyReset.get();
    }

    public String getResetDate() {
        return getResetDate.get();
    }

    public Color getColorUnknown() {
        return getColorUnknown.get();
    }

    public Color getColorEmpty() {
        return getColorEmpty.get();
    }

    public Color getColorActivated() {
        return getColorActivated.get();
    }

    public Color getColorDefault() {
        return getColorDefault.get();
    }

    public boolean hideDestroyMenuEntries() {
        return hideDestroyMenuEntries.get();
    }

    public boolean showOverlays() {
        return showOverlays.get();
    }

    public boolean showUnlimited() {
        return showUnlimited.get();
    }

    public boolean showBankOverlays() {
        return showBankOverlays.get();
    }

    public boolean showOverlaysOnlyInBank() {
        return showOverlaysOnlyInBank.get();
    }

    public TicTac7xChargesImprovedConfig.ItemOverlayLocation itemOverlayLocation() {
        return itemOverlayLocation.get();
    }

    public boolean showStorageTooltips() {
        return showStorageTooltips.get();
    }

    public boolean showInfoboxes() {
        return showInfoboxes.get();
    }

    public TicTac7xChargesImprovedConfig.CombatTimeDegradableStyle combatTimeDegradableStyle() {
        return combatTimeDegradableStyle.get();
    }

    public int getEscapeCrystalInactivityPeriod() {
        return getEscapeCrystalInactivityPeriod.get();
    }

    public TicTac7xChargesImprovedConfig.EscapeCrystalTimeRemainingUnit getEscapeCrystalTimeRemainingUnit() {
        return getEscapeCrystalTimeRemainingUnit.get();
    }

    public int getEscapeCrystalTimeRemainingWarning() {
        return getEscapeCrystalTimeRemainingWarning.get();
    }

    public TicTac7xChargesImprovedConfig.ItemActivity getEscapeCrystalStatus() {
        return getEscapeCrystalStatus.get();
    }

    public Color get4DoseColor() {
        return get4DoseColor.get();
    }

    public Color get3DoseColor() {
        return get3DoseColor.get();
    }

    public Color get2DoseColor() {
        return get2DoseColor.get();
    }

    public Color get1DoseColor() {
        return get1DoseColor.get();
    }

    public int getColossalPouchDecayCount() {
        return getColossalPouchDecayCount.get();
    }
}