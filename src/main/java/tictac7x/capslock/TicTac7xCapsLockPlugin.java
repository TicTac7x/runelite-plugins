package tictac7x.capslock;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.MessageNode;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@PluginDescriptor(
	name = "CAPS LOCK",
	description = "ALLOW CAPS LOCK MESSAGES",
	tags = { "caps", "lock"	}
)
public class TicTac7xCapsLockPlugin extends Plugin {
	@Subscribe
	public void onOverheadTextChanged(final OverheadTextChanged event) {
		final String message = event.getOverheadText().trim();
		if (isMessageValidForCapsLock(message)) {
			event.getActor().setOverheadText(capsLockMessage(message));
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

		if (isMessageValidForCapsLock(message)) {
			messageNode.setValue(capsLockMessage(message));
		}
	}

	boolean isMessageValidForCapsLock(final String message) {
		for (final String sentence : message.trim().split("[.?!]+")) {
			if (isSentenceValidForCapsLock(sentence) == false) {
				return false;
			}
		}

		return true;
	}

	boolean isSentenceValidForCapsLock(final String sentence) {
		final String[] words = sentence.trim().replaceAll("\\s*[^\\p{L}\\p{N}\\s]+\\w*", "").split("\\s+");
		if (words.length == 1) return false;

		final List<String> cleanedWords = new LinkedList<>();
		for (final String rawWord : words) {
			final String cleanedWord = rawWord.replaceAll("[^a-zA-Z]", "");
			if (!cleanedWord.isEmpty()) {
				cleanedWords.add(cleanedWord);
			}
		}

		if (cleanedWords.size() == 1) return false;

		for (final String cleanedWord : cleanedWords) {
			if (!Character.isUpperCase(cleanedWord.charAt(0))) {
				return false;
			}
		}

		return true;
	}

	String capsLockMessage(final String message) {
		return message.toUpperCase();
	}
}
