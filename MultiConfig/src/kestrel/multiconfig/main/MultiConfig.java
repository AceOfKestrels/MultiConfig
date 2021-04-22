package kestrel.multiconfig.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MultiConfig extends JavaPlugin {

	@Override
	public void onLoad() {
		savedConfigs = new HashMap<>();
		instance = MultiConfig.getPlugin(MultiConfig.class);
	}

	private static Map<String, YamlConfiguration> savedConfigs;
	private static MultiConfig instance;

	public static void saveConfig(InputStream config, String path) {
		File f = new File(instance.getDataFolder().getPath() + "/" + path);
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			try {
				Files.write(Paths.get(instance.getDataFolder().getPath() + "/" + path), inputStreamToBytes(config),
						StandardOpenOption.CREATE);
			} catch (IOException e) {
				System.out.println("MultiConfig Error: Config with path \"" + path
						+ "\" could not be created. The following exception was thrown:");
				e.printStackTrace();
				System.out.println("If the exception was not caused by an error in your plugin, please "
						+ "update to the newest version of MultiConfig or contact the author of MultiConfig if you are already using the most recent version!");
			}
		}
		saveConfig(path);
	}

	public static boolean saveConfig(String path) {
		File f = new File(instance.getDataFolder().getPath() + "/" + path);
		if (f.exists())
			savedConfigs.put(path, YamlConfiguration.loadConfiguration(f));
		return f.exists();
	}

	public static YamlConfiguration getConfig(String path) {
		if (savedConfigs.containsKey(path) || saveConfig(path))
			return savedConfigs.get(path);
		return null;
	}

	public static String getString(String key, String config) {
		YamlConfiguration file = getConfig(config);
		if (file != null && file.getString(key) != null)
			return file.getString(key);
		return "null";
	}

	private static byte[] inputStreamToBytes(InputStream is) throws IOException {
		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		return bytes;
	}

}
