package io.github.radbuilder.emojichat.utils;

import io.github.radbuilder.emojichat.EmojiChat;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * EmojiChat update checker class.
 *
 * @author RadBuilder
 * @since 1.3
 * @version 1.5
 */
public class EmojiChatUpdateChecker {
	/**
	 * The current version of EmojiChat.
	 */
	public final double currentVersion;
	/**
	 * If an update is available.
	 */
	public boolean updateAvailable;
	/**
	 * The latest version of EmojiChat.
	 */
	public double latestVersion;
	/**
	 * The update checker {@link org.bukkit.scheduler.BukkitTask}, which runs every 4 hours after 10 seconds.
	 */
	private final BukkitTask updateTask;
	
	/**
	 * Creates the EmojiChat update checker class with the main class instance.
	 *
	 * @param plugin The EmojiChat main class instance.
	 */
	public EmojiChatUpdateChecker(EmojiChat plugin) {
		currentVersion = Double.parseDouble(plugin.getDescription().getVersion());
		
		updateTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::checkForUpdates, 20L * 10L, 20L * 60L * 60L * 4L); // Start checking for updates after 10 seconds, then every 4 hours
	}
	
	/**
	 * Checks if updates are available.
	 */
	private void checkForUpdates() {
		try {
			URL url = new URL("https://api.spiget.org/v2/resources/50955/versions?size=1&sort=-id");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("User-Agent", "EmojiChat Update Checker"); // Sets the user-agent
			
			InputStream inputStream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			
			JSONArray value = (JSONArray) JSONValue.parseWithException(reader);
			
			latestVersion = Double.parseDouble(((JSONObject) value.get(value.size() - 1)).get("name").toString());
			
			updateAvailable = currentVersion < latestVersion;
		} catch (Exception ignored) { // Something happened, not sure what (possibly no internet connection), so no updates available
			updateAvailable = false;
		}
	}
	
	/**
	 * Cancel the {@link #updateTask}.
	 */
	public void cancelUpdateTask() {
		updateTask.cancel();
	}
}