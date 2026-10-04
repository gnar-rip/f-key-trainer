package com.fkeytrainer;

import net.runelite.api.gameval.InterfaceID;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameTabTest
{
	@Test public void mapsToplevel()
	{
		assertEquals(GameTab.COMBAT, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE0));
		assertEquals(GameTab.SKILLS, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE1));
		assertEquals(GameTab.QUESTS, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE2));
		assertEquals(GameTab.INVENTORY, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE3));
		assertEquals(GameTab.EQUIPMENT, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE4));
		assertEquals(GameTab.PRAYER, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE5));
		assertEquals(GameTab.MAGIC, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE6));
		assertEquals(GameTab.CHANNELS, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE7));
		assertEquals(GameTab.ACCOUNT, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE8));
		assertEquals(GameTab.FRIENDS, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE9));
		assertEquals(GameTab.LOGOUT, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE10));
		assertEquals(GameTab.SETTINGS, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE11));
		assertEquals(GameTab.EMOTES, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE12));
		assertEquals(GameTab.MUSIC, GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.STONE13));
	}

	@Test public void mapsToplevelOsrsStretch()
	{
		assertEquals(GameTab.COMBAT, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE0));
		assertEquals(GameTab.SKILLS, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE1));
		assertEquals(GameTab.QUESTS, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE2));
		assertEquals(GameTab.INVENTORY, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE3));
		assertEquals(GameTab.EQUIPMENT, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE4));
		assertEquals(GameTab.PRAYER, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE5));
		assertEquals(GameTab.MAGIC, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE6));
		assertEquals(GameTab.CHANNELS, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE7));
		assertEquals(GameTab.ACCOUNT, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE8));
		assertEquals(GameTab.FRIENDS, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE9));
		assertEquals(GameTab.LOGOUT, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE10));
		assertEquals(GameTab.SETTINGS, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE11));
		assertEquals(GameTab.EMOTES, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE12));
		assertEquals(GameTab.MUSIC, GameTab.fromWidget(InterfaceID.TOPLEVEL_OSRS_STRETCH, InterfaceID.ToplevelOsrsStretch.STONE13));
	}

	@Test public void mapsToplevelPreEoc()
	{
		assertEquals(GameTab.COMBAT, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE0));
		assertEquals(GameTab.SKILLS, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE1));
		assertEquals(GameTab.QUESTS, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE2));
		assertEquals(GameTab.INVENTORY, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE3));
		assertEquals(GameTab.EQUIPMENT, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE4));
		assertEquals(GameTab.PRAYER, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE5));
		assertEquals(GameTab.MAGIC, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE6));
		assertEquals(GameTab.CHANNELS, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE7));
		assertEquals(GameTab.ACCOUNT, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE8));
		assertEquals(GameTab.FRIENDS, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE9));
		assertEquals(GameTab.LOGOUT, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE10));
		assertEquals(GameTab.SETTINGS, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE11));
		assertEquals(GameTab.EMOTES, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE12));
		assertEquals(GameTab.MUSIC, GameTab.fromWidget(InterfaceID.TOPLEVEL_PRE_EOC, InterfaceID.ToplevelPreEoc.STONE13));
	}

	@Test public void unknownRootsAndWidgetsFailOpen()
	{
		assertNull(GameTab.fromWidget(-1, -1));
		assertNull(GameTab.fromWidget(InterfaceID.TOPLEVEL_OSM, InterfaceID.Toplevel.STONE3));
		assertNull(GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.ToplevelOsrsStretch.STONE3));
		assertNull(GameTab.fromWidget(InterfaceID.TOPLEVEL, InterfaceID.Toplevel.SIDE3));
	}

	@Test public void everyToggleControlsItsOwnTab()
	{
		FKeyTrainerConfig config = mock(FKeyTrainerConfig.class);
		when(config.blockCombat()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.COMBAT, tab.isBlocked(config)); }
		when(config.blockCombat()).thenReturn(false);
		when(config.blockSkills()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.SKILLS, tab.isBlocked(config)); }
		when(config.blockSkills()).thenReturn(false);
		when(config.blockQuests()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.QUESTS, tab.isBlocked(config)); }
		when(config.blockQuests()).thenReturn(false);
		when(config.blockInventory()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.INVENTORY, tab.isBlocked(config)); }
		when(config.blockInventory()).thenReturn(false);
		when(config.blockEquipment()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.EQUIPMENT, tab.isBlocked(config)); }
		when(config.blockEquipment()).thenReturn(false);
		when(config.blockPrayer()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.PRAYER, tab.isBlocked(config)); }
		when(config.blockPrayer()).thenReturn(false);
		when(config.blockMagic()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.MAGIC, tab.isBlocked(config)); }
		when(config.blockMagic()).thenReturn(false);
		when(config.blockChannels()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.CHANNELS, tab.isBlocked(config)); }
		when(config.blockChannels()).thenReturn(false);
		when(config.blockAccount()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.ACCOUNT, tab.isBlocked(config)); }
		when(config.blockAccount()).thenReturn(false);
		when(config.blockFriends()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.FRIENDS, tab.isBlocked(config)); }
		when(config.blockFriends()).thenReturn(false);
		when(config.blockLogout()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.LOGOUT, tab.isBlocked(config)); }
		when(config.blockLogout()).thenReturn(false);
		when(config.blockSettings()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.SETTINGS, tab.isBlocked(config)); }
		when(config.blockSettings()).thenReturn(false);
		when(config.blockEmotes()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.EMOTES, tab.isBlocked(config)); }
		when(config.blockEmotes()).thenReturn(false);
		when(config.blockMusic()).thenReturn(true);
		for (GameTab tab : GameTab.values()) { assertEquals(tab == GameTab.MUSIC, tab.isBlocked(config)); }
		when(config.blockMusic()).thenReturn(false);
	}
}
