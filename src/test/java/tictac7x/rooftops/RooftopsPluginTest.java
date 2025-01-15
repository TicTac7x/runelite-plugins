package tictac7x.rooftops;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RooftopsPluginTest {
	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(TicTac7xRooftopsPlugin.class);
		RuneLite.main(args);
	}
}