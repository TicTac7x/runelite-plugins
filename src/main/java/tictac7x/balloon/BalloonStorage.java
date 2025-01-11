package tictac7x.balloon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;

public class BalloonStorage {
    private final Pattern regex_left_plural = Pattern.compile("You have (?<count>.+) sets of (?<type>.*) left in storage.");
    private final Pattern regex_left_singular = Pattern.compile("You have one set of (?<type>.*) left in storage.");
    private final Pattern regex_last = Pattern.compile("You used the last of your (?<type>.*).");
    private final Pattern regex_store = Pattern.compile("You put the (?<type>.*) in the crate. You now have (?<count>.+) stored.");
    private final Pattern regex_needed = Pattern.compile("You need 1 (?<type>.*) logs to make this trip.");
    private final Pattern regex_check = Pattern.compile("This crate currently contains (?<regular>.+) logs, (?<oak>.+) oak logs, (?<willow>.+) willow logs, (?<yew>.+) yew logs and (?<magic>.+) magic logs.");

    private final ConfigManager configManager;

    public BalloonStorage(final ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void onChatMessage(final ChatMessage event) {
        if (event.getType() != ChatMessageType.SPAM && event.getType() != ChatMessageType.MESBOX) return;
        final String message = event.getMessage();

        updateLogsCountFromStoreMessage(message);
        updateLogsCountFromCheckMessage(message);
        updateLogsCountFromLeftPluralMessage(message);
        updateLogsCountFromLeftSingularMessage(message);
        updateLogsCountFromLastMessage(message);
        updateLogsCountFromNeededMessage(message);
    }

    private void updateLogsCountFromLeftPluralMessage(final String message) {
        final Matcher matcher = regex_left_plural.matcher(message);

        if (matcher.find()) {
            final String type = matcher.group("type");
            final int count = Integer.parseInt(matcher.group("count"));

            saveLogsCountBasedOnType(type, count);
        }
    }

    private void updateLogsCountFromLeftSingularMessage(final String message) {
        final Matcher matcher = regex_left_singular.matcher(message);

        if (matcher.find()) {
            final String type = matcher.group("type");
            saveLogsCountBasedOnType(type, 1);
        }
    }

    private void updateLogsCountFromLastMessage(final String message) {
        final Matcher matcher = regex_last.matcher(message);

        if (matcher.find()) {
            final String type = matcher.group("type");
            saveLogsCountBasedOnType(type, 0);
        }
    }

    private void updateLogsCountFromCheckMessage(final String message) {
        final Matcher matcher = regex_check.matcher(message);
        if (!matcher.find()) return;

        final int regularLogs = Integer.parseInt(matcher.group("regular"));
        final int oakLogs = Integer.parseInt(matcher.group("oak"));
        final int willowLogs = Integer.parseInt(matcher.group("willow"));
        final int yewLogs = Integer.parseInt(matcher.group("yew"));
        final int magicLogs = Integer.parseInt(matcher.group("magic"));

        saveLogsCountBasedOnType("regular", regularLogs);
        saveLogsCountBasedOnType("oak", oakLogs);
        saveLogsCountBasedOnType("willow", willowLogs);
        saveLogsCountBasedOnType("yew", yewLogs);
        saveLogsCountBasedOnType("magic", magicLogs);
    }

    private void updateLogsCountFromNeededMessage(final String message) {
        final Matcher matcher = regex_needed.matcher(message);

        if (matcher.find()) {
            final String type = matcher.group("type");
            saveLogsCountBasedOnType(type, 0);
        }
    }

    private void updateLogsCountFromStoreMessage(final String message) {
        final Matcher matcher = regex_store.matcher(message);

        if (matcher.find()) {
            final String type = matcher.group("type");
            final int count = Integer.parseInt(matcher.group("count"));
            saveLogsCountBasedOnType(type, count);
        }
    }

    private void saveLogsCountBasedOnType(final String type, final int count) {
        switch (type) {
            case "Logs":
            case "normal":
            case "regular":
                configManager.setConfiguration(TicTac7xBalloonConfig.group, TicTac7xBalloonConfig.logs_regular, count);
                break;
            case "Oak logs":
            case "oak":
                configManager.setConfiguration(TicTac7xBalloonConfig.group, TicTac7xBalloonConfig.logs_oak, count);
                break;
            case "Willow logs":
            case "willow":
                configManager.setConfiguration(TicTac7xBalloonConfig.group, TicTac7xBalloonConfig.logs_willow, count);
                break;
            case "Yew logs":
            case "yew":
                configManager.setConfiguration(TicTac7xBalloonConfig.group, TicTac7xBalloonConfig.logs_yew, count);
                break;
            case "Magic logs":
            case "magic":
                configManager.setConfiguration(TicTac7xBalloonConfig.group, TicTac7xBalloonConfig.logs_magic, count);
                break;
        }
    }
}
