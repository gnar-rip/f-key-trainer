package com.fkeytrainer;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class FKeyTrainerLauncher
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(FKeyTrainerPlugin.class);
		RuneLite.main(args);
	}
}
