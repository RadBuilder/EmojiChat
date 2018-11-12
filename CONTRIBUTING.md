### Hello, and thank you for contributing to EmojiChat!  
In order for everyone to have the best experience possible, we have a few guidelines that everyone must follow.  
- For all things on GitHub, please make sure you follow the [code of conduct](CODE_OF_CONDUCT.md).    
- When creating an issue, please make sure you're using the [issue template](ISSUE_TEMPLATE.md).    
- When submitting a pull request, please make sure you're using the [pull request template](PULL_REQUEST_TEMPLATE.md).    

### Requirements
There are some specific requirements you need to follow when making and proposing changes to EmojiChat. This ensures order, consistency, and organization.  
*While some little requirements will probably be looked over, for the majority of these things, your PRs will be marked as invalid unless they're followed. Don't worry, they're not too hard :).* 
1. Fork the repository ([fork](https://github.com/RadBuilder/EmojiChat/fork));
2. Create a new branch with a description of the changes you're making (`git checkout -b super-brief-description`);
3. Make any changes you need, ensuring you add what you've changed in the commit message. If you're making big changes, use multiple commits for clarity (`git commit -a -m "Added 20 animal emoji"`);
4. Create a pull request into the version branch being worked on and fill out the template provided ([create a pull request](https://github.com/RadBuilder/EmojiChat/pulls));

#### Emoji Addition/Modification Requirements
1. They're something everyone can and will use (not just for you);  
2. It's legal to use (if applicable, such as a company logo);  
3. You're allowed to use and edit the icon (check the licence for the image you're grabbing);  
4. It's appropriate and legal (nothing that's illegal or clearly inappropriate. If you have to question it, it's probably not appropriate);  

If none of the above stop you, check out the READMEs in the assets and resourcepack folder for more information and guidance.

#### Hook Addition Requirements
1. The plugin to hook into has a public repo that can be added to Maven.
2. The plugin to hook into is best done from EmojiChat to that plugin and not the other way around. If a plugin is only grabbing emojis, it can be done in other ways: using one of the two placeholder plugins to grab the character (see [Hooks](https://github.com/RadBuilder/EmojiChat/wiki/Hooks)), using the EmojiHandler class, or by using the unicode character manually (although this isn't the best idea if you decide to switch pack variants). 