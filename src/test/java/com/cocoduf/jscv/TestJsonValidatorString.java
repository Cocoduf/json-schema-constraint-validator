package com.cocoduf.jscv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cocoduf on 17-05-28.
 */
public class TestJsonValidatorString extends TestJsonValidator {

    @Before
    public void init() {
        fieldType = "string";
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
    public void testPriorTo() {
        try {
            JsonValidator jsonValidator = new JsonValidator();
            jsonValidator.setSchema(getFormattedJsonData("schemas/date-time.json", "priorTo", "a", "c"));
            Assert.assertEquals(true, jsonValidator.validateJson(getFileResource("data/date-time.json")));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

    @Test
    public void testSubsequentTo() {
        try {
            JsonValidator jsonValidator = new JsonValidator();
            jsonValidator.setSchema(getFormattedJsonData("schemas/date-time.json", "subsequentTo", "a", "b"));
            Assert.assertEquals(true, jsonValidator.validateJson(getFileResource("data/date-time.json")));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

}
