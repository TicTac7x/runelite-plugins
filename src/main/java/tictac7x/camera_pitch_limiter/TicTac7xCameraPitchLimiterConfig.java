package tictac7x.camera_pitch_limiter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("tictac7x-pitch-limiter")
public interface TicTac7xCameraPitchLimiterConfig extends Config {
	@Range(min = 0, max = 4160)
	@ConfigItem(
		keyName = "maximumPitch",
		name = "Maximum Pitch",
		description = "Highest camera point (0 - 4160)"
	) default int getMaximumPitch() { return 3064; }

	@Range(min = 0, max = 4160)
	@ConfigItem(
		keyName = "minimumPitch",
		name = "Minimum Pitch",
		description = "Lowest camera point (0 - 4160)"
	) default int getMinimumPitch() { return 1024; }
}
