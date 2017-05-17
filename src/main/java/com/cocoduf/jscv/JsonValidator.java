package com.cocoduf.jscv;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.URL;

/**
 * Created by cocoduf on 17-05-16.
 */
public class JsonValidator {

    private String schemaText;
    private JsonObject schemaRootObject;

    public JsonValidator() {

    }

    /**
     *
     * @param file
     * @return a JsonObject of the JSON from the file.
     * @throws FileNotFoundException
     * @throws JsonParseException
     */
    private JsonObject getJsonObjectFromFile(File file) throws FileNotFoundException, JsonParseException {
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream);
        JsonParser parser = new JsonParser();
        return parser.parse(reader).getAsJsonObject();
    }

    /**
     *
     * @param file
     * @return a string of the JSON from the file.
     * @throws FileNotFoundException
     * @throws JsonParseException
     */
    private String getJsonTextFromFile(File file) throws FileNotFoundException, JsonParseException {
        return getJsonObjectFromFile(file).toString();
    }

    /**
     *
     * @param schemaText
     * @throws JsonParseException
     */
    public void setSchema(String schemaText) throws JsonParseException {
        this.schemaText = schemaText;
        this.schemaRootObject = new JsonParser().parse(this.schemaText).getAsJsonObject();
    }

    /**
     *
     * @param schemaFile
     * @throws FileNotFoundException
     * @throws JsonParseException
     */
    public void setSchema(File schemaFile) throws FileNotFoundException, JsonParseException {
        this.schemaText =  getJsonTextFromFile(schemaFile);
        this.schemaRootObject = getJsonObjectFromFile(schemaFile);
    }

    /**
     *
     * @param jsonText
     * @return whether the JSON Schema Validator validation has been successful. This does not test the constraints.
     * @throws IllegalStateException
     * @throws ProcessingException
     * @throws IOException
     */
    public boolean validateJson(String jsonText) throws IllegalStateException, ProcessingException, IOException {
        if (schemaText != null) {
            {
                return ValidationUtils.isJsonValid(schemaText, jsonText);
            }
        } else {
            throw new IllegalStateException("Missing schema.");
        }
    }

    /**
     *
     * @param jsonFile
     * @return whether the JSON Schema Validator validation has been successful. This does not test the constraints.
     * @throws IllegalStateException
     * @throws ProcessingException
     * @throws IOException
     */
    public boolean validateJson(File jsonFile) throws IllegalStateException, ProcessingException, IOException {
        if (schemaText != null) {
            {
                return ValidationUtils.isJsonValid(schemaText, getJsonTextFromFile(jsonFile));
            }
        } else {
            throw new IllegalStateException("Missing schema.");
        }
    }

}
