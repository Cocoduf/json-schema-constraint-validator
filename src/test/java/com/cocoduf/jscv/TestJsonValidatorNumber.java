package com.cocoduf.jscv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cocoduf on 17-05-28.
 */
public class TestJsonValidatorNumber extends TestJsonValidator {

    @Before
    public void init() {
        fieldType = "number";
    }

    @Test
    public void testValueEqualTo() {
        try {
            Assert.assertEquals(true, validate("valueEqualTo","a","b"));
            Assert.assertEquals(false, validate("valueEqualTo","a","c"));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

    @Test
    public void testValueNotEqualTo() {
        try {
            Assert.assertEquals(true, validate("valueNotEqualTo","a","c"));
            Assert.assertEquals(false, validate("valueNotEqualTo","a","b"));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

    @Test
    public void testValueGreaterThan() {
        try {
            Assert.assertEquals(true, validate("valueGreaterThan","c","a"));
            Assert.assertEquals(false, validate("valueGreaterThan","a","b"));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

    @Test
    public void testValueGreaterThanOrEqualTo() {
        try {
            Assert.assertEquals(true, validate("valueGreaterThanOrEqualTo","c","a"));
            Assert.assertEquals(true, validate("valueGreaterThanOrEqualTo","a","b"));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

    @Test
    public void testValueLesserThan() {
        try {
            Assert.assertEquals(true, validate("valueLesserThan","a","c"));
            Assert.assertEquals(false, validate("valueGreaterThan","a","b"));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

    @Test
    public void testValueLesserThanOrEqualTo() {
        try {
            Assert.assertEquals(true, validate("valueLesserThanOrEqualTo","a","c"));
            Assert.assertEquals(true, validate("valueGreaterThanOrEqualTo","a","b"));
        } catch(Exception e) {
            System.out.println(e);
            error();
        }
    }

}
