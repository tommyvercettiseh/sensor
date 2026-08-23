package com.hes.sensor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class SkillingOverlay extends Overlay
{
	private static final int DEFAULT_WIDTH = 210;
	private static final int DEFAULT_HEIGHT = 52;
	private static final int MIN_WIDTH = 125;
	private static final int MIN_HEIGHT = 38;
	private static final Color BACKGROUND = new Color(18, 18, 18, 220);
	private static final Color TEXT = Color.WHITE;
	private static final Color GREEN = new Color(40, 210, 55);
	private static final Color RED = new Color(225, 35, 35);

	private final SensorPlugin plugin;
	private final SensorConfig config;

	@Inject
	public SkillingOverlay(SensorPlugin plugin, SensorConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.TOP_LEFT);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setResizable(true);
		setMinimumSize(MIN_HEIGHT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.showSkillingCard())
		{
			return null;
		}

		Dimension size = getPreferredSize();
		int width = size == null ? DEFAULT_WIDTH : Math.max(MIN_WIDTH, size.width);
		int height = size == null ? DEFAULT_HEIGHT : Math.max(MIN_HEIGHT, size.height);
		boolean active = plugin.isSkilling();
		drawCard(graphics, width, height, "SKILLING", active ? "SKILLING" : "NOT SKILLING", active);
		return new Dimension(width, height);
	}

	private void drawCard(Graphics2D graphics, int width, int height, String label, String status, boolean active)
	{
		int pad = Math.max(7, Math.min(14, height / 5));
		int barHeight = Math.max(6, Math.min(16, height / 5));
		int fontSize = Math.max(10, Math.min(22, height / 3));
		Font base = graphics.getFont();
		graphics.setFont(base.deriveFont(Font.BOLD, (float) fontSize));
		FontMetrics metrics = graphics.getFontMetrics();

		graphics.setColor(BACKGROUND);
		graphics.fillRoundRect(0, 0, width, height, 10, 10);

		int textY = Math.min(height - barHeight - 8, pad + metrics.getAscent());
		graphics.setColor(TEXT);
		graphics.drawString(label, pad, textY);

		graphics.setColor(active ? GREEN : RED);
		int statusX = Math.max(pad, width - pad - metrics.stringWidth(status));
		graphics.drawString(status, statusX, textY);

		int barY = height - pad - barHeight;
		int barWidth = Math.max(20, width - (pad * 2));
		graphics.fillRoundRect(pad, barY, barWidth, barHeight, barHeight, barHeight);
		graphics.setFont(base);
	}
}
