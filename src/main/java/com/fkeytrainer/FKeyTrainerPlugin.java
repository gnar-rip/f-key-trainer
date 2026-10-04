package com.fkeytrainer;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "F-Key Trainer",
	description = "Train keyboard tab switching by blocking mouse actions on selected tabs",
	tags = {"fkeys", "keyboard", "tabs", "training"}
)
public class FKeyTrainerPlugin extends Plugin
{
	@Inject Client client;
	@Inject FKeyTrainerConfig config;
	@Inject OverlayManager overlayManager;
	@Inject FKeyTrainerOverlay overlay;
	private volatile boolean active;

	@Provides
	FKeyTrainerConfig provideConfig(ConfigManager manager)
	{
		return manager.getConfig(FKeyTrainerConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlay.clear();
		overlayManager.add(overlay);
		active = true;
	}

	@Override
	protected void shutDown()
	{
		active = false;
		overlayManager.remove(overlay);
		overlay.clear();
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (!active || !config.enabled() || event.isConsumed()
			|| client.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}

		// Native tab buttons use static CC_OP entries. Do not match menu text,
		// RUNELITE options, item actions, dynamic children, or unknown operations.
		MenuAction action = event.getMenuAction();
		if ((action != MenuAction.CC_OP && action != MenuAction.CC_OP_LOW_PRIORITY)
			|| event.getParam0() != -1 || event.getId() < 1 || event.getId() > 10)
		{
			return;
		}

		GameTab tab = GameTab.fromWidget(client.getTopLevelInterfaceId(), event.getParam1());
		if (tab == null || !tab.isBlocked(config))
		{
			return;
		}
		Widget widget = client.getWidget(event.getParam1());
		if (widget == null || widget.isHidden())
		{
			return;
		}

		// The game's on-key script calls toplevel_sidebutton_op directly; it
		// does not dispatch this menu event. Never intercept scripts or keys.
		event.consume();
		if (config.showFeedback())
		{
			overlay.show(tab);
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (FKeyTrainerConfig.GROUP.equals(event.getGroup())) { overlay.clear(); }
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() != GameState.LOGGED_IN) { overlay.clear(); }
	}
}
