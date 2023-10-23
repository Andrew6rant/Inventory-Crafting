package io.github.andrew6rant.invcrafting;

import eu.midnightdust.lib.config.MidnightConfig;
import io.github.andrew6rant.invcrafting.config.Config;
import net.fabricmc.api.ClientModInitializer;

public class InvCraftingClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MidnightConfig.init("invcrafting", Config.class);
	}
}