package tictac7x.tithe;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TicTac7xTithePluginTest {
	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(tictac7x.tithe.TicTac7xTithePlugin.class);
		RuneLite.main(args);
	}
}