package com.hes.sensor;

import com.google.inject.Provides;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Sensor",
	description = "Shows compact, movable combat, skilling and session status cards",
	tags = {"activity", "combat", "skilling", "session", "status", "overlay"}
)
public class SensorPlugin extends Plugin
{
	private static final long COMBAT_HOLD_MS = 5000L;
	private static final long SKILLING_HOLD_MS = 3000L;

	private static final Set<Skill> COMBAT_SKILLS = EnumSet.of(
		Skill.ATTACK,
		Skill.STRENGTH,
		Skill.DEFENCE,
		Skill.RANGED,
		Skill.MAGIC,
		Skill.HITPOINTS,
		Skill.SLAYER
	);

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private CombatOverlay combatOverlay;

	@Inject
	private SkillingOverlay skillingOverlay;

	@Inject
	private SessionOverlay sessionOverlay;

	private final Map<Skill, Integer> lastXp = new EnumMap<>(Skill.class);
	private long combatUntil;
	private long skillingUntil;

	@Provides
	SensorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SensorConfig.class);
	}

	@Override
	protected void startUp()
	{
		combatUntil = 0L;
		skillingUntil = 0L;
		lastXp.clear();
		overlayManager.add(combatOverlay);
		overlayManager.add(skillingOverlay);
		overlayManager.add(sessionOverlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(combatOverlay);
		overlayManager.remove(skillingOverlay);
		overlayManager.remove(sessionOverlay);
		combatUntil = 0L;
		skillingUntil = 0L;
		lastXp.clear();
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

	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		Skill skill = event.getSkill();
		int xp = event.getXp();
		Integer previousXp = lastXp.put(skill, xp);

		if (previousXp == null || xp <= previousXp || COMBAT_SKILLS.contains(skill))
		{
			return;
		}

		skillingUntil = System.currentTimeMillis() + SKILLING_HOLD_MS;
	}

	boolean isCombat()
	{
		return client.getLocalPlayer() != null && System.currentTimeMillis() < combatUntil;
	}

	boolean isSkilling()
	{
		Player local = client.getLocalPlayer();
		if (local == null || isCombat())
		{
			return false;
		}

		if (System.currentTimeMillis() < skillingUntil)
		{
			return true;
		}

		return local.getAnimation() != -1;
	}

	boolean isLoggedIn()
	{
		return client.getGameState() == GameState.LOGGED_IN;
	}
}
