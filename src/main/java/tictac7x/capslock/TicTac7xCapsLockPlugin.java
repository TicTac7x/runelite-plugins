package tictac7x.capslock;

import javax.inject.Inject;
import net.runelite.api.Client;
import lombok.extern.slf4j.Slf4j;
import com.google.inject.Provides;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "CAPS LOCK",
	description = "ALLOW WRITING MESSAGES IN CAPS LOCK",
	tags = { "caps", "lock"	}
)
public class TicTac7xCapsLockPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private TicTac7xCapsLockConfig config;

	@Provides
	TicTac7xCapsLockConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TicTac7xCapsLockConfig.class);
	}

	@Override
	protected void startUp() {
	}

	@Override
	protected void shutDown() {
	}
}
