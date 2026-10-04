package com.fkeytrainer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.function.LongSupplier;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

/** One replaceable reminder; no queue, chat messages, timers, or persisted history. */
class FKeyTrainerOverlay extends OverlayPanel
{
	static final long DURATION_NANOS = 1_500_000_000L;
	private final Client client;
	private final FKeyTrainerConfig config;
	private final LongSupplier clock;
	private volatile Reminder reminder;

	@Inject
	FKeyTrainerOverlay(Client client, FKeyTrainerConfig config)
	{
		this(client, config, System::nanoTime);
	}

	FKeyTrainerOverlay(Client client, FKeyTrainerConfig config, LongSupplier clock)
	{
		this.client = client;
		this.config = config;
		this.clock = clock;
		setPosition(OverlayPosition.BOTTOM_RIGHT);
		setResizable(false);
		panelComponent.setPreferredSize(new Dimension(230, 0));
	}

	void show(GameTab tab)
	{
		reminder = new Reminder(tab.displayName(), clock.getAsLong());
	}

	void clear() { reminder = null; }

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Reminder current = reminder;
		if (current == null || !config.enabled() || !config.showFeedback()
			|| client.getGameState() != GameState.LOGGED_IN
			|| clock.getAsLong() - current.startedAt >= DURATION_NANOS)
		{
			return null;
		}
		panelComponent.getChildren().add(LineComponent.builder().left(current.tab).build());
		panelComponent.getChildren().add(LineComponent.builder().left("Use your tab keybind").build());
		return super.render(graphics);
	}

	private static final class Reminder
	{
		private final String tab;
		private final long startedAt;

		private Reminder(String tab, long startedAt)
		{
			this.tab = tab;
			this.startedAt = startedAt;
		}
	}
}
