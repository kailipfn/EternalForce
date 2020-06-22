package me.kailip.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.util.Properties;

public class Config {
    private String name;
    private File file = new File("");
    private Properties properties;
    public Config(String name) {
        this.name = name;

        File file = new File("");
        File f = new File(file.getAbsolutePath() + File.separator + this.name + ".yml");

        try {
            if(!f.exists()) {
                f.createNewFile();
            }
            Properties prop = new Properties();
            InputStream istream = new FileInputStream(f);
            prop.load(istream);
            this.properties = prop;
            this.file = f;
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void save(File f) {
        try {
            properties.store(new FileOutputStream(f), "設定変更");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setString(String key,String a) {
        properties.setProperty(key, a);
        save(file);
    }

    public void setInt(String key,int a) {
        properties.setProperty(key, a + "");
        save(file);
    }

    public void setBoolean(String key,boolean a) {
        properties.setProperty(key, a + "");
        save(file);
    }

    public void remove(String key) {
        properties.remove(key);
        save(file);
    }

    public Object get(String key) {
        try {
            Properties prop = properties;
            InputStream istream = new FileInputStream(file);
            prop.load(istream);
            return prop.get(key);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public int getInt(String key) {
        String result = properties.getProperty(key);
        if (NumberUtils.isNumber(result)) {
            return Integer.parseInt(result);
        } else {
            return 0;
        }
    }

    public boolean getBoolean(String key) {
        String result = properties.getProperty(key);
        if (result.equals("true") || result.equals("false")) {
            return Boolean.parseBoolean(result);
        } else {
            return false;
        }
    }

    public Config getConfig() {
        return this;
    }

    public Properties getProperties() {return properties;}

    public File getConfigFile() {
        return this.file;
    }

    public String getName() {
        return name;
    }
}
