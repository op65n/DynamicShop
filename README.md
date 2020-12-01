# DynamicShop (Coming soon!)
Dynamic shop Minecraft 1.16.x plugin with advanced capabilities, based on the real economy.
<br>
Version: `1.0-SNAPSHOT`<br>
Native API: `Paper-1.16.4-R0.1-SNAPSHOT`<br>
Source code: <a href="https://github.com/SebbaIndustries/DynamicShop">github.com/SebbaIndustries/DynamicShop</a><br>
Wiki: <a href="#">TODO: Plugin Wiki here</a><br>
Developer: `SebbaIndustries` <br>


[![license](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)
![Java CI with Maven](https://github.com/SebbaIndustries/DynamicShop/workflows/Java%20CI%20with%20Maven/badge.svg)
[![GitHub issues](https://img.shields.io/github/issues/SebbaIndustries/DynamicShop)](https://github.com/SebbaIndustries/DynamicShop/issues)

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
- DynamicShop
  * `README.md`
  * `configuration.toml`
  * `messages.js`
  - shop
    * `shop_configuration.toml`
    - categories
      * `%item_name%.js`
    - statistics
      * `%item_name%_stat.js`
    - gui
      * `main.js`
      * `store_page.toml`
      * `transaction_page.toml`

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