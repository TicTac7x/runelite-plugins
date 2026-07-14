package tictac7x.charges.item.triggers;

import java.util.*;

public class OnVarbitsMapChanged extends TriggerBase {
    public Map<Integer, Integer> varbitsMap;

    public OnVarbitsMapChanged(Map<Integer, Integer> varbitsMap) {
        this.varbitsMap = varbitsMap;
    }
}
