package com.fkeytrainer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicLong;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FKeyTrainerOverlayTest
{
	private final Client client = mock(Client.class);
	private final FKeyTrainerConfig config = mock(FKeyTrainerConfig.class, CALLS_REAL_METHODS);
	private final AtomicLong clock = new AtomicLong();
	private final FKeyTrainerOverlay overlay = new FKeyTrainerOverlay(client, config, clock::get);
	private final Graphics2D graphics = new BufferedImage(400, 200, BufferedImage.TYPE_INT_ARGB).createGraphics();

	@Before public void setUp() { when(client.getGameState()).thenReturn(GameState.LOGGED_IN); }
	@After public void tearDown() { graphics.dispose(); }

	@Test public void reminderExpiresWithoutScheduledWork()
	{
		assertNull(overlay.render(graphics));
		overlay.show(GameTab.PRAYER);
		assertNotNull(overlay.render(graphics));
		clock.set(FKeyTrainerOverlay.DURATION_NANOS - 1);
		assertNotNull(overlay.render(graphics));
		clock.incrementAndGet();
		assertNull(overlay.render(graphics));
	}

	@Test public void repeatClickReplacesRatherThanQueuesReminder()
	{
		overlay.show(GameTab.PRAYER);
		clock.set(FKeyTrainerOverlay.DURATION_NANOS - 1);
		overlay.show(GameTab.MAGIC);
		clock.addAndGet(FKeyTrainerOverlay.DURATION_NANOS - 1);
		assertNotNull(overlay.render(graphics));
		clock.incrementAndGet();
		assertNull(overlay.render(graphics));
	}

	@Test public void clearAndConfigurationSuppressReminder()
	{
		overlay.show(GameTab.PRAYER);
		overlay.clear();
		assertNull(overlay.render(graphics));
		overlay.show(GameTab.PRAYER);
		when(config.showFeedback()).thenReturn(false);
		assertNull(overlay.render(graphics));
		when(config.showFeedback()).thenReturn(true);
		when(config.enabled()).thenReturn(false);
		assertNull(overlay.render(graphics));
		when(config.enabled()).thenReturn(true);
		when(client.getGameState()).thenReturn(GameState.LOGIN_SCREEN);
		assertNull(overlay.render(graphics));
	}
}
