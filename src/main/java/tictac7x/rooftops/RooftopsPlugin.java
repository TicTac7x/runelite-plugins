package tictac7x.rooftops;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Rooftop Agility Improved",
	description = "Improved clickboxes for rooftop agility courses",
	tags = { "roof", "rooftop", "agility", "mark", "grace", "graceful" }
)
public class RooftopsPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlays;

	@Inject
	private RooftopsConfig config;

	private RooftopsCoursesManager coursesManager;

	private RooftopsOverlay overlayRooftops;

	@Provides
	RooftopsConfig provideConfig(final ConfigManager configManager) {
		return configManager.getConfig(RooftopsConfig.class);
	}

	@Override
	protected void startUp() {
		coursesManager = new RooftopsCoursesManager(client);
		overlayRooftops = new RooftopsOverlay(client, config, coursesManager);
		overlays.add(overlayRooftops);
	}

	@Override
	protected void shutDown() {
		overlays.remove(overlayRooftops);
	}

	@Subscribe
	public void onGameObjectSpawned(final GameObjectSpawned event) {
		coursesManager.onTileObjectSpawned(event.getGameObject());
	}

	@Subscribe
	public void onWallObjectSpawned(final WallObjectSpawned event) {
		coursesManager.onTileObjectSpawned(event.getWallObject());
	}

	@Subscribe
	public void onGroundObjectSpawned(final GroundObjectSpawned event) {
		coursesManager.onTileObjectSpawned(event.getGroundObject());
	}

	@Subscribe
	public void onDecorativeObjectSpawned(final DecorativeObjectSpawned event) {
		coursesManager.onTileObjectSpawned(event.getDecorativeObject());
	}

	@Subscribe
	public void onItemSpawned(final ItemSpawned event) {
		coursesManager.onItemSpawned(event);
	}

	@Subscribe
	public void onItemDespawned(final ItemDespawned event) {
		coursesManager.onItemDespawned(event);
	}

	@Subscribe
	public void onChatMessage(final ChatMessage event) {
		coursesManager.onChatMessage(event);
	}

	@Subscribe
	public void onStatChanged(final StatChanged event) {
		coursesManager.onStatChanged(event);
		System.out.println(client.getLocalPlayer().getWorldLocation().getX() + " " + client.getLocalPlayer().getWorldLocation().getY());
	}

	@Subscribe
	public void onHitsplatApplied(final HitsplatApplied event) {
		coursesManager.onHitsplatApplied(event);
	}

	@Subscribe
	public void onGameTick(final GameTick gametick) {
		coursesManager.onGameTick(gametick);
		System.out.println(client.getBoostedSkillLevel(Skill.AGILITY));
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		coursesManager.onGameStateChanged(event);
	}

	@Subscribe
	public void onMenuOptionClicked(final MenuOptionClicked event) {
		coursesManager.onMenuOptionClicked(event);
	}
}
