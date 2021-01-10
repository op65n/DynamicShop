# DynamicShop (Coming soon!)
Dynamic shop Minecraft 1.16.x plugin with advanced capabilities, based on the real economy.
![Cover Art](https://op65n.tech/i/2021-01-07-00-44-58.png)

Version: `1.0.0-R0.1-Alpha`<br>
Native API: `Paper-1.16.4-R0.1-SNAPSHOT`<br>
Source code: <a href="https://github.com/op65n/DynamicShop">github.com/op65n/DynamicShop</a><br>
Wiki: <a href="https://op65n.tech/wiki/">op65n.tech/wiki</a><br>
Developer: `SebbaIndustries` <br>

Special thanks to (Minecraft IGN): `zaraamelie` & `TeamsInsane` for helping with the project.


[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![GitHub issues](https://img.shields.io/github/issues/SebbaIndustries/DynamicShop)](https://github.com/SebbaIndustries/DynamicShop/issues)
![Java CI with Gradle](https://github.com/op65n/DynamicShop/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=master)

How To (Server Owners)
------
This is a plugin build on PaperAPI, you can run it on Spigot but use Paper for best experience.<br>

Download DynamicShop here: <a href="#">TODO setup download site</a>

<b>Installation:</b> 
- Place DynamicShop-VERSION.jar (`DynamicShop-v0.1.6.jar`) file into plugins folder
- Start the server, plugin will generate `DynamicShop` directory with files:
  * `README.md`
  * `configuration.toml`
  * `messages.js`
- Stop the server after everything has been loaded
- Open and configure the plugin to your needs.
- Start the server and enjoy the plugin!

<b>Message configuration:</b>
<br>All messages are stored in `messages.js`, you can change them to your liking.


## Features

Directory structure:
```
DynamicShop
├── messages.toml
├── configuration.toml
│
├── categories
│   ├── blocks.toml
│   ├── drops.toml
│   ├── food.toml
│   ├── miscellaneous.toml
│   └── ores.toml
│
├── gui
│   ├── buy_page.toml
│   ├── main_page.toml
│   ├── sell_page.toml
│   └── store_page.toml
│
└── statistics

```

## Commands & permissions

`/shop`
 - permission: `N/A`
 - description: Opens GUI for the shop, players can browse/sell/buy items from it. /shop is primary command for the plugin.

`/buy <amount> <item>`
 - permission: `N/A`
 - description: Buy items directly from the shop without the GUI.
 
`/sell <amount> <item>`
 - permission: `N/A`
 - description: Sell items directly to the shop without the GUI.
 
`/price <amount> <item>`
 - permission: `N/A`
 - description: Check price of items directly from the shop without the GUI. 
 
`/adminshop`
 - permission: `N/A`
 - description: TODO: add description.