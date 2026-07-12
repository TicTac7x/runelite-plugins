package tictac7x.charges.store.utils;

public class WidgetMenuAction {
    public final String selectedOption;
    public final int childIndex;
    public final String childString;

    public WidgetMenuAction(
        final String selectedOption,
        final int childIndex,
        final String childString
    ) {
        this.selectedOption = selectedOption;
        this.childIndex = childIndex;
        this.childString = childString;
    }
}
