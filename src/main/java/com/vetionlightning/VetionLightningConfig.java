package com.vetionlightning;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("vetionlightning")
public interface VetionLightningConfig extends Config
{
	@Alpha
	@ConfigItem(keyName = "borderColor", name = "Border color", description = "Tile outline color", position = 1)
	default Color borderColor()
	{
		return new Color(255, 0, 0, 255);
	}

	@Alpha
	@ConfigItem(keyName = "fillColor", name = "Fill color", description = "Tile fill color", position = 2)
	default Color fillColor()
	{
		return new Color(255, 0, 0, 60);
	}

	@Range(min = 1, max = 5)
	@ConfigItem(keyName = "borderWidth", name = "Border width", description = "Tile outline thickness", position = 3)
	default int borderWidth()
	{
		return 2;
	}

	@Range(min = 0, max = 3)
	@ConfigItem(
		keyName = "hitRadius",
		name = "Hit radius (tiles)",
		description = "Tiles highlighted around each lightning strike. 1 = 3x3 area, 0 = center tile only.",
		position = 3
	)
	default int hitRadius()
	{
		return 1;
	}

	@ConfigItem(
		keyName = "graphicId",
		name = "Lightning graphic ID",
		description = "Graphics object ID of the lightning strike. The default should work; only change it if the game updates.",
		position = 4
	)
	default int graphicId()
	{
		return 2346;
	}

}
