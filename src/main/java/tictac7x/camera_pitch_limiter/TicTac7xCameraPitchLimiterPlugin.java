package tictac7x.camera_pitch_limiter;

import javax.inject.Inject;
import net.runelite.api.Client;
import lombok.extern.slf4j.Slf4j;
import com.google.inject.Provides;
import net.runelite.api.events.BeforeRender;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Camera Pitch Limiter",
	description = "Limit Camera Pitch",
	tags = { "camera", "pitch", "limit"	}
)
public class TicTac7xCameraPitchLimiterPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private TicTac7xCameraPitchLimiterConfig config;

	@Provides
	TicTac7xCameraPitchLimiterConfig provideConfig(final ConfigManager configManager) {
		return configManager.getConfig(TicTac7xCameraPitchLimiterConfig.class);
	}

	@Subscribe
	public void onBeforeRender(final BeforeRender event) {
		if (client.getCameraPitchTarget() >= config.getMaximumPitch()) {
			client.setCameraPitchTarget(config.getMaximumPitch());
		} else if (client.getCameraPitchTarget() <= config.getMinimumPitch()) {
			client.setCameraPitchTarget(config.getMinimumPitch());
		}
	}
}
