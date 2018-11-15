package io.github.radbuilder.emojichat.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The emoji replacement variant.
 *
 * @author RadBuilder
 * @version 1.8
 * @since 1.7
 */
public enum EmojiPackVariant {
	/**
	 * Replaces Korean unicode characters with emojis (the original, default variant).
	 */
	KOREAN(1),
	/**
	 * Replaces Chinese unicode characters with emojis.
	 */
	CHINESE(2);
	
	/**
	 * The variant id.
	 */
	private final int id;
	/**
	 * The pack variant's SHA1 hash.
	 */
	private final byte[][] hash = new byte[2][];
	/**
	 * The pack variant's URL.
	 */
	private final String url;
	
	/**
	 * Creates a new emoji pack variant with the specified id.
	 *
	 * @param id The id associated with the emoji pack variant.
	 */
	EmojiPackVariant(int id) {
		this.id = id;
		this.hash[0] = byteHash(id, 0);
		this.hash[1] = byteHash(id, 1);
		this.url = "https://github.com/RadBuilder/EmojiChat/releases/download/v1.7/EmojiChat." + id + ".{HD or SD}.ResourcePack.v1.8.zip";
	}
	
	/**
	 * Gets the variant id.
	 *
	 * @return The variant id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the variant's SHA1 hash.
	 *
	 * @return The variant's SHA1 hash.
	 */
	public byte[] getHash(String quality) {
		quality = quality.toUpperCase(); // Ensure quality is uppercase for a valid hash
		if (!quality.equals("HD") && !quality.equals("SD")) {
			quality = "SD"; // Handle invalid config options by giving them the sd version
		}
		return hash[quality.equals("HD") ? 0 : 1];
	}
	
	/**
	 * Gets the variant's url.
	 *
	 * @param quality The quality of the pack (hd or sd).
	 * @return The variant's url.
	 */
	public String getUrl(String quality) {
		quality = quality.toUpperCase(); // Ensure quality is uppercase for a valid URL
		if (!quality.equals("HD") && !quality.equals("SD")) {
			quality = "SD"; // Handle invalid config options by giving them the sd version
		}
		return url.replace("{HD or SD}", quality);
	}
	
	/**
	 * Gets the {@link EmojiPackVariant} based on the id specified.
	 *
	 * @param id The id.
	 * @return The {@link EmojiPackVariant} with the specified id, or null if there isn't one.
	 */
	static EmojiPackVariant getVariantbyId(int id) {
		for (EmojiPackVariant variant : values()) {
			if (variant.id == id) { // ID given matches a variant
				return variant;
			}
		}
		return null; // ID given doesn't match a variant
	}
	
	/**
	 * Gets the hash for the specified id.
	 *
	 * @param id The id to get the hash for.
	 * @param quality The quality as an int to get the hash for. 0 for HD, 1 for SD.
	 * @return The hash if the id is valid, a blank byte array otherwise.
	 */
	private byte[] byteHash(int id, int quality) {
		byte[] hash = new byte[20];
		
		try {
			InputStream listInput = getClass().getResourceAsStream("/hash.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(listInput));
			int currentLine = 0;
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("#")) { // Ignored line
					continue;
				}
				currentLine++;
				if (currentLine == id + quality) {
					String[] values = line.split(",");
					for (int i = 0; i < 20; i++) {
						hash[i] = Byte.valueOf(values[i]);
					}
				}
			}
			bufferedReader.close();
			listInput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}
}
