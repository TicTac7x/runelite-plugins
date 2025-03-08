package tictac7x.capslock;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TicTac7xCapsLockPluginTest {
	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(TicTac7xCapsLockPlugin.class);
		RuneLite.main(args);
	}

	@Test
	public void testIsValidMessageForCapsLock() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();

		final String message1 = "Yes";
		assertEquals(false, plugin.isMessageValidForCapsLock(message1));

		final String message2 = "No :)";
		assertEquals(false, plugin.isMessageValidForCapsLock(message2));

		final String message3 = "Yes Please";
		assertEquals(true, plugin.isMessageValidForCapsLock(message3));

		final String message4 = "Buy 10";
		assertEquals(false, plugin.isMessageValidForCapsLock(message4));

		final String message5 = "Buy 10 More";
		assertEquals(true, plugin.isMessageValidForCapsLock(message5));

		final String message6 = "Test 123T";
		assertEquals(true, plugin.isMessageValidForCapsLock(message6));

		final String message7 = "Should Pass :)";
		assertEquals(true, plugin.isMessageValidForCapsLock(message7));
	}
}