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
	public void Yes() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(false, plugin.isMessageValidForCapsLock("Yes"));
	}

	@Test
	public void No_Smiley() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(false, plugin.isMessageValidForCapsLock("No :)"));
	}

	@Test
	public void Yep_Grin() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(false, plugin.isMessageValidForCapsLock("Nope :D"));
	}

	@Test
	public void Should_Pass_Smiley() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(true, plugin.isMessageValidForCapsLock("Should Pass :)"));
	}

	@Test
	public void Yes_Please() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(true, plugin.isMessageValidForCapsLock("Yes Please"));
	}

	@Test
	public void Buy_10() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(false, plugin.isMessageValidForCapsLock("Buy 10"));
	}

	@Test
	public void Buy_10_More() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(true, plugin.isMessageValidForCapsLock("Buy 10 More"));
	}

	@Test
	public void Test_123X() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(true, plugin.isMessageValidForCapsLock("Test 123X"));
	}

	@Test
	public void Test_123y() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(false, plugin.isMessageValidForCapsLock("Test 123y"));
	}

	@Test
	public void TraumaQuestionmark_PtsdQuestionmark() {
		final TicTac7xCapsLockPlugin plugin = new TicTac7xCapsLockPlugin();
		assertEquals(false, plugin.isMessageValidForCapsLock("Trauma? Ptsd?"));
	}
}