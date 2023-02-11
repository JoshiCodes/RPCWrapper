package de.joshizockt.rpcwrapper.util.config;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlConfig  {

    private File file;
    private Yaml yaml;
    private Map<String, Object> data;

    public YamlConfig(File file) {
        this.file = file;
        init(file);
        if(file.exists()) updateData();
    }

    public void init(File file) {
        yaml = new Yaml();
    }

    public void writeToFile(PrintWriter writer) {
        if(data == null) return;
        yaml.dump(data, writer);
    }

    public void updateData() {
        try {
            data = yaml.load(new FileInputStream(getFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Object> getData() {
        return new HashMap<>(data);
    }

    public void set(String key, Object o) {
        if(data == null) data = new HashMap<>();
        data.put(key, o);
    }

    public Object get(String key) {
        String[] args = key.split("\\.");
        Map<String, Object> map = data;
        if(map != null) {
            for(int i = 0; i < (args.length-1); i++) {
                String arg = args[i];
                if(map.containsKey(arg)) {
                    Object o = map.get(arg);
                    if(o == null) continue;
                    if(o instanceof LinkedHashMap linkedHashMap) {
                        map = linkedHashMap;
                    }
                }
            }
            String arg = args[args.length-1];
            if(map.containsKey(arg)) return map.get(arg);
        }
        return null;
    }

    /**
     * Get an Object with any class from the Config. Returns the def variable if nothing is found.
     * @param key The key with which the object is associated with
     * @param def The fallback-Object which is used, when the object is null.
     * @return the found Object or the def-object if nothing is found.
     */
    public Object get(String key, Object def) {
        Object o = get(key);
        if(o == null) return def;
        return o;
    }

    /**
     * Get a String from the Config. Returns the def variable if nothing is found.
     * @param key The key with which the string is associated with
     * @return the found string or the def-string if nothing is found.
     * @throws ClassCastException when the Object of 'key' is no instance of a String.
     */
    public String getString(String key) {
        return (String) get(key, key);
    }


    /**
     * Get a String from the Config. Returns the def variable if nothing is found.
     * @param key The key with which the string is associated with
     * @param def The fallback-string which is used, when the string is null.
     * @return the found string or the def-string if nothing is found.
     * @throws ClassCastException when the Object of 'key' is no instance of a String.
     */
    public String getString(String key, String def) {
        return (String) get(key, def);
    }

    /**
     * Get a boolean from the Config. Returns the false if nothing is found.
     * @param key The key with which the boolean is associated with
     * @return the found boolean or false if nothing is found.
     * @throws ClassCastException when the Object of 'key' is no instance of a boolean.
     */
    public boolean getBoolean(String key) {
        return (boolean) get(key, false);
    }

    /**
     * Get a boolean from the Config. Returns the def variable if nothing is found.
     * @param key The key with which the boolean is associated with
     * @param def The fallback-boolean which is used, when the boolean is not found.
     * @return the found boolean or the def-boolean if nothing is found.
     * @throws ClassCastException when the Object of 'key' is no instance of a boolean.
     */
    public boolean getBoolean(String key, boolean def) {
        return (boolean) get(key, def);
    }

    /**
     * Get an int from the Config. Returns 0 if nothing is found.
     * @param key The key with which the int is associated with
     * @return the found int or 0 if nothing is found.
     * @throws ClassCastException when the Object of 'key' is no instance of an int.
     */
    public int getInt(String key) {
        return (int) get(key, false);
    }

    /**
     * Get an int from the Config. Returns the def variable if nothing is found.
     * @param key The key with which the int is associated with
     * @param def The fallback-int which is used, when the int is null.
     * @return the found int or the def-int if nothing is found.
     * @throws ClassCastException when the Object of 'key' is no instance of an int.
     */
    public int getInt(String key, int def) {
        return (int) get(key, def);
    }

    /**
     * Copies the resource File outside the working directory.
     * @throws NullPointerException if no File was found in the resources.
     */
    public void copyDefaults() {
        if(file.exists()) return;
        InputStream in = getClass().getClassLoader().getResourceAsStream(file.getPath());
        if(in == null) {
            throw new NullPointerException("There was no resource File found named '" + file.getPath() + "'.");
        }
        if(!file.exists()) {
            try {
                if(file.getParentFile() != null && !file.getParentFile().exists()) file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                try {
                    in.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            }
        }
        try {
            Files.copy(in, Paths.get(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            try {
                in.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        updateData();
    }

    /**
     * Saves the Configuration into the given File
     * @return true if the File was written, false if not.
     */
    public boolean save() {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        writeToFile(writer);
        updateData();
        return true;
    }

    public File getFile() {
        return file;
    }

}