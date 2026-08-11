package tictac7x.charges.item.triggers;

import net.runelite.api.events.*;

import java.util.*;
import java.util.function.*;

public class OnMenuOpened extends TriggerBase {
    public Optional<Consumer<MenuOpened>> menuConsumer = Optional.empty();

    public OnMenuOpened menuConsumer(Consumer<MenuOpened> menuConsumer) {
        this.menuConsumer = Optional.of(menuConsumer);
        return this;
    }
}
