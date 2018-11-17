package io.github.radbuilder.emojichat.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The emoji replacement variant.
 *
 * @author RadBuilder
 * @version 1.8.1
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
	private byte[][] hash;
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
		this.hash = byteHash(id);
		this.url = "https://github.com/RadBuilder/EmojiChat/releases/download/v1.8/EmojiChat." + id + ".{HD or SD}.ResourcePack.v1.8.zip";
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
	 * @param quality The pack quality for the correct SHA1 hash to be returned (HD or SD).
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
	 * @return The hash if the id is valid, a blank byte array otherwise.
	 */
	private byte[][] byteHash(int id) {
		byte[][] hashes = new byte[2][];
		int hashesPosition = 0;
		
		try {
			InputStream listInput = getClass().getResourceAsStream("/hash." + id + ".txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(listInput));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("#") || !line.contains(",")) { // Ignore lines that start with # and non-valid lines (don't contain ",")
					continue;
				}
				byte[] hash = new byte[20];
				
				String[] values = line.split(",");
				for (int i = 0; i < 20; i++) {
					hash[i] = Byte.valueOf(values[i]);
				}
				hashes[hashesPosition] = hash;
				hashesPosition++;
			}
			bufferedReader.close();
			listInput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashes;
	}
}
