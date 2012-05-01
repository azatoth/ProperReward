package se.azatoth.properreward;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ProperReward extends JavaPlugin {
	@Override
	public void onDisable() {
		PluginDescriptionFile plugin = getDescription();
		System.out.println(plugin.getName() + " version " + plugin.getVersion()
				+ " is now disabled");
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(
				new ProperRewardListener(), this);
		PluginDescriptionFile plugin = getDescription();
		System.out.println(plugin.getName() + " version " + plugin.getVersion()
				+ " by mbcraft.com is now enabled.");
	}

}