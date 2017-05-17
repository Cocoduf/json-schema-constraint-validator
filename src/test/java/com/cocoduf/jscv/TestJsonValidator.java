package com.cocoduf.jscv;

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
    public void testValidateJson() {
        JsonValidator jsonValidator = new JsonValidator();
        try {

            jsonValidator.setSchema(getFileResource("constraints.json"));
            Assert.assertEquals(true, jsonValidator.validateJson(getFileResource("data.json")));

        } catch(Exception e) {}
    }

    @Test
    public void testSetSchemaNullFile() {
        JsonValidator jsonValidator = new JsonValidator();
        try {

            jsonValidator.setSchema(new File("irrelevant"));
            Assert.assertEquals(true, false);

        } catch(Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    public void testSetSchemaInvalidJson() {
        JsonValidator jsonValidator = new JsonValidator();
        try {

            jsonValidator.setSchema(getFileResource("invalid.json"));
            Assert.assertEquals(true, false);

        } catch(Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
