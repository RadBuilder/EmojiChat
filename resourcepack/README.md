## Resource Pack Information

### Variants
There are two resource pack variants. The reasoning behind this is because of how the resource pack works: it replaces other characters in the set of unicode characters with emojis. The first variant replaces characters in the Korean character set, and the second variant replaces characters in the Chinese character set. This is why it's not possible for both Korean and Chinese to be used by players at the same time. The variant can be changed in the config with the `pack-variant` option, but note that it'll ruin anything with emojis already on them, such as signs.    

### HD vs SD
Because 1.13 no longer supports HD fonts, the option to have an SD pack was introduced. This can be changed in the config with the `pack-quality` option. The only difference between the two pack versions is the quality of emojis. For the HD pack, they're 768x768 pixels. For the SD pack, they're 256x256 pixels. If you're using a server configuration that allows any 1.13+ clients to join, you'll need to use the SD pack. Otherwise use the HD pack for better quality. The HD pack has a slightly larger file size, however it's minimal and shouldn't be a factor in determining which pack quality to choose.   

### glyph_sizes.bin
`glyph_sizes.bin` holds the left and right margins of each character according to the hex offset. The file can be opened in a hex editor such as [HxD](https://mh-nexus.de/en/hxd/). The first nibble holds the left margin, and the second nibble holds the right margin. Because emojis now fill an entire block (16x16 pixels) and no longer overlap the old character, `glyph_sizes.bin` is used to tell Minecraft to use the whole space. In this case, `0F` for the full 16 pixel width.  
*Note: Older versions of Minecraft experience a bug where `glyph_sizes.bin` isn't loaded when changing the resource pack. More information about it can be found on [Mojang's bug tracker](https://bugs.mojang.com/browse/MC-41270).*

### Want to contribute?
Awesome! Check out CONTRIBUTING in the root of the repository for more information.  