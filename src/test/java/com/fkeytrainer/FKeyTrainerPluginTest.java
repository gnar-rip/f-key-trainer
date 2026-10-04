package com.fkeytrainer;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.ScriptID;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.ScriptPreFired;
import net.runelite.api.events.VarClientIntChanged;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.VarClientID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.ui.overlay.OverlayManager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FKeyTrainerPluginTest
{
	private final FKeyTrainerPlugin plugin = new FKeyTrainerPlugin();
	private final Client client = mock(Client.class);
	private final FKeyTrainerConfig config = mock(FKeyTrainerConfig.class, CALLS_REAL_METHODS);
	private final Widget widget = mock(Widget.class);

	@Before
	public void setUp()
	{
		plugin.client = client;
		plugin.config = config;
		plugin.overlayManager = mock(OverlayManager.class);
		plugin.overlay = mock(FKeyTrainerOverlay.class);
		when(client.getGameState()).thenReturn(GameState.LOGGED_IN);
		when(client.getTopLevelInterfaceId()).thenReturn(InterfaceID.TOPLEVEL);
		when(client.getWidget(anyInt())).thenReturn(widget);
		plugin.startUp();
		clearInvocations(plugin.overlay);
	}

	private MenuOptionClicked click(int id, MenuAction type, int child, int op)
	{
		MenuEntry entry = mock(MenuEntry.class);
		when(entry.getParam1()).thenReturn(id);
		when(entry.getParam0()).thenReturn(child);
		when(entry.getType()).thenReturn(type);
		when(entry.getIdentifier()).thenReturn(op);
		// Text deliberately doesn't identify a tab.
		when(entry.getOption()).thenReturn("Unrelated / translated label");
		return new MenuOptionClicked(entry);
	}

	private MenuOptionClicked prayer()
	{
		return click(InterfaceID.Toplevel.STONE5, MenuAction.CC_OP, -1, 1);
	}

	@Test
	public void configuredMouseClickBlockedAndFeedbackShown()
	{
		MenuOptionClicked event = prayer();
		plugin.onMenuOptionClicked(event);
		assertTrue(event.isConsumed());
		verify(plugin.overlay).show(GameTab.PRAYER);
	}

	@Test
	public void defaultLocksOnlyFivePrimaryTabsAcrossAllLayouts()
	{
		int[] roots = {InterfaceID.TOPLEVEL, InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.TOPLEVEL_PRE_EOC};
		for (int root : roots)
		{
			when(client.getTopLevelInterfaceId()).thenReturn(root);
			for (GameTab tab : GameTab.values())
			{
				MenuOptionClicked event = click(tab.widgetId(root), MenuAction.CC_OP, -1, 1);
				plugin.onMenuOptionClicked(event);
				boolean primary = tab == GameTab.COMBAT || tab == GameTab.INVENTORY
					|| tab == GameTab.EQUIPMENT || tab == GameTab.PRAYER || tab == GameTab.MAGIC;
				assertEquals(tab + " root " + root, primary, event.isConsumed());
			}
		}
	}

	@Test
	public void togglesTakeEffectImmediately()
	{
		when(config.blockPrayer()).thenReturn(false);
		MenuOptionClicked first = prayer();
		plugin.onMenuOptionClicked(first);
		assertFalse(first.isConsumed());
		when(config.blockPrayer()).thenReturn(true);
		MenuOptionClicked second = prayer();
		plugin.onMenuOptionClicked(second);
		assertTrue(second.isConsumed());
	}

	@Test
	public void masterOffAllowsAllTabs()
	{
		when(config.enabled()).thenReturn(false);
		for (GameTab tab : GameTab.values())
		{
			MenuOptionClicked event = click(tab.widgetId(InterfaceID.TOPLEVEL), MenuAction.CC_OP, -1, 1);
			plugin.onMenuOptionClicked(event);
			assertFalse(event.isConsumed());
		}
		verifyNoInteractions(plugin.overlay);
	}

	@Test
	public void unknownContentIconsAndInactiveLayoutNeverBlocked()
	{
		int[] ids = {-1, 0, Integer.MAX_VALUE, InterfaceID.Toplevel.ICON5,
			InterfaceID.Toplevel.SIDE5, InterfaceID.ToplevelOsrsStretch.STONE5};
		for (int id : ids)
		{
			MenuOptionClicked event = click(id, MenuAction.CC_OP, -1, 1);
			plugin.onMenuOptionClicked(event);
			assertFalse(event.isConsumed());
		}
		when(client.getTopLevelInterfaceId()).thenReturn(InterfaceID.TOPLEVEL_OSM);
		MenuOptionClicked event = prayer();
		plugin.onMenuOptionClicked(event);
		assertFalse(event.isConsumed());
	}

	@Test
	public void everyNonWidgetActionFailsOpen()
	{
		for (MenuAction action : MenuAction.values())
		{
			if (action == MenuAction.CC_OP || action == MenuAction.CC_OP_LOW_PRIORITY) { continue; }
			MenuOptionClicked event = click(InterfaceID.Toplevel.STONE5, action, -1, 1);
			plugin.onMenuOptionClicked(event);
			assertFalse(action.toString(), event.isConsumed());
		}
	}

	@Test
	public void dynamicChildrenAndInvalidOpsFailOpen()
	{
		for (int child : new int[]{0, 1, -2})
		{
			MenuOptionClicked event = click(InterfaceID.Toplevel.STONE5, MenuAction.CC_OP, child, 1);
			plugin.onMenuOptionClicked(event);
			assertFalse(event.isConsumed());
		}
		for (int op : new int[]{-1, 0, 11})
		{
			MenuOptionClicked event = click(InterfaceID.Toplevel.STONE5, MenuAction.CC_OP, -1, op);
			plugin.onMenuOptionClicked(event);
			assertFalse(event.isConsumed());
		}
	}

	@Test
	public void rightClickAndLowPriorityOpsBlockedOnlyOnConfiguredTabs()
	{
		when(config.blockQuests()).thenReturn(true);
		for (int op = 1; op <= 10; op++)
		{
			MenuOptionClicked event = click(InterfaceID.Toplevel.STONE2,
				op <= 5 ? MenuAction.CC_OP : MenuAction.CC_OP_LOW_PRIORITY, -1, op);
			plugin.onMenuOptionClicked(event);
			assertTrue(event.isConsumed());
		}
	}

	@Test
	public void missingHiddenAndLoggedOutFailOpen()
	{
		when(client.getWidget(anyInt())).thenReturn(null);
		MenuOptionClicked missing = prayer();
		plugin.onMenuOptionClicked(missing);
		assertFalse(missing.isConsumed());
		when(client.getWidget(anyInt())).thenReturn(widget);
		when(widget.isHidden()).thenReturn(true);
		MenuOptionClicked hidden = prayer();
		plugin.onMenuOptionClicked(hidden);
		assertFalse(hidden.isConsumed());
		when(widget.isHidden()).thenReturn(false);
		when(client.getGameState()).thenReturn(GameState.LOGIN_SCREEN);
		MenuOptionClicked loggedOut = prayer();
		plugin.onMenuOptionClicked(loggedOut);
		assertFalse(loggedOut.isConsumed());
	}

	@Test
	public void feedbackCanBeDisabledWithoutDisablingLock()
	{
		when(config.showFeedback()).thenReturn(false);
		MenuOptionClicked event = prayer();
		plugin.onMenuOptionClicked(event);
		assertTrue(event.isConsumed());
		verifyNoInteractions(plugin.overlay);
	}

	@Test
	public void consumedEventsLeftAlone()
	{
		MenuOptionClicked event = prayer();
		event.consume();
		plugin.onMenuOptionClicked(event);
		verifyNoInteractions(plugin.overlay);
	}

	@Test
	public void shutdownRemovesOverlayAndStopsBlocking()
	{
		plugin.shutDown();
		verify(plugin.overlayManager).remove(plugin.overlay);
		verify(plugin.overlay).clear();
		MenuOptionClicked event = prayer();
		plugin.onMenuOptionClicked(event);
		assertFalse(event.isConsumed());
	}

	@Test
	public void keyboardAndProgrammaticScriptPathsHaveNoSubscriberOrWidgetMutations()
	{
		// This verifies isolation, not a simulated OSRS F-key integration test.
		// The actual on-key script path is documented in docs/RESEARCH.md.
		EventBus bus = new EventBus();
		bus.register(plugin);
		clearInvocations(client, widget, plugin.overlay);
		bus.post(new ScriptPreFired(ScriptID.TOPLEVEL_REDRAW));
		bus.post(new VarClientIntChanged(VarClientID.TOPLEVEL_PANEL));
		verifyNoInteractions(client, widget, plugin.overlay);
		bus.unregister(plugin);
	}
}
