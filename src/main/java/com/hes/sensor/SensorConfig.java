package com.hes.sensor;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(SensorConfig.GROUP)
public interface SensorConfig extends Config
{
	String GROUP = "sensor";

	@ConfigItem(
		keyName = "showCombatCard",
		name = "Show combat card",
		description = "Show the combat activity card",
		position = 0
	)
	default boolean showCombatCard()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showSkillingCard",
		name = "Show skilling card",
		description = "Show the skilling activity card",
		position = 1
	)
	default boolean showSkillingCard()
	{
		return true;
	}
}
