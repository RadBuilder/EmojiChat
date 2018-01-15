package io.github.radbuilder.emojichat.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The emoji replacement variant.
 *
 * @author RadBuilder
 * @version 1.7
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
	private int id;
	/**
	 * The pack variant's SHA1 hash.
	 */
	private byte[] hash;
	/**
	 * The pack variant's URL.
	 */
	private String url;
	
	/**
	 * Creates a new emoji pack variant with the specified id.
	 *
	 * @param id The id associated with the emoji pack variant.
	 */
	EmojiPackVariant(int id) {
		this.id = id;
		this.hash = byteHash(id);
		this.url = "https://github.com/RadBuilder/EmojiChat/releases/download/v1.7/EmojiChat." + id + ".ResourcePack.v1.7.zip";
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
	public byte[] getHash() {
		return hash;
	}
	
	/**
	 * Gets the variant's url.
	 *
	 * @return The variant's url.
	 */
	public String getUrl() {
		return url;
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
	private byte[] byteHash(int id) {
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
				if (currentLine == id) {
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
