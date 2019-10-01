package io.github.radbuilder.emojichat;

import io.github.radbuilder.emojichat.utils.EmojiHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * EmojiChat gui class.
 *
 * @author RadBuilder
 * @version 1.8.3
 * @since 1.2
 */
class EmojiChatGui {
	/**
	 * EmojiChat main class instance.
	 */
	private final EmojiChat plugin;
	
	/**
	 * Creates the EmojiChat gui class with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	EmojiChatGui(EmojiChat plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Gets the emoji list gui {@link org.bukkit.inventory.Inventory} using the specified page number.
	 *
	 * @param page The page number to view (starts at 0).
	 * @return The emoji list gui {@link org.bukkit.inventory.Inventory} with the page specified.
	 */
	Inventory getInventory(int page) {
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.DARK_AQUA + "Emoji List Page " + (page + 1));
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			List<String> keyList = new ArrayList<>(plugin.getEmojiHandler().getEmojis().keySet());
			for (int inventoryPosition = 0, emojiMapPosition = page * 54; inventoryPosition < 54 && emojiMapPosition < keyList.size(); inventoryPosition++, emojiMapPosition++) {
				if (inventoryPosition == 45 && page != 0) { // Add a back button if they're not on the first page
					addBackArrow(gui);
					emojiMapPosition--;
				} else if (emojiMapPosition == keyList.size() - 1) { // On the last page, not full of items
					addBackArrow(gui);
					addItem(gui, keyList, emojiMapPosition);
				} else if (inventoryPosition == 53 && emojiMapPosition != keyList.size() - 1) { // Add a next button if they're not on the last page
					addNextArrow(gui);
					emojiMapPosition--;
				} else { // Add the emoji list item
					addItem(gui, keyList, emojiMapPosition);
				}
			}
			keyList.clear();
		});
		return gui;
	}
	
	/**
	 * Adds the back arrow to the gui specified.
	 *
	 * @param gui The gui {@link org.bukkit.inventory.Inventory} to add the back arrow to.
	 */
	private void addBackArrow(Inventory gui) {
		ItemStack backArrowStack = new ItemStack(Material.DIAMOND, 1);
		ItemMeta meta = backArrowStack.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "<- Back");
		backArrowStack.setItemMeta(meta);
		gui.setItem(45, backArrowStack);
	}
	
	/**
	 * Adds the next arrow to the gui specified.
	 *
	 * @param gui The gui {@link org.bukkit.inventory.Inventory} to add the next arrow to.
	 */
	private void addNextArrow(Inventory gui) {
		ItemStack nextArrowStack = new ItemStack(Material.DIAMOND, 1);
		ItemMeta meta = nextArrowStack.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Next ->");
		nextArrowStack.setItemMeta(meta);
		gui.setItem(53, nextArrowStack);
	}
	
	/**
	 * Adds an EmojiList item to the gui specified.
	 *
	 * @param gui The gui {@link org.bukkit.inventory.Inventory}.
	 * @param keyList The list of emoji keys.
	 * @param emojiMapPosition The current position in the emoji map, {@link EmojiHandler#getEmojis()}.
	 */
	private void addItem(Inventory gui, List<String> keyList, int emojiMapPosition) {
		ItemStack stack = new ItemStack(Material.PAPER, 1);
		ItemMeta meta = stack.getItemMeta();
		String key = keyList.get(emojiMapPosition);
		meta.setDisplayName(ChatColor.AQUA + key + ChatColor.RESET + " " + plugin.getEmojiHandler().getEmojis().get(key));
		stack.setItemMeta(meta);
		gui.addItem(stack);
	}
}
