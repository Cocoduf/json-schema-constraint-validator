package com.cocoduf.jscv;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.gson.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by cocoduf on 17-05-11.
 */
public class JsonParserTest {

    private File getFileResource(String fileName) throws NullPointerException {
        URL fileUrl = getClass().getClassLoader().getResource(fileName);
        if (fileUrl != null) {
            return new File(fileUrl.getFile());
        } else {
            throw new NullPointerException("Resource "+fileName+" not found.");
        }
    }

    private JsonObject getJsonObject(File file) throws FileNotFoundException, JsonParseException {
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream);
        JsonParser parser = new JsonParser();
        return parser.parse(reader).getAsJsonObject();
    }

    @Test
    public void testJsonSchemaValidator() {
        try {
            File schemaFile = getFileResource("constraints.json");
            File jsonFile = getFileResource("data.json");
            Assert.assertEquals(true, ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (NullPointerException e) {
            System.out.println("NullPointerException : " + e.getMessage());
            Assert.fail();
        } catch (ProcessingException e) {
            System.out.println("ProcessingException : " + e.getMessage());
            Assert.fail();
        } catch (IOException e) {
            System.out.println("IOException : " + e.getMessage());
            Assert.fail();;
        }
    }

    @Test
    public void testLoadJsonResources() {
        try {
            File dataFile = getFileResource("data.json");
            JsonObject dataRootObject = getJsonObject(dataFile);
            File constraintsFile = getFileResource("constraints.json");
            JsonObject constraintsRootObject = getJsonObject(constraintsFile);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException : " + e.getMessage());
            Assert.fail();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException : " + e.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void testGetNullJsonObject() {
        try {
            File dataFile = getFileResource("null.json");
            JsonObject dataRootObject = getJsonObject(dataFile);
            Assert.fail();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException : " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException : " + e.getMessage());
        }
    }

    @Test
    public void testLoopOverDataEntrySet() {
        try {
            File dataFile = getFileResource("data.json");
            JsonObject dataRootObject = getJsonObject(dataFile);
            Set<Map.Entry<String, JsonElement>> entrySet = dataRootObject.entrySet();
            Assert.assertEquals(entrySet.size(), 3);
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                Assert.assertNotEquals(entry.getValue(), null);
            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException : " + e.getMessage());
            Assert.fail();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException : " + e.getMessage());
            Assert.fail();
        } catch (JsonParseException e) {
            System.out.println("JsonParseException : " + e.getMessage());
            Assert.fail();
        }
    }
}
