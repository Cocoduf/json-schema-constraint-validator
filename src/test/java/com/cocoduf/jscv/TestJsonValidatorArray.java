package com.cocoduf.jscv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cocoduf on 17-05-28.
 */
public class TestJsonValidatorArray extends TestJsonValidator {

    @Before
    public void init() {
        fieldType = "array";
    }

    @Test
    public void testValueEqualTo() {
        try {
            Assert.assertEquals(true, validate("valueEqualTo","a","b"));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

    @Test
    public void testValueNotEqualTo() {
        try {
            Assert.assertEquals(true, validate("valueNotEqualTo","a","c"));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

    @Test
    public void testInArrayNotInArray() {
        try {
            JsonValidator jsonValidator = new JsonValidator();
            jsonValidator.setSchema(getFileResource("schemas/inArray.json"));
            Assert.assertEquals(true, jsonValidator.validateJson(getFileResource("data/inArray.json")));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

}
