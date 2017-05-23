package com.cocoduf.jscv;

import com.google.gson.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by cocoduf on 17-05-11.
 */
public class TestLab {

    private File getFileResource(String fileName) throws FileNotFoundException {
        URL fileUrl = getClass().getClassLoader().getResource(fileName);
        if (fileUrl != null) {
            return new File(fileUrl.getFile());
        } else {
            throw new FileNotFoundException("Resource "+fileName+" not found.");
        }
    }

    private JsonObject getJsonObjectFromFile(File file) throws FileNotFoundException, JsonParseException {
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream);
        JsonParser parser = new JsonParser();
        return parser.parse(reader).getAsJsonObject();
    }

    private void handleException(Exception e) {
        System.out.println(e);
    }

    @Test
    public void testJsonSchemaValidator() {
        try {
            File schemaFile = getFileResource("constraints.json");
            File jsonFile = getFileResource("data.json");
            Assert.assertEquals(true, ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (Exception e) {
            handleException(e);
            Assert.fail();
        }
    }

    @Test
    public void testLoadJsonResources() {
        try {
            File dataFile = getFileResource("data.json");
            JsonObject dataRootObject = getJsonObjectFromFile(dataFile);
            File constraintsFile = getFileResource("constraints.json");
            JsonObject constraintsRootObject = getJsonObjectFromFile(constraintsFile);
        }  catch (Exception e) {
            handleException(e);
            Assert.fail();
        }
    }

    @Test
    public void testGetNullJsonResource() {
        try {
            File dataFile = getFileResource("null.json");
            Assert.fail();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Test
    public void testLoopOverDataEntrySet() {
        try {
            File dataFile = getFileResource("data.json");
            JsonObject dataRootObject = getJsonObjectFromFile(dataFile);
            Set<Map.Entry<String, JsonElement>> entrySet = dataRootObject.entrySet();
            Assert.assertEquals(entrySet.size(), 3);
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                Assert.assertNotEquals(entry.getValue(), null);
            }
        } catch (Exception e) {
            handleException(e);
            Assert.fail();
        }
    }

    private Set<Map.Entry<String, JsonObject>> getJsonObjects(JsonObject e) {
        Set<Map.Entry<String, JsonElement>> rawEntrySet = e.entrySet();
        Set<Map.Entry<String, JsonObject>> jsonObjectsEntrySet = new HashSet<>();
        for (Map.Entry<String, JsonElement> entry : rawEntrySet) {
            if (entry.getValue().isJsonObject()) {
                jsonObjectsEntrySet.add(new AbstractMap.SimpleEntry<>(entry.getKey(), (JsonObject) entry.getValue()));
            }
        }
        return jsonObjectsEntrySet;
    }

    private void recursive(JsonObject o) {
        if (o.has("properties")) {
            JsonObject properties = o.get("properties").getAsJsonObject();
            Set<Map.Entry<String, JsonObject>> entrySet = getJsonObjects(properties);
            for (Map.Entry<String, JsonObject> entry : entrySet) {
                JsonObject prop = entry.getValue();
                System.out.println(entry.getKey());
                if (prop.has("constraints")) {
                    System.out.println(prop.get("constraints").getAsJsonArray().toString());
                }
                recursive(prop);
            }
        }
    }

    @Test
    public void testGatherConstraints() {
        try {
            File constraintsFile = getFileResource("constraints.json");
            JsonObject cons_rootObject = getJsonObjectFromFile(constraintsFile);

            recursive(cons_rootObject);

        } catch (Exception e) {
            handleException(e);
            Assert.fail();
        }
    }
}
