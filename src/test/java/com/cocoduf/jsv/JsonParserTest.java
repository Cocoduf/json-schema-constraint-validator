package com.cocoduf.jsv;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 * Created by cocoduf on 17-05-11.
 */
public class JsonParserTest {

    @Test
    public void testGsonExample() {
        JsonParser parser = new JsonParser();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
        Reader reader = new InputStreamReader(inputStream);
        JsonObject rootObject = parser.parse(reader).getAsJsonObject();
        Assert.assertEquals(rootObject.get("price").getAsInt(), 750);
    }

    @Test
    public void testValidatingResources() {

        File schemaFile = new File(getClass().getClassLoader().getResource("constraints.json").getFile());
        File jsonFile = new File(getClass().getClassLoader().getResource("data.json").getFile());

        try {
            Assert.assertEquals(true, ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (Exception e) {
            Assert.assertEquals(1,2);
        }
    }
}
