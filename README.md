# MultiConfig
An easy solution for Bukkit/Spigot plugins to share config files

How to use step-by-step:
1. Add MultiConfig to your project's build path and as a dependency in your plugin.yml

2. Optional: Create the following method in your main class:
```
public static Main getInstance() {
  return Main.getPlugin(Main.class);
}
```
(Assuming your main class is called "Main", otherwise substitude that for the class name) 

This method will be used in the following steps.
  
3. Use `MultiConfig.saveConfig(getInstance().getResource(configPath), savePath)` to register a default config into MultiConfig.
	
	3.1 configPath is the relative path (aka starting from your plugin's folder) of the config.
  
	3.2 savePath is the path under which the config file will be saved inside the MultiConfig folder, aswell as accessed using the `getConfig(path)` method.

4. Use `MultiConfig.saveConfig(path)` to save a non-default config. `path` is the relative path of the file from the MultiConfig folder and will again be used with the `getConfig(path)` method.

5. Use `MultiConfig.getConfig(path)` to access a previously saved config.

Example code from my RPGSystem:
```
public static Main getInstance() {
  return Main.getPlugin(Main.class);
}

@Override
public void onLoad() {
  MultiConfig.saveConfig(getInstance().getResource("language/lang_default.yml"), "rpgsystem/language/lang_default.yml"); // save the default language file
  MultiConfig.saveConfig("rpgsystem/language/lang_german.yml");	// save the german language file located in plugins/MultiConfig/rpgsystem/language/
	
  System.out.println(MultiConfig.getConfig("rpgsystem/language/lang_default.yml").getString("ability.dodge")); // print out a string from the config
}
```
