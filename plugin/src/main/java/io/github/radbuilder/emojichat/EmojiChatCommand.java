package io.github.radbuilder.emojichat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * EmojiChat command class.
 *
 * @author RadBuilder
 * @since 1.0
 */
class EmojiChatCommand implements CommandExecutor {
	/**
	 * EmojiChat main class instance.
	 */
	private EmojiChat plugin;
	
	/**
	 * Creates the EmojiChat command class with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	EmojiChatCommand(EmojiChat plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (args.length < 1) {
			sender.sendMessage(ChatColor.AQUA + "EmojiChat v1.2 by RadBuilder");
			sender.sendMessage(ChatColor.AQUA + "Use " + ChatColor.GREEN + "/emojichat help" + ChatColor.AQUA + " for help.");
			return true;
		}
		
		// Sub commands
		switch (args[0].toLowerCase()) {
			case "help":
				if (!sender.hasPermission("emojichat.help")) {
					sender.sendMessage(ChatColor.RED + "You need " + ChatColor.GOLD + "emojichat.help" + ChatColor.RED + " to use this command.");
					return true;
				}
				
				sender.sendMessage(ChatColor.AQUA + "---------- EmojiChat Help ----------");
				sender.sendMessage(ChatColor.GREEN + "/emojichat help: " + ChatColor.AQUA + "Opens up this help menu.");
				sender.sendMessage(ChatColor.GREEN + "/emojichat list: " + ChatColor.AQUA + "Lists all of the emojis configured.");
				return true;
			case "resourcepack":
				if (!sender.hasPermission("emojichat.see")) {
					sender.sendMessage(ChatColor.RED + "You need " + ChatColor.GOLD + "emojichat.see" + ChatColor.RED + " to use this command.");
					return true;
				}
				
				if (sender instanceof Player) {
					((Player) sender).setResourcePack(plugin.PACK_URL);
					sender.sendMessage(ChatColor.GREEN + "Sent EmojiChat ResourcePack.");
					sender.sendMessage(ChatColor.AQUA + "If you still can't see emojis, make sure the settings for this server (on the server list) have the resource pack option set to prompt or enabled.");
				}
				return true;
			case "list":
				if (!sender.hasPermission("emojichat.list")) {
					sender.sendMessage(ChatColor.RED + "You need " + ChatColor.GOLD + "emojichat.list" + ChatColor.RED + " to use this command.");
					return true;
				}
				
				sender.sendMessage(ChatColor.AQUA + "---------- EmojiChat List ----------");
				for (String key : plugin.emojiMap.keySet()) {
					sender.sendMessage(ChatColor.AQUA + key + " " + ChatColor.RESET + plugin.emojiMap.get(key));
				}
				return true;
			default:
				sender.sendMessage(ChatColor.RED + "Unknown sub-command. Use " + ChatColor.GOLD + "/emojichat help" + ChatColor.RED + " for help.");
				return true;
		}
	}
}
