package com.cocoduf.jscv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by cocoduf on 17-05-23.
 */
public class ConstraintDictionary {

    private static Map<ConstraintType, ConstraintCore> cores;

    private ConstraintDictionary() {}

    public static ConstraintCore getConstraintCoreFromTypeName(String typeName) {
        return cores.get(ConstraintType.getFromText(typeName));
    }

    public static void main(String[] args) {
        if (cores.isEmpty()) {
            generateCores();
        }
    }

    private static void generateCores() {
        cores.put(ConstraintType.requiredIfNull, new ConstraintCore(
                array("boolean","number","string","array","object"),
                array("boolean","number","string","array","object")) {
            @Override
            public boolean isDataValid(JsonObject source, JsonObject target) {
                return false;
            }
        });
    }

    private static String[] array(String... args) {
        return args;
    }

    /**********************THIS IS WRONG :) THIS IS WRONG :) THIS IS WRONG*********************************************/

    public static class ConstraintCore {
        private String[] allowedSourceFieldTypes;
        private String[] allowedTargetFieldTypes;
        private String requiredFormat;

        public ConstraintCore(String[] sourceTypes, String[] targetTypes) {
            this.allowedSourceFieldTypes = sourceTypes;
            this.allowedTargetFieldTypes = targetTypes;
        }

        boolean isDataValid(Boolean source, Boolean target) { return false; };
        boolean isDataValid(Float source, Float target) { return false; };
        boolean isDataValid(String source, String target) { return false; };
        boolean isDataValid(JsonArray source, JsonArray target) { return false; };
        boolean isDataValid(JsonObject source, JsonObject target) { return false; };

        private boolean verifyFieldsValidity(JsonObject source, JsonObject target) {
            return false;
        }
        public boolean check(JsonObject source, JsonObject target) {
            return verifyFieldsValidity(source, target)
                    && isDataValid(source, target);
        }
    }
}

enum ConstraintType {
    requiredIfNull,
    requiredIfNotNull,
    requiredIfTrue,
    requiredIfFalse,
    valueEqualTo,
    valueNotEqualTo,
    valueGreaterThan,
    valueGreaterThanOrEqualTo,
    valueLesserThan,
    valueLesserThanOrEqualTo,
    lengthEqualTo,
    lengthNotEqualTo,
    lengthGreaterThan,
    lengthGreaterThanOrEqualTo,
    lengthLesserThan,
    lengthLesserThanOrEqualTo,
    inArray,
    notInArray,
    priorTo,
    subsequentTo;

    public static ConstraintType getFromText(String typeName) {
        return ConstraintType.valueOf(typeName);
    }
}