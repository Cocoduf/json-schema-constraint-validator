package com.cocoduf.jscv;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    public static ConstraintCore getConstraintCoreFromConstraintType(ConstraintType type) {
        return cores.get(type);
    }

    public static void main(String[] args) {
        if (cores.isEmpty()) {
            generateCores();
        }
    }

    public static boolean isDataValid(Constraint c, JsonElement source, JsonElement target) {
        return getConstraintCoreFromConstraintType(c.getType()).isDataValid(c.getFieldsType(), source, target);
    }

    /******************************************************************************************************************/

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