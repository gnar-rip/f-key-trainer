package com.fkeytrainer;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(FKeyTrainerConfig.GROUP)
public interface FKeyTrainerConfig extends Config
{
	String GROUP = "fkeytrainer";

	@ConfigItem(keyName = "enabled", name = "Enable trainer",
		description = "Block mouse actions on selected tab buttons. Set your in-game keybinds first.", position = 0)
	default boolean enabled() { return true; }

	@ConfigItem(keyName = "showFeedback", name = "Show feedback when blocked",
		description = "Briefly show a reminder to use your keybind. Never writes to chat.", position = 1)
	default boolean showFeedback() { return true; }

	@ConfigItem(keyName = "blockCombat", name = "Block Combat Options mouse clicks",
		description = "Block mouse menu actions on the Combat Options tab button, including right-click options.", position = 2)
	default boolean blockCombat() { return true; }

	@ConfigItem(keyName = "blockSkills", name = "Block Skills mouse clicks",
		description = "Block mouse menu actions on the Skills tab button, including right-click options.", position = 3)
	default boolean blockSkills() { return false; }

	@ConfigItem(keyName = "blockQuests", name = "Block Quests / Journal mouse clicks",
		description = "Block mouse menu actions on the Quests / Journal tab button, including right-click options.", position = 4)
	default boolean blockQuests() { return false; }

	@ConfigItem(keyName = "blockInventory", name = "Block Inventory mouse clicks",
		description = "Block mouse menu actions on the Inventory tab button, including right-click options.", position = 5)
	default boolean blockInventory() { return true; }

	@ConfigItem(keyName = "blockEquipment", name = "Block Worn Equipment mouse clicks",
		description = "Block mouse menu actions on the Worn Equipment tab button, including right-click options.", position = 6)
	default boolean blockEquipment() { return true; }

	@ConfigItem(keyName = "blockPrayer", name = "Block Prayer mouse clicks",
		description = "Block mouse menu actions on the Prayer tab button, including right-click options.", position = 7)
	default boolean blockPrayer() { return true; }

	@ConfigItem(keyName = "blockMagic", name = "Block Magic mouse clicks",
		description = "Block mouse menu actions on the Magic tab button, including right-click options.", position = 8)
	default boolean blockMagic() { return true; }

	@ConfigItem(keyName = "blockChannels", name = "Block Chat Channels mouse clicks",
		description = "Block mouse menu actions on the Chat Channels tab button, including right-click options.", position = 9)
	default boolean blockChannels() { return false; }

	@ConfigItem(keyName = "blockAccount", name = "Block Account Management mouse clicks",
		description = "Block mouse menu actions on the Account Management tab button, including right-click options.", position = 10)
	default boolean blockAccount() { return false; }

	@ConfigItem(keyName = "blockFriends", name = "Block Friends / Ignore mouse clicks",
		description = "Block mouse menu actions on the Friends / Ignore tab button, including right-click options.", position = 11)
	default boolean blockFriends() { return false; }

	@ConfigItem(keyName = "blockLogout", name = "Block Logout mouse clicks",
		description = "Block mouse menu actions on the Logout tab button, including right-click options.", position = 12)
	default boolean blockLogout() { return false; }

	@ConfigItem(keyName = "blockSettings", name = "Block Settings mouse clicks",
		description = "Block mouse menu actions on the Settings tab button, including right-click options.", position = 13)
	default boolean blockSettings() { return false; }

	@ConfigItem(keyName = "blockEmotes", name = "Block Emotes mouse clicks",
		description = "Block mouse menu actions on the Emotes tab button, including right-click options.", position = 14)
	default boolean blockEmotes() { return false; }

	@ConfigItem(keyName = "blockMusic", name = "Block Music mouse clicks",
		description = "Block mouse menu actions on the Music tab button, including right-click options.", position = 15)
	default boolean blockMusic() { return false; }
}
