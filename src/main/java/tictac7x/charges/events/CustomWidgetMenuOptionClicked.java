package tictac7x.charges.events;

import java.util.List;

public class CustomWidgetMenuOptionClicked {
    public final int widgetId;
    public final List<String> options;
    public final String selectedOption;

    public CustomWidgetMenuOptionClicked(
        final int widgetId,
        final List<String> options,
        final String selectedOption
    ) {
        this.widgetId = widgetId;
        this.options = options;
        this.selectedOption = selectedOption;
    }
}
