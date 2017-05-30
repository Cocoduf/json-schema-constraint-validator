package com.cocoduf.jscv;

import com.google.gson.JsonParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by cocoduf on 17-05-16.
 */
public class TestJsonValidatorErrors extends TestJsonValidator {

    @Test
    public void testValidateFailureInvalidData() {
        JsonValidator jsonValidator = new JsonValidator();
        try {

            jsonValidator.setSchema(getFileResource("schemas/invalid.json"));
            Assert.assertEquals(false, jsonValidator.validateJson(getFileResource("data/invalid.json")));

        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateFailureUnknownConstraint() throws Exception {
        JsonValidator jsonValidator = new JsonValidator();
        jsonValidator.setSchema(getFileResource("schemas/unknown_type.json"));
        jsonValidator.validateJson(getFileResource("data/data.json"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateFailureIllegalPath() throws Exception {
        JsonValidator jsonValidator = new JsonValidator();
        jsonValidator.setSchema(getFileResource("schemas/illegal_path.json"));
        jsonValidator.validateJson(getFileResource("data/data.json"));
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
