package com.cocoduf.jscv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cocoduf on 17-05-29.
 */
public class TestJsonValidatorBoolean extends TestJsonValidator {

    @Before
    public void init() {
        fieldType = "boolean";
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

}
