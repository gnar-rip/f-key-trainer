package com.fkeytrainer;

import java.util.function.Predicate;
import net.runelite.api.gameval.InterfaceID;

/** Explicit allowlist of native tab buttons, not tab contents or decorative icons. */
enum GameTab
{
	COMBAT("Combat Options", FKeyTrainerConfig::blockCombat,
		InterfaceID.Toplevel.STONE0, InterfaceID.ToplevelOsrsStretch.STONE0, InterfaceID.ToplevelPreEoc.STONE0),
	SKILLS("Skills", FKeyTrainerConfig::blockSkills,
		InterfaceID.Toplevel.STONE1, InterfaceID.ToplevelOsrsStretch.STONE1, InterfaceID.ToplevelPreEoc.STONE1),
	QUESTS("Quests / Journal", FKeyTrainerConfig::blockQuests,
		InterfaceID.Toplevel.STONE2, InterfaceID.ToplevelOsrsStretch.STONE2, InterfaceID.ToplevelPreEoc.STONE2),
	INVENTORY("Inventory", FKeyTrainerConfig::blockInventory,
		InterfaceID.Toplevel.STONE3, InterfaceID.ToplevelOsrsStretch.STONE3, InterfaceID.ToplevelPreEoc.STONE3),
	EQUIPMENT("Worn Equipment", FKeyTrainerConfig::blockEquipment,
		InterfaceID.Toplevel.STONE4, InterfaceID.ToplevelOsrsStretch.STONE4, InterfaceID.ToplevelPreEoc.STONE4),
	PRAYER("Prayer", FKeyTrainerConfig::blockPrayer,
		InterfaceID.Toplevel.STONE5, InterfaceID.ToplevelOsrsStretch.STONE5, InterfaceID.ToplevelPreEoc.STONE5),
	MAGIC("Magic", FKeyTrainerConfig::blockMagic,
		InterfaceID.Toplevel.STONE6, InterfaceID.ToplevelOsrsStretch.STONE6, InterfaceID.ToplevelPreEoc.STONE6),
	CHANNELS("Chat Channels", FKeyTrainerConfig::blockChannels,
		InterfaceID.Toplevel.STONE7, InterfaceID.ToplevelOsrsStretch.STONE7, InterfaceID.ToplevelPreEoc.STONE7),
	ACCOUNT("Account Management", FKeyTrainerConfig::blockAccount,
		InterfaceID.Toplevel.STONE8, InterfaceID.ToplevelOsrsStretch.STONE8, InterfaceID.ToplevelPreEoc.STONE8),
	FRIENDS("Friends / Ignore", FKeyTrainerConfig::blockFriends,
		InterfaceID.Toplevel.STONE9, InterfaceID.ToplevelOsrsStretch.STONE9, InterfaceID.ToplevelPreEoc.STONE9),
	LOGOUT("Logout", FKeyTrainerConfig::blockLogout,
		InterfaceID.Toplevel.STONE10, InterfaceID.ToplevelOsrsStretch.STONE10, InterfaceID.ToplevelPreEoc.STONE10),
	SETTINGS("Settings", FKeyTrainerConfig::blockSettings,
		InterfaceID.Toplevel.STONE11, InterfaceID.ToplevelOsrsStretch.STONE11, InterfaceID.ToplevelPreEoc.STONE11),
	EMOTES("Emotes", FKeyTrainerConfig::blockEmotes,
		InterfaceID.Toplevel.STONE12, InterfaceID.ToplevelOsrsStretch.STONE12, InterfaceID.ToplevelPreEoc.STONE12),
	MUSIC("Music", FKeyTrainerConfig::blockMusic,
		InterfaceID.Toplevel.STONE13, InterfaceID.ToplevelOsrsStretch.STONE13, InterfaceID.ToplevelPreEoc.STONE13);

	private final String displayName;
	private final Predicate<FKeyTrainerConfig> blocked;
	private final int fixed;
	private final int classic;
	private final int modern;

	GameTab(String displayName, Predicate<FKeyTrainerConfig> blocked, int fixed, int classic, int modern)
	{
		this.displayName = displayName;
		this.blocked = blocked;
		this.fixed = fixed;
		this.classic = classic;
		this.modern = modern;
	}

	String displayName() { return displayName; }
	boolean isBlocked(FKeyTrainerConfig config) { return blocked.test(config); }

	int widgetId(int root)
	{
		switch (root)
		{
			case InterfaceID.TOPLEVEL: return fixed;
			case InterfaceID.TOPLEVEL_OSRS_STRETCH: return classic;
			case InterfaceID.TOPLEVEL_PRE_EOC: return modern;
			default: return -1;
		}
	}

	static GameTab fromWidget(int root, int widgetId)
	{
		if (widgetId < 0) { return null; }
		for (GameTab tab : values())
		{
			if (tab.widgetId(root) == widgetId) { return tab; }
		}
		return null;
	}
}
