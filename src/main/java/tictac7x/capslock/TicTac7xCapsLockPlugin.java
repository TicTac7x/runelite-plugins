package tictac7x.capslock;

import javax.inject.Inject;

import net.runelite.api.Client;
import lombok.extern.slf4j.Slf4j;
import com.google.inject.Provides;
import net.runelite.api.MessageNode;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "CAPS LOCK",
	description = "ALLOW CAPS LOCK MESSAGES",
	tags = { "caps", "lock"	}
)
public class TicTac7xCapsLockPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private TicTac7xCapsLockConfig config;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Provides
	TicTac7xCapsLockConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TicTac7xCapsLockConfig.class);
	}

	@Subscribe
	public void onOverheadTextChanged(final OverheadTextChanged event) {
		final String message = event.getOverheadText().trim();
		if (isMessageValidForUppercase(message)) {
			event.getActor().setOverheadText(message.toUpperCase());
		}
	}

	@Subscribe
	public void onChatMessage(final ChatMessage chatMessage) {
		switch (chatMessage.getType()) {
			case PUBLICCHAT:
			case MODCHAT:
			case FRIENDSCHAT:
			case CLAN_CHAT:
			case CLAN_GUEST_CHAT:
			case CLAN_GIM_CHAT:
			case PRIVATECHAT:
			case PRIVATECHATOUT:
			case MODPRIVATECHAT:
				break;
			default:
				return;
		}

		final MessageNode messageNode = chatMessage.getMessageNode();
		final String message = messageNode.getValue();

		if (isMessageValidForUppercase(message)) {
			messageNode.setValue(message.trim().toUpperCase());
		}
	}

	private boolean isMessageValidForUppercase(final String message) {
		final String[] words = message.trim().split("\\s+");
		if (words.length == 1) return false;

		for (final String rawWord : words) {
			final String cleanedWord = rawWord.replaceAll("[^a-zA-Z]", "");
			if (cleanedWord.isEmpty()) continue;

			if (!Character.isUpperCase(cleanedWord.charAt(0))) {
				return false;
			}
		}

		return true;
	}
}
