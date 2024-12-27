package tictac7x.charges.store;

public class AdvancedMenuEntry {
    public final int eventId;
    public final String target;
    public final String option;
    public final int impostorId;

    public AdvancedMenuEntry(final int eventId, final String target, final String option, final int impostorId) {
        this.eventId = eventId;
        this.target = target;
        this.option = option;
        this.impostorId = impostorId;
    }
}
