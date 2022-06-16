package org.vivecraft.vve;

import java.util.Arrays;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

@Plugin(id = "vvivecraftvelocityextensions", name = "Vivecraft Velocity Extensions", version = "1.0", authors = {"Techjar"})
public class VVE {
	public static final ChannelIdentifier[] CHANNELS = {
			MinecraftChannelIdentifier.create("vivecraft", "data"),
			new LegacyChannelIdentifier("Vivecraft")
	};

	private final ProxyServer server;
	private final Logger logger;

	@Inject
	public VVE(ProxyServer server, Logger logger) {
		this.server = server;
		this.logger = logger;

		for (ChannelIdentifier channel : CHANNELS)
			server.getChannelRegistrar().register(channel);

		logger.info("Vivecraft Velocity Extensions loaded!");
	}

	@Subscribe
	public void on(PluginMessageEvent event) {
		ChannelIdentifier channel = event.getIdentifier();
		if (Arrays.stream(CHANNELS).noneMatch(channel::equals))
			return;
		event.getTarget().sendPluginMessage(channel, event.getData());
	}
}
