package com.hes.sensor;

import com.hes.objectmarker.ObjectMarkerPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SensorPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SensorPlugin.class);
		ExternalPluginManager.loadBuiltin(ObjectMarkerPlugin.class);
		RuneLite.main(args);
	}
}
