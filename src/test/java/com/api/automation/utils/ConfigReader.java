package com.api.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for reading configuration properties from config.properties file
 * Provides centralized access to test configuration values
 */
public class ConfigReader {

    private static final Logger LOGGER = Logger.getLogger(ConfigReader.class.getName());
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;

    // Private constructor to prevent instantiation
    private ConfigReader() {
        throw new IllegalStateException("Utility class - cannot be instantiated");
    }

    // Static block to load properties when class is loaded
    static {
        properties = new Properties();
        try (InputStream inputStream = ConfigReader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            
            if (inputStream == null) {
                LOGGER.log(Level.SEVERE, "Unable to find " + CONFIG_FILE);
                throw new RuntimeException("Configuration file " + CONFIG_FILE + " not found in classpath");
            }
            
            properties.load(inputStream);
            LOGGER.log(Level.INFO, "Successfully loaded configuration from " + CONFIG_FILE);
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading configuration file: " + e.getMessage(), e);
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    /**
     * Get a property value by key
     * @param key The property key
     * @return The property value, or null if not found
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.log(Level.WARNING, "Property '" + key + "' not found in configuration");
        }
        return value;
    }

    /**
     * Get a property value with a default fallback
     * @param key The property key
     * @param defaultValue The default value if property is not found
     * @return The property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key, defaultValue);
        if (value.equals(defaultValue)) {
            LOGGER.log(Level.INFO, "Using default value for property '" + key + "': " + defaultValue);
        }
        return value;
    }

    /**
     * Get the base URI for the API
     * @return The base URI
     */
    public static String getBaseUri() {
        return getProperty("base.uri");
    }

    /**
     * Get request timeout in milliseconds
     * @return The request timeout
     */
    public static int getRequestTimeout() {
        String timeout = getProperty("request.timeout", "30000");
        return Integer.parseInt(timeout);
    }

    /**
     * Get connection timeout in milliseconds
     * @return The connection timeout
     */
    public static int getConnectionTimeout() {
        String timeout = getProperty("connection.timeout", "10000");
        return Integer.parseInt(timeout);
    }

    /**
     * Get the default user ID for testing
     * @return The default user ID
     */
    public static int getDefaultUserId() {
        String userId = getProperty("default.user.id", "1");
        return Integer.parseInt(userId);
    }

    /**
     * Get the default post ID for testing
     * @return The default post ID
     */
    public static int getDefaultPostId() {
        String postId = getProperty("default.post.id", "1");
        return Integer.parseInt(postId);
    }

    /**
     * Get the log level
     * @return The log level
     */
    public static String getLogLevel() {
        return getProperty("log.level", "INFO");
    }
}
