package com.vetionlightning;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GraphicsObject;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class VetionLightningOverlay extends Overlay
{
	private final Client client;
	private final VetionLightningPlugin plugin;
	private final VetionLightningConfig config;

	@Inject
	private VetionLightningOverlay(Client client, VetionLightningPlugin plugin, VetionLightningConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		for (GraphicsObject obj : plugin.getLightning())
		{
			LocalPoint lp = obj.getLocation();
			if (lp == null)
			{
				continue;
			}

			int r = config.hitRadius();
			for (int dx = -r; dx <= r; dx++)
			{
				for (int dy = -r; dy <= r; dy++)
				{
					LocalPoint tile = new LocalPoint(
						lp.getX() + dx * Perspective.LOCAL_TILE_SIZE,
						lp.getY() + dy * Perspective.LOCAL_TILE_SIZE);

					Polygon poly = Perspective.getCanvasTilePoly(client, tile);
					if (poly == null)
					{
						continue;
					}

					graphics.setColor(config.fillColor());
					graphics.fill(poly);
					graphics.setColor(config.borderColor());
					graphics.setStroke(new BasicStroke(config.borderWidth()));
					graphics.draw(poly);
				}
			}
		}
		return null;
	}
}
