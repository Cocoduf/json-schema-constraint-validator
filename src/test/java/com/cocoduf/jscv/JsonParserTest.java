package com.cocoduf.jscv;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Map;
import java.util.Set;

/**
 * Created by cocoduf on 17-05-11.
 */
public class JsonParserTest {

    private File getFileResource(String fileName) {
        return new File(getClass().getClassLoader().getResource(fileName).getFile());
    }

    private JsonObject getJsonObject(String fileName) {
        JsonParser parser = new JsonParser();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        Reader reader = new InputStreamReader(inputStream);
        return parser.parse(reader).getAsJsonObject();
    }

    @Test
    public void testJsonSchemaValidator() {
        try {
            File schemaFile = getFileResource("constraints.json");
            File jsonFile = getFileResource("data.json");
            Assert.assertEquals(true, ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (Exception e) {
            Assert.assertEquals(true,false);
        }
    }

    @Test
    public void testLoadJsonResources() {
        JsonObject dataRootObject = getJsonObject("data.json");
        JsonObject constraintsRootObject = getJsonObject("constraints.json");
    }

    @Test
    public void testLoopOverDataEntrySet() {
        JsonObject rootObject = getJsonObject("data.json");
        Set<Map.Entry<String, JsonElement>> entrySet = rootObject.entrySet();
        Assert.assertEquals(entrySet.size(), 3);
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            Assert.assertNotEquals(entry.getValue(), null);
        }
    }
}
