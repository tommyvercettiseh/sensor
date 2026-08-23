package com.hes.sensor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class SensorOverlay extends Overlay
{
	private static final int WIDTH = 220;
	private static final int HEIGHT = 132;
	private static final int PAD = 12;
	private static final int BAR_HEIGHT = 12;
	private static final Color BACKGROUND = new Color(18, 18, 18, 220);
	private static final Color TEXT = Color.WHITE;
	private static final Color GREEN = new Color(40, 210, 55);
	private static final Color RED = new Color(225, 35, 35);
	private static final Color IDLE = new Color(255, 210, 35);

	private final SensorPlugin plugin;

	@Inject
	public SensorOverlay(SensorPlugin plugin)
	{
		this.plugin = plugin;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		SensorPlugin.SensorState state = plugin.getState();
		int x = 0;
		int y = 0;

		graphics.setColor(BACKGROUND);
		graphics.fillRoundRect(x, y, WIDTH, HEIGHT, 10, 10);

		graphics.setColor(TEXT);
		graphics.drawString("SENSOR", x + PAD, y + 20);

		drawCombatRow(graphics, x + PAD, y + 38, plugin.isCombat());
		drawSkillingRow(graphics, x + PAD, y + 76, plugin.isSkilling());

		graphics.setColor(TEXT);
		graphics.drawString("STATE:", x + PAD, y + 122);
		graphics.setColor(state == SensorPlugin.SensorState.IDLE ? IDLE : GREEN);
		graphics.drawString(state.name(), x + PAD + 48, y + 122);

		return new Dimension(WIDTH, HEIGHT);
	}

	private void drawCombatRow(Graphics2D graphics, int x, int y, boolean active)
	{
		drawRow(graphics, x, y, "COMBAT", active ? "ACTIVE" : "OFF", active);
	}

	private void drawSkillingRow(Graphics2D graphics, int x, int y, boolean active)
	{
		drawRow(graphics, x, y, "SKILLING", active ? "SKILLING" : "NOT SKILLING", active);
	}

	private void drawRow(Graphics2D graphics, int x, int y, String label, String status, boolean active)
	{
		FontMetrics metrics = graphics.getFontMetrics();
		graphics.setColor(TEXT);
		graphics.drawString(label, x, y);

		graphics.setColor(active ? GREEN : RED);
		graphics.drawString(status, x + WIDTH - (PAD * 2) - metrics.stringWidth(status), y);
		graphics.fillRoundRect(x, y + 7, WIDTH - (PAD * 2), BAR_HEIGHT, BAR_HEIGHT, BAR_HEIGHT);
	}
}
