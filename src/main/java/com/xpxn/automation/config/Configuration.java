package com.xpxn.automation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class for handling system properties and properties files.
 */
public final class Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

    private Configuration() {
    }

    static {
        try {
            init();
        } catch (IOException e) {
            // Stuff went south, real fast. BAIL!
            logger.error("Configuration initialization failed.", e);
            System.exit(1);
        }
    }

    /**
     * Initializes the Configuration by preparing various properties files and system properties.
     * Loaded in the following order: defaults -&gt; platform-specific -&gt; user-definitions -&gt; system-derived
     * Value overrides occur in the following order of importance: null -&gt; empty string -&gt; non-empty string
     *
     * @throws IOException Properties files were not setup correctly.
     */
    private static void init() throws IOException {
        Properties baseline = load("test.properties");
        Properties custom = load("custom.properties", true);

        Properties tmp = flatten(baseline, custom, System.getProperties());

        Properties platform;
        try {
//            platform = load("profiles/" + new Property("app.profile", tmp) + ".properties");
        } catch (NullPointerException e) {
            throw new IllegalStateException("Must specify a platform profile to use.", e);
        }

        Properties squashed = flatten(
                baseline,
                custom
//                ,platform
        );
        // Add environment variables
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            squashed.put(entry.getKey(), entry.getValue());
        }
        // Any arguments pass via CLI will forcefully override the equivalent property, regardless of value
        // TODO: Better way of differentiating between maven properties and command line properties.
        //     Use unique names and combine them in here?
        squashed.putAll(System.getProperties());

        logger.debug("========== Begin Properties List ==========");
        Enumeration keys = squashed.keys();
        while (keys.hasMoreElements()) {
            String k = (String) keys.nextElement();
            String v = (String) squashed.get(k);
            if (v.length() > 40)
                v = v.substring(0, 37) + "...";
            logger.debug(k + "=" + v);
        }
        logger.debug("=========== End Properties List ===========");
        System.setProperties(squashed);

    }

    /**
     * Simple properties file loading wrapper with error handling
     *
     * @param file     Desired properties file to load
     * @param optional Allows skipping failed file loads (eg. they don't need to exist)
     * @return Properties object
     * @throws IOException Specified file does not exist or cannot be read
     */
    public static Properties load(String file, boolean optional) throws IOException {
        Properties loading = new Properties();
        if (file == null) return loading;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        try (InputStream resource = classLoader.getResourceAsStream(file)) {
            if (resource == null) {
                if (!optional) {
                    throw new IOException("Make sure the '" + file + "' file is present. ");
                }
            } else loading.load(resource);
        }
        return loading;
    }

    /**
     * Simple properties file loading wrapper with error handling
     * Loading a file with this method means it cannot fail loading, else throws an exception.
     *
     * @param file Desired properties file to load
     * @return Properties object
     * @throws IOException Specified file does not exist or cannot be read
     */
    public static Properties load(String file) throws IOException {
        return load(file, false);
    }

    /**
     * Combine multiple properties objects into a single one.
     *
     * @param sources Properties objects to combine
     * @return Resulting Properties object
     */
    private static Properties flatten(Properties... sources) {
        Properties target = new Properties();
        for (Properties source : sources) {
            for (String prop : source.stringPropertyNames()) {
                String value = source.getProperty(prop);
                String check = target.getProperty(prop);
                // source prop will never be null here, only check for existing null and 0 length
                if (check == null || check.length() == 0 || value.length() > 0)
                    // overwrite existing property if source prop is not and empty string or target prop is null
                    target.setProperty(prop, value);
            }
        }
        return target;
    }

    /**
     * Dynamically add properties to the default properties object.
     *
     * @param source Properties object to merge.
     */
    public static void extend(Properties source) {
        System.setProperties(flatten(System.getProperties(), source));
    }

    /**
     * Dynamically add properties to the default properties object from a properties file.
     *
     * @param source Properties file to load and merge.
     */
    public static void extend(String source) throws IOException {
        extend(load(source));
    }

    public static Property envBrowserName(){
        return new Property("env.browser.name");
    }

    public static Property appEnv(){
        return new Property("app.env");
    }

    public static Property appName (){
        return new Property("app.name");
    }

    public static Property envRemoteUrl(){
        return new Property("env.remote.url");
    }

}