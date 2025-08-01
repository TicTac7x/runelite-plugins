package tictac7x.camera_pitch_limiter;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TicTac7xCameraPitchLimiterPluginTest {
	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(TicTac7xCameraPitchLimiterPlugin.class);
		RuneLite.main(args);
	}
}