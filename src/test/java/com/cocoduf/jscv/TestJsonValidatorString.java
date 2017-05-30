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

}
