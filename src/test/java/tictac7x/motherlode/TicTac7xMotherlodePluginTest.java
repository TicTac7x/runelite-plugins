package tictac7x.motherlode;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import org.junit.Before;

public class TicTac7xMotherlodePluginTest {
	Sack sack;

	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(TicTac7xMotherlodePlugin.class);
		RuneLite.main(args);
	}

	@Before
	public void setup() {
		sack = new Sack();
	}
}