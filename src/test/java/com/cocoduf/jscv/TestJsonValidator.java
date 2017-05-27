package com.cocoduf.jscv;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by cocoduf on 17-05-16.
 */
public class TestJsonValidator {

    private File getFileResource(String fileName) throws FileNotFoundException {
        URL fileUrl = getClass().getClassLoader().getResource(fileName);
        if (fileUrl != null) {
            return new File(fileUrl.getFile());
        } else {
            throw new FileNotFoundException("Resource "+fileName+" not found.");
        }
    }

    @Test
    public void testValidateJsonSuccess() {
        JsonValidator jsonValidator = new JsonValidator();
        try {

            jsonValidator.setSchema(getFileResource("constraints.json"));
            Assert.assertEquals(true, jsonValidator.validateJson(getFileResource("data.json")));

        } catch(Exception e) {}
    }

    @Test
    public void testValidateJsonFailureIrreleventData() {
        JsonValidator jsonValidator = new JsonValidator();
        try {

            jsonValidator.setSchema(getFileResource("constraints.json"));
            Assert.assertEquals(false, jsonValidator.validateJson(getFileResource("irrelevant.json")));

        } catch(Exception e) {}
    }

    @Test
    public void testValidateJsonFailureWrongData() {
        JsonValidator jsonValidator = new JsonValidator();
        try {

            jsonValidator.setSchema(getFileResource("constraints.json"));
            Assert.assertEquals(false, jsonValidator.validateJson(getFileResource("wrong_data.json")));

        } catch (Exception e) {}
    }

    @Test(expected = FileNotFoundException.class)
    public void testSetSchemaFileNotFound() throws Exception {
        JsonValidator jsonValidator = new JsonValidator();
        jsonValidator.setSchema(new File(""));
    }

    @Test(expected = JsonParseException.class)
    public void testSetSchemaMalformedJson() throws Exception {
        JsonValidator jsonValidator = new JsonValidator();
        jsonValidator.setSchema(getFileResource("malformed.json"));
    }

}
