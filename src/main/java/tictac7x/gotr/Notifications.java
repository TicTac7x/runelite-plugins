package tictac7x.gotr;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.Notifier;
import tictac7x.gotr.store.Inventory;
import tictac7x.gotr.types.BeforeGameStarts;
import tictac7x.gotr.types.Teleporter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Notifications {
    private final Notifier notifier;
    private final Inventory inventory;
    private TicTac7xGotrImprovedConfig config;

    private final String regexGameStartsIn30Seconds = "The rift will become active in 30 seconds.";
    private final String regexGameStartsIn10Seconds = "The rift will become active in 10 seconds.";
    private final String regexGameStartsIn5Seconds = "The rift will become active in 5 seconds.";
    private final String regexGameStarted = "The rift becomes active!";
    private final Pattern regexPortalOpened = Pattern.compile("(?<location>.*) - .*");

    public Notifications(final Notifier notifier, final Inventory inventory, final TicTac7xGotrImprovedConfig config) {
        this.notifier = notifier;
        this.inventory = inventory;
        this.config = config;
    }

    public void onGameObjectSpawned(final GameObject gameObject) {
        if (!config.notifyUnusedEyeRobes()) return;
        boolean altarSpawned = false;

        for (final Teleporter teleporter : Teleporter.ALL) {
            if (gameObject.getId() == teleporter.altarId) {
                altarSpawned = true;
                break;
            }
        }
        if (altarSpawned && inventory.hasGuardianEssence() && inventory.hasPieceOfEyeEquipmentInInventory()) {
            notifier.notify("Remember to wear your robes of the eye.");
        }
    }

    public void onChatMessage(final ChatMessage message) {
        if (message.getMessage().equals(regexGameStarted)) {
            if (config.notifyGameStarted()) {
                notifier.notify("Guardian remains are now mineable!");
            }
        } else if (message.getMessage().equals(regexGameStartsIn30Seconds)) {
            if (config.notifyBeforeGameStarts() == BeforeGameStarts.THIRTY) {
                notifier.notify(regexGameStartsIn30Seconds.replace(".", ""));
            }
        } else if (message.getMessage().equals(regexGameStartsIn10Seconds)) {
            if (config.notifyBeforeGameStarts() == BeforeGameStarts.TEN) {
                notifier.notify(regexGameStartsIn10Seconds.replace(".", ""));
            }
        } else if (message.getMessage().equals(regexGameStartsIn5Seconds)) {
            if (config.notifyBeforeGameStarts() == BeforeGameStarts.FIVE) {
                notifier.notify(regexGameStartsIn5Seconds.replace(".", ""));
            }
        }
    }

    public void notifyAboutPortal(final String openedPortal) {
        if (!config.notifyPortalOpened()) return;

        final Matcher matcher = regexPortalOpened.matcher(openedPortal);
        if (!matcher.find()) return;

        final String location = matcher.group("location");

        notifier.notify("Portal opened to the " + (
            location.equals("S") ? "south" :
            location.equals("SW") ? "south west" :
            location.equals("SE") ? "south east" :
            location.equals("E") ? "east" :
            location.equals("W") ? "west" :
            "") + "!"
        );
    }

    public void notifyBeforeFirstRift(final int seconds) {
        if (config.notifyBeforeFirstRift() != -1) {
            notifier.notify(seconds + " seconds before creatures start attacking!");
        }
    }
}
