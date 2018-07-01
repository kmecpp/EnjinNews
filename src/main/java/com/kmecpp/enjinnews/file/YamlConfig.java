//package com.kmecpp.enjinnews.file;
//
//import java.io.File;
//import java.util.List;
//
//import org.bukkit.configuration.file.YamlConfiguration;
//import org.bukkit.util.Consumer;
//
//import com.kmecpp.enjinnews.Logger;
//import com.kmecpp.enjinnews.util.FileUtil;
//
//public class YamlConfig extends YamlConfiguration {
//
//	private final File file;
//	private boolean corrupt;
//	private boolean modified;
//
//	// FROM FILE PATH
//	/**
//	 * Creates a new YamlConfig from the given file path. Searching in the
//	 * PLUGIN_ROOT directory. This will not create the file if it doesn't exist.
//	 * 
//	 * @param filePath
//	 *            the path to the yml configuration file
//	 */
//	public YamlConfig(String filePath) {
//		this(Directory.PLUGIN, filePath);
//	}
//
//	/**
//	 * Creates a new YamlConfig from the given file path. Searching in the
//	 * specified {@link Directory}. This will not create the file if it doesn't
//	 * exist.
//	 * 
//	 * @param directory
//	 *            the directory to search
//	 * @param filePath
//	 *            the file's path
//	 */
//	public YamlConfig(Directory directory, String filePath) {
//		this(new File(directory == Directory.ROOT ? "" : directory + File.separator + filePath));
//	}
//
//	// FROM FILE
//	/**
//	 * Creates a new YamlConfig from the given file. Creating it if specified
//	 * 
//	 * @param file
//	 *            the file to get
//	 * @param createNotExists
//	 *            whether or not to create the file if it doesn't exists
//	 */
//	public YamlConfig(File file) {
//		this.file = file;
//		this.options().copyDefaults(true);
//		FileUtil.createFile(file);
//		loadFile();
//	}
//
//	/**
//	 * Erases the contents of the file
//	 */
//	public void eraseKeys() {
//		for (String key : getKeys(false)) {
//			delete(key);
//		}
//	}
//
//	public void delete(String path) {
//		set(path, null);
//	}
//
//	@Override
//	public void set(String path, Object value) {
//		super.set(path, value);
//		modified = true;
//	}
//
//	/**
//	 * Reloads the file from the disk
//	 */
//	public void loadFile() {
//		try {
//			super.load(file);
//			corrupt = false;
//		} catch (Exception e) {
//			corrupt = true;
//			throw new RuntimeException("Failed to load yaml configuration!", e);
//		}
//	}
//
//	/**
//	 * Saves the YAML configuration to disk
//	 */
//	public void save() {
//		if (corrupt) {
//			Logger.error("Refused to override corrupt YAML! Please reload the file!");
//			return;
//		} else if (!modified) {
//			return;
//		}
//
//		this.options().copyDefaults(true);
//		try {
//			this.save(file);
//			modified = false;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @return the full absolute path to the YAML file
//	 */
//	public String getFilePath() {
//		return file.getAbsolutePath();
//	}
//
//	public void forEach(Consumer<String> keyConsumer) {
//		for (String key : getKeys(false)) {
//			try {
//				keyConsumer.accept(key);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	public void addToList(String key, String value) {
//		List<String> list = getStringList(key);
//		list.add(value);
//		set(key, list);
//	}
//
//	// PERSISTENT VALUES
//	public String getPersistentString(String path, String def) {
//		return getPersistentObject(path, def);
//	}
//
//	public List<String> getPersistentStringList(String path, List<String> def) {
//		return getPersistentObject(path, def);
//	}
//
//	public int getPersistentInt(String path, int def) {
//		return getPersistentObject(path, def);
//	}
//
//	public float getPersistentFloat(String path, float def) {
//		return getPersistentObject(path, def);
//	}
//
//	public double getPersistentDouble(String path, double def) {
//		return getPersistentObject(path, def);
//	}
//
//	public boolean getPersistentBoolean(String path, boolean def) {
//		return getPersistentObject(path, def);
//	}
//
//	public <T> T getPersistentObject(String path, T def) {
//		return getPersistentObject(path, def, false);
//	}
//
//	@SuppressWarnings("unchecked")
//	public <T> T getPersistentObject(String path, T def, boolean save) { //The existing method this.get(path, def) will not set the default value, only return it
//		if (this.contains(path)) {
//			Object val = this.get(path);
//			if (val.getClass().isAssignableFrom(def.getClass())) {
//				return (T) val;
//			} else {
//				throw new RuntimeException("Invalid data type! Expected: '" + def.getClass().getSimpleName() + "' found: " + val.getClass().getSimpleName());
//			}
//		} else {
//			this.set(path, def);
//			if (save) {
//				this.save();
//			}
//			return def;
//		}
//	}
//
//	public void setDefault(String path, Object def) {
//		if (!this.contains(path)) {
//			this.set(path, def);
//		}
//	}
//}
