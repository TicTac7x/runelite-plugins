package tictac7x.gotr.store;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Points {
    private final Client client;
    private final ConfigManager configManager;
    private final TicTac7xGotrImprovedConfig config;

    private final int GOTR_WIDGET_GROUP = 746;
    private final int GOTR_WIDGET_ELEMENTAL_POINTS_CHILD = 21;
    private final int GOTR_WIDGET_CATALYTIC_POINTS_CHILD = 24;
    private final Pattern REGEX_LOOT = Pattern.compile("You found some loot: .*");
    private final Pattern REGEX_CHECK = Pattern.compile("You have (?<catalytic>.+) catalytic energy and (?<elemental>.+) elemental energy. You can use them to search the rift (?<searches>.+) times. You have searched the rift (?<total>.+) times.");
    private final Pattern REGEX_TOTAL = Pattern.compile("Total elemental energy: (?<elemental>.+). Total catalytic energy: ( )?(?<catalytic>.+).");
    private final Pattern REGEX_ENERGY = Pattern.compile(".+<br>Energy: (?<energy>.+)");

    private Optional<Widget> elementalPointsWidget = Optional.empty();
    private Optional<Widget> catalyticPointsWidget = Optional.empty();
    private int gameElementalPoints = 0;
    private int gameCatalyticPoints = 0;
    private boolean gameRunning = true;

    public Points(final Client client, final ConfigManager configManager, final TicTac7xGotrImprovedConfig config) {
        this.client = client;
        this.configManager = configManager;
        this.config = config;
    }

    public int getTotalElementalPoints() {
        return config.getElementalEnergy();
    }

    public int getTotalCatalyticPoints() {
        return config.getCatalyticEnergy();
    }

    public int getGameElementalPoints() {
        return gameRunning ? gameElementalPoints : 0;
    }

    public int getGameCatalyticPoints() {
        return gameRunning ? gameCatalyticPoints : 0;
    }

    public void onChatMessage(final ChatMessage message) {
        if (message.getType() != ChatMessageType.GAMEMESSAGE && message.getType() != ChatMessageType.MESBOX && message.getType() != ChatMessageType.SPAM) return;

        final Matcher matcherCheck = REGEX_CHECK.matcher(message.getMessage());
        final Matcher matcherTotal = REGEX_TOTAL.matcher(message.getMessage());
        final Matcher matcherLoot = REGEX_LOOT.matcher(message.getMessage());

        // Game started.
        if (message.getMessage().equals("The rift becomes active!")) {
            gameRunning = true;

        // Game ended.
        } else if (message.getMessage().equals("The Portal Guardians will keep their rifts open for another 30 seconds.")) {
            gameRunning = false;

        // Check manually.
        } else if (matcherCheck.find()) {
            final int catalytic = Integer.parseInt(matcherCheck.group("catalytic"));
            final int elemental = Integer.parseInt(matcherCheck.group("elemental"));

            configManager.setConfiguration(TicTac7xGotrImprovedConfig.group, TicTac7xGotrImprovedConfig.energy_catalytic, catalytic);
            configManager.setConfiguration(TicTac7xGotrImprovedConfig.group, TicTac7xGotrImprovedConfig.energy_elemental, elemental);

        // Game finished total message.
        } else if (matcherTotal.find()) {
            final int catalytic = Integer.parseInt(matcherTotal.group("catalytic"));
            final int elemental = Integer.parseInt(matcherTotal.group("elemental"));

            configManager.setConfiguration(TicTac7xGotrImprovedConfig.group, TicTac7xGotrImprovedConfig.energy_catalytic, catalytic);
            configManager.setConfiguration(TicTac7xGotrImprovedConfig.group, TicTac7xGotrImprovedConfig.energy_elemental, elemental);

        // Loot.
        } else if (matcherLoot.find()) {
            configManager.setConfiguration(TicTac7xGotrImprovedConfig.group, TicTac7xGotrImprovedConfig.energy_elemental, config.getElementalEnergy() - 1);
            configManager.setConfiguration(TicTac7xGotrImprovedConfig.group, TicTac7xGotrImprovedConfig.energy_catalytic, config.getCatalyticEnergy() - 1);
        }
    }

    public void onWidgetLoaded(final WidgetLoaded event) {
        if (event.getGroupId() == GOTR_WIDGET_GROUP) {
            elementalPointsWidget = Optional.ofNullable(client.getWidget(GOTR_WIDGET_GROUP, GOTR_WIDGET_ELEMENTAL_POINTS_CHILD));
            catalyticPointsWidget = Optional.ofNullable(client.getWidget(GOTR_WIDGET_GROUP, GOTR_WIDGET_CATALYTIC_POINTS_CHILD));
        }
    }

    public void onGameTick() {
        if (elementalPointsWidget.isPresent()) {
            final Matcher matcher = REGEX_ENERGY.matcher(elementalPointsWidget.get().getText());
            if (matcher.find()) {
                this.gameElementalPoints = Integer.parseInt(matcher.group("energy"));
            }
        }

        if (catalyticPointsWidget.isPresent()) {
            final Matcher matcher = REGEX_ENERGY.matcher(catalyticPointsWidget.get().getText());
            if (matcher.find()) {
                this.gameCatalyticPoints = Integer.parseInt(matcher.group("energy"));
            }
        }
    }
}
