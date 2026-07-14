package tictac7x.charges.item.triggers;

public class OnGraphicChanged extends TriggerBase {
    public int[] graphicId;

    public OnGraphicChanged(int ...graphicId) {
        this.graphicId = graphicId;
    }
}
