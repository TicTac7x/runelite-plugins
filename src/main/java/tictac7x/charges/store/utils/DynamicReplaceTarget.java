package tictac7x.charges.store.utils;

import java.util.concurrent.*;

public class DynamicReplaceTarget {
    public String target;
    public Callable<String> replace;

    public DynamicReplaceTarget(String target, Callable<String> replace) {
        this.target = target;
        this.replace = replace;
    }
}
