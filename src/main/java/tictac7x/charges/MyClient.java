package tictac7x.charges;

import net.runelite.api.*;

import java.util.function.*;

public class MyClient {
    private Function<Integer, Integer> getVarbitValue;
    private Supplier<Integer> getTickCount;
    private Supplier<GameState> getGameState;
    private Function<Actor, Boolean> isLocalPlayer;

    public MyClient(Function<Integer, Integer> getVarbitValue, Supplier<Integer> getTickCount, Supplier<GameState> getGameState, Function<Actor, Boolean> isLocalPlayer) {
        this.getVarbitValue = getVarbitValue;
        this.getTickCount = getTickCount;
        this.getGameState = getGameState;
        this.isLocalPlayer = isLocalPlayer;
    }

    public int getVarbitValue(int varbitId) {
        return getVarbitValue.apply(varbitId);
    }

    public int getTickCount() {
        return getTickCount.get();
    }

    public GameState getGameState() {
        return getGameState.get();
    }

    public boolean isLocalPlayer(Actor actor) {
        return isLocalPlayer.apply(actor);
    }
}
