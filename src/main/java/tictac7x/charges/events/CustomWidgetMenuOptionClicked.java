package tictac7x.charges.events;

import java.util.*;

public class CustomWidgetMenuOptionClicked {
    public int widgetId;
    public List<String> options;
    public String selectedOption;

    public CustomWidgetMenuOptionClicked(
        int widgetId,
        List<String> options,
        String selectedOption
    ) {
        this.widgetId = widgetId;
        this.options = options;
        this.selectedOption = selectedOption;
    }
}
