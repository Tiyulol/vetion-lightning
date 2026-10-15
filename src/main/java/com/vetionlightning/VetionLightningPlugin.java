package com.vetionlightning;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.GraphicsObject;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GraphicsObjectCreated;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Vet'ion Lightning",
	description = "Highlights tiles hit by Vet'ion / Calvar'ion lightning",
	tags = {"vetion", "vet'ion", "calvarion", "calvar'ion", "wilderness", "boss", "lightning"}
)
public class VetionLightningPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private VetionLightningOverlay overlay;

	@Inject
	private VetionLightningConfig config;

	@Getter
	private final List<GraphicsObject> lightning = new ArrayList<>();

	@Provides
	VetionLightningConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VetionLightningConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		lightning.clear();
	}

	@Subscribe
	public void onGraphicsObjectCreated(GraphicsObjectCreated event)
	{
		GraphicsObject obj = event.getGraphicsObject();


		if (obj.getId() == config.graphicId())
		{
			lightning.add(obj);
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		lightning.removeIf(GraphicsObject::finished);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING)
		{
			lightning.clear();
		}
	}
}
