[![Build Status](https://travis-ci.org/RadBuilder/EmojiChat.svg?branch=master)](https://travis-ci.org/RadBuilder/EmojiChat)
[![GitHub last commit](https://img.shields.io/github/last-commit/RadBuilder/EmojiChat.svg)](https://github.com/RadBuilder/EmojiChat/commits/master)
[![version](https://img.shields.io/github/release/RadBuilder/EmojiChat.svg?colorB=1565C0)](https://github.com/RadBuilder/EmojiChat/releases/latest)
[![Discord](https://discordapp.com/api/guilds/394749667226943489/widget.png)](https://discord.gg/faUbQ6B)
[![view on SpigotMC](https://img.shields.io/badge/view%20on-spigotmc-orange.svg)](https://www.spigotmc.org/resources/emojichat.50955/)
[![wiki](https://img.shields.io/badge/go%20to-wiki-blue.svg)](https://github.com/RadBuilder/EmojiChat/wiki)
## EmojiChat
EmojiChat is a simple, configurable, opensource plugin that adds emojis to your chat, signs, and more!

[![ChatExample](https://i.imgur.com/wa0LeVd.png)](https://i.imgur.com/zJUVyst.png)  

In order for everyone to have the best experience possible, we have a few guidelines that everyone must follow.    
- For all things on GitHub, please make sure you follow the [code of conduct](.github/CODE_OF_CONDUCT.md).  
- When creating an issue, please make sure you're using the [issue template](.github/ISSUE_TEMPLATE/BUG_REPORT.md).  
- When submitting a pull request, please make sure you're using the [pull request template](.github/PULL_REQUEST_TEMPLATE.md).  
- When contributing, please make sure you check out the [contribution guidelines](.github/CONTRIBUTING.md).  

##Compiling
When compiling, you may recieve an error along the lines of:
> SunCertPathBuilderException: unable to find valid certification path to requested target

To fix, compile with Maven with the following extra flags:
> -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true