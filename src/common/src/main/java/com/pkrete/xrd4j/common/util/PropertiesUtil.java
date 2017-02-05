package com.pkrete.xrd4j.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers methods for loading properties files. All the loaded files
 * are cached, which means that they are read from disk only once.
 *
 * @author Petteri Kivimäki
 */
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private HashMap<String, Properties> loadedProps;
    private static PropertiesUtil ref;

    /**
     * Constructs and initializes a new PropertiesUtil object. Should never be
     * used outside this class.
     */
    private PropertiesUtil() {
        this.loadedProps = new HashMap<>();
    }

    /**
     * Returns a reference to a PropertiesUtil instance. If the instance does
     * not exist yet, it's first initialized.
     *
     * @return PropertiesUtil object
     */
    public static PropertiesUtil getInstance() {
        if (ref == null) {
            ref = new PropertiesUtil();
        }
        return ref;
    }

    /**
     * Loads a properties file from the classpath.
     *
     * @param propsName path to the properties file
     * @return Properties properties that were loaded from the given file or
     * null, if loading the properties fails
     */
    public Properties load(String propsName) {
        return PropertiesUtil.getInstance().load(propsName, true);
    }

    /**
     * Loads a properties file from the classpath or from file system.
     *
     * @param propsName path to the properties file
     * @param fromClasspath when true the file is searched from classpath,
     * otherwise directly from the file system
     * @return Properties properties that were loaded from the given file or
     * null, if loading the properties fails
     */
    public Properties load(String propsName, boolean fromClasspath) {
        logger.debug("Load properties from file : \"" + propsName + "\".");
        // If file is already loaded, return it from cache
        if (loadedProps.containsKey(propsName)) {
            logger.debug("File already loaded. Use cache.");
            return loadedProps.get(propsName);
        }

        Properties props = null;
        InputStream is = null;
        try {
            if (fromClasspath) {
                logger.debug("Load properties from classpath.");
                is = this.getClass().getResourceAsStream(propsName);
            } else {
                logger.debug("Load properties from file system.");
                is = new FileInputStream(propsName);
            }
            props = new Properties();
            props.load(is);
            loadedProps.put(propsName, props);
            logger.debug("Properties were succesfully loaded.");
        } catch (Exception e) {
            logger.error("Loading properties failed.");
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    logger.error("Closing input stream failed.");
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
        return props;
    }
}
