package com.cocoduf.jscv;

import com.google.gson.JsonParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by cocoduf on 17-05-16.
 */
public class TestJsonValidatorErrors extends TestJsonValidator {

    @Test
    public void testValidateJsonFailureIrreleventData() {
        JsonValidator jsonValidator = new JsonValidator();
        try {

            jsonValidator.setSchema(getFileResource("schemas/irrelevent.json"));
            Assert.assertEquals(false, jsonValidator.validateJson(getFileResource("data/irrelevant.json")));

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testValidateJsonFailureUnknownConstraint() {
        JsonValidator jsonValidator = new JsonValidator();
        try {

            jsonValidator.setSchema(getFileResource("schemas/unknown_type.json"));
            Assert.assertEquals(false, jsonValidator.validateJson(getFileResource("data/unknown_type.json")));

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void testSetSchemaFileNotFound() throws Exception {
        JsonValidator jsonValidator = new JsonValidator();
        jsonValidator.setSchema(new File(""));
    }

    @Test(expected = JsonParseException.class)
    public void testSetSchemaMalformedJson() throws Exception {
        JsonValidator jsonValidator = new JsonValidator();
        jsonValidator.setSchema(getFileResource("data/malformed.json"));
    }

}
