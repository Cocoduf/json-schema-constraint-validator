package com.cocoduf.jscv;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cocoduf on 17-05-23.
 */
public class ConstraintDictionary {

    private static Map<ConstraintType, ConstraintCore> cores = new HashMap<>();

    private ConstraintDictionary() {}

    public static boolean isDataValid(Constraint c, JsonElement source, JsonElement target) {
        if (cores.isEmpty()) {
            generateCores();
        }
        return getConstraintCoreFromConstraintType(c.getType()).check(c.getFieldsType(), source, target);
    }

    public static ConstraintCore getConstraintCoreFromTypeName(String typeName) {
        return cores.get(ConstraintType.getFromText(typeName));
    }

    public static ConstraintCore getConstraintCoreFromConstraintType(ConstraintType type) {
        System.out.println("LOG> ConstraintDictionary.getConstraintCoreFromConstraintType");
        return cores.get(type);
    }

    /******************************************************************************************************************/

    private static void generateCores() {
        System.out.println("LOG> ConstraintDictionary.generateCores");
        cores.put(ConstraintType.valueEqualTo, new ConstraintCore(
                array("boolean","number","string","array","object"),
                array("boolean","number","string","array","object")) {
            @Override
            public boolean isDataValid(Float source, Float target) {
                System.out.println("LOG> ConstraintCore.isDataValid " + Float.toString(source) + " " + Float.toString(target));
                return source.equals(target);
            }
        });
    }

    /******************************************************************************************************************/

    private static String[] array(String... args) {
        return args;
    }

    public static class ConstraintCore {
        private String[] allowedSourceFieldTypes;
        private String[] allowedTargetFieldTypes;
        private String requiredFormat;

        public ConstraintCore(String[] sourceTypes, String[] targetTypes) {
            this.allowedSourceFieldTypes = sourceTypes;
            this.allowedTargetFieldTypes = targetTypes;
        }

        // For the clueless external calls
        public boolean isDataValid(String fieldsType, JsonElement source, JsonElement target) {
            System.out.println("LOG> ConstraintCore.isDataValid " + fieldsType);
            switch (fieldsType) {
                case "boolean":
                    return isDataValid(source.getAsJsonPrimitive().getAsBoolean(), target.getAsJsonPrimitive().getAsBoolean());
                case "number":
                    return isDataValid(source.getAsJsonPrimitive().getAsNumber().floatValue(), target.getAsJsonPrimitive().getAsNumber().floatValue());
                case "string":
                    return isDataValid(source.getAsJsonPrimitive().getAsString(), target.getAsJsonPrimitive().getAsString());
                case "array":
                    return isDataValid(source.getAsJsonArray(), source.getAsJsonArray());
                case "object":
                    return isDataValid(source.getAsJsonObject(), source.getAsJsonObject());
                default:
                    return true;
            }
        }

        // This block has type-specific implementations
        public boolean isDataValid(Boolean source, Boolean target) { return false; };
        public boolean isDataValid(Float source, Float target) { return false; };
        public boolean isDataValid(String source, String target) { return false; };
        public boolean isDataValid(JsonArray source, JsonArray target) { return false; };
        public boolean isDataValid(JsonObject source, JsonObject target) { return false; };
        // end block

        private boolean arrayHas(String[] array, String value) {
            System.out.println("LOG> ConstraintCore.arrayHas " + value);
            boolean result = false;
            for (String s : array) {
                result = result || s.equals(value);
            }
            return result;
        }

        private boolean verifyFieldsValidity(String fieldsType, JsonElement source, JsonElement target) {
            return arrayHas(allowedSourceFieldTypes, fieldsType) && arrayHas(allowedTargetFieldTypes, fieldsType);
        }

        public boolean check(String fieldsType, JsonElement source, JsonElement target) {
            return verifyFieldsValidity(fieldsType, source, target)
                    && isDataValid(fieldsType, source, target);
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