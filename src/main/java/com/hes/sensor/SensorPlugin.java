package com.hes.sensor;

import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Sensor",
	description = "Shows a clear current activity state for combat, skilling and idle",
	tags = {"sensor", "combat", "skilling", "status"}
)
public class SensorPlugin extends Plugin
{
	private static final long COMBAT_HOLD_MS = 5000L;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private SensorOverlay overlay;

	private long combatUntil;

	@Override
	protected void startUp()
	{
		combatUntil = 0L;
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		combatUntil = 0L;
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied event)
	{
		Player local = client.getLocalPlayer();
		if (local == null)
		{
			return;
		}

		if (event.getActor() == local || event.getActor() == local.getInteracting())
		{
			combatUntil = System.currentTimeMillis() + COMBAT_HOLD_MS;
		}
	}

	SensorState getState()
	{
		Player local = client.getLocalPlayer();
		if (local == null)
		{
			return SensorState.IDLE;
		}

		if (System.currentTimeMillis() < combatUntil)
		{
			return SensorState.COMBAT;
		}

		if (local.getAnimation() != -1)
		{
			return SensorState.SKILLING;
		}

		return SensorState.IDLE;
	}

	enum SensorState
	{
		COMBAT,
		SKILLING,
		IDLE
	}
}
