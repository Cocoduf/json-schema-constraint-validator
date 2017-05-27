package com.cocoduf.jscv;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.*;

/**
 * Created by cocoduf on 17-05-16.
 */
public class JsonValidator {

    private String schemaText;
    private JsonObject schemaRootObject;
    private ConstraintsValidator validator;

    public JsonValidator() {

    }

    /**
     *
     * @param schemaText
     * @throws JsonParseException
     */
    public void setSchema(String schemaText) throws JsonParseException {
        this.schemaText = schemaText;
        this.schemaRootObject = getJsonObjectFromText(schemaText);
        validator = new ConstraintsValidator(schemaRootObject);
    }

    /**
     *
     * @param schemaFile
     * @throws FileNotFoundException
     * @throws JsonParseException
     */
    public void setSchema(File schemaFile) throws FileNotFoundException, JsonParseException {
        setSchema(getJsonTextFromFile(schemaFile));
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
                return ValidationUtils.isJsonValid(schemaText, jsonText) && validateConstraints(schemaText, jsonText);
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
        return validateJson(getJsonTextFromFile(jsonFile));
    }

    /******************************************************************************************************************/

    /**
     *
     * @param schemaText
     * @param jsonText
     * @return whether the constraints validation has been successful.
     */
    private boolean validateConstraints(String schemaText, String jsonText) {
        return validator.isJsonValid(getJsonObjectFromText(jsonText));
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
     * @param text
     * @return a JsonObject of the JSON from the text.
     * @throws JsonParseException
     */
    private JsonObject getJsonObjectFromText(String text) throws JsonParseException {
        return new JsonParser().parse(text).getAsJsonObject();
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
}
