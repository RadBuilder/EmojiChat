package io.github.radbuilder.emojichat.hooks;

/**
 * EmojiChatHook interface.
 *
 * @author RadBuilder
 * @since 1.4
 * @version 1.4
 */
public interface EmojiChatHook {
	/**
	 * If the hook is enabled or not.
	 *
	 * @return True if the hook is enabled, false otherwise.
	 */
	boolean isEnabled();
	
	/**
	 * Gets the name of the hook.
	 *
	 * @return The name of the hook.
	 */
	String getName();
	
	/**
	 * Gets the {@link io.github.radbuilder.emojichat.hooks.EmojiChatHookType}.
	 *
	 * @return The {@link io.github.radbuilder.emojichat.hooks.EmojiChatHookType}.
	 */
	EmojiChatHookType getHookType();
	
	/**
	 * Disables/unhooks the hook.
	 */
	void disable();
}
