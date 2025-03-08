package tictac7x.capslock;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TicTac7xCapsLockPluginTest {
	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(TicTac7xCapsLockPlugin.class);
		RuneLite.main(args);
	}
}