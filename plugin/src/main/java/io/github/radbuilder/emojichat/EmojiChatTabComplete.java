package io.github.radbuilder.emojichat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * EmojiChat tab completer.
 *
 * @author RadBuilder
 * @version 1.7
 * @since 1.7
 */
class EmojiChatTabComplete implements TabCompleter {
	/**
	 * The list of /emojichat sub-commands.
	 */
	private final List<String> SUB_COMMANDS = new ArrayList<>(Arrays.asList("help", "resourcepack", "reload", "toggle", "list"));
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
		// The possible completions
		List<String> completions = new ArrayList<>();
		// Gets the matches
		StringUtil.copyPartialMatches(args[0], SUB_COMMANDS, completions);
		// Sort the completions
		Collections.sort(completions);
		return completions;
	}
}
