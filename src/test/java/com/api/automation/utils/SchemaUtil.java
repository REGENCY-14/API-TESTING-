package com.api.automation.utils;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for handling JSON schema operations
 * Provides methods to load schema files from resources
 */
public class SchemaUtil {

    private static final String SCHEMA_DIR = "schemas";

    /**
     * Load JSON schema file from resources
     *
     * @param schemaFileName the name of the schema file (e.g., "post-schema.json")
     * @return the schema file as a File object
     */
    public static File getSchemaFile(String schemaFileName) {
        try {
            URL schemaUrl = SchemaUtil.class.getClassLoader()
                    .getResource(SCHEMA_DIR + "/" + schemaFileName);
            
            if (schemaUrl == null) {
                throw new RuntimeException("Schema file not found: " + schemaFileName);
            }
            
            return new File(schemaUrl.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Error loading schema file: " + schemaFileName, e);
        }
    }

    /**
     * Load JSON schema as string from resources
     *
     * @param schemaFileName the name of the schema file (e.g., "post-schema.json")
     * @return the schema content as a string
     */
    public static String getSchemaAsString(String schemaFileName) {
        try {
            URL schemaUrl = SchemaUtil.class.getClassLoader()
                    .getResource(SCHEMA_DIR + "/" + schemaFileName);
            
            if (schemaUrl == null) {
                throw new RuntimeException("Schema file not found: " + schemaFileName);
            }
            
            return new String(Files.readAllBytes(Paths.get(schemaUrl.toURI())), 
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error loading schema file: " + schemaFileName, e);
        }
    }
}
