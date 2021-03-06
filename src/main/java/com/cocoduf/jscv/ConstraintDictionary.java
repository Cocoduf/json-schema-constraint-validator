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

    public static boolean validate(Constraint c, JsonElement source, JsonElement target, String valueFormat) {
        if (cores.isEmpty()) {
            generateCores();
        }
        return getConstraintCoreFromConstraintType(c.getType()).check(source, target, c.getSourceFieldType(), c.getTargetFieldType(), valueFormat);
    }

    public static ConstraintCore getConstraintCoreFromConstraintType(ConstraintType type) {
        return cores.get(type);
    }

    /******************************************************************************************************************/

    private static void generateCores() {

        cores.put(ConstraintType.valueEqualTo, new ConstraintCore(
                array("boolean","number","string","array","object"),
                array("boolean","number","string","array","object"), true, null) {
            @Override
            public boolean isDataValid(Boolean source, Boolean target) {
                return source.equals(target);
            }
            @Override
            public boolean isDataValid(Float source, Float target) {
                return source.equals(target);
            }
            @Override
            public boolean isDataValid(String source, String target) {
                return source.equals(target);
            }
            @Override
            public boolean isDataValid(JsonArray source, JsonArray target) {
                return source.equals(target);
            }
            @Override
            public boolean isDataValid(JsonObject source, JsonObject target) {
                return source.equals(target);
            }
        });

        cores.put(ConstraintType.valueNotEqualTo, new ConstraintCore(
                array("boolean","number","string","array","object"),
                array("boolean","number","string","array","object"), true, null) {
            @Override
            public boolean isDataValid(Boolean source, Boolean target) {
                return !source.equals(target);
            }
            @Override
            public boolean isDataValid(Float source, Float target) {
                return !source.equals(target);
            }
            @Override
            public boolean isDataValid(String source, String target) {
                return !source.equals(target);
            }
            @Override
            public boolean isDataValid(JsonArray source, JsonArray target) {
                return !source.equals(target);
            }
            @Override
            public boolean isDataValid(JsonObject source, JsonObject target) {
                return !source.equals(target);
            }
        });

        cores.put(ConstraintType.valueGreaterThan, new ConstraintCore(
                array("number"),
                array("number"), true) {
            @Override
            public boolean isDataValid(Float source, Float target) {
                return source>target;
            }
        });

        cores.put(ConstraintType.valueGreaterThanOrEqualTo, new ConstraintCore(
                array("number"),
                array("number"), true) {
            @Override
            public boolean isDataValid(Float source, Float target) {
                return source>=target;
            }
        });

        cores.put(ConstraintType.valueLesserThan, new ConstraintCore(
                array("number"),
                array("number"), true) {
            @Override
            public boolean isDataValid(Float source, Float target) {
                return source<target;
            }
        });

        cores.put(ConstraintType.valueLesserThanOrEqualTo, new ConstraintCore(
                array("number"),
                array("number"), true) {
            @Override
            public boolean isDataValid(Float source, Float target) {
                return source<=target;
            }
        });

        cores.put(ConstraintType.priorTo, new ConstraintCore(
                array("string"),
                array("string"), true, array("date-time","date")) {
            @Override
            public boolean isDataValid(String source, String target) {
                return new JsonDateTime(source).isPriorTo(new JsonDateTime(target));
            }
        });

        cores.put(ConstraintType.subsequentTo, new ConstraintCore(
                array("string"),
                array("string"), true, array("date-time","date")) {
            @Override
            public boolean isDataValid(String source, String target) {
                return new JsonDateTime(source).isSubsequentTo(new JsonDateTime(target));
            }
        });

        cores.put(ConstraintType.inArray, new ConstraintCore(
                array("number","string"),
                array("array"), false, null) {
            @Override
            public boolean isDataValid(JsonElement source, JsonArray target, String sourceType) {
                for (int i = 0; i < target.size(); i++) {
                    JsonElement e = target.get(i);
                    if (e.equals(source)) {
                        return true;
                    }
                }
                return false;
            }
        });

        cores.put(ConstraintType.notInArray, new ConstraintCore(
                array("number","string"),
                array("array"), false, null) {
            @Override
            public boolean isDataValid(JsonElement source, JsonArray target, String sourceType) {
                for (int i = 0; i < target.size(); i++) {
                    JsonElement e = target.get(i);
                    if (e.equals(source)) {
                        return false;
                    }
                }
                return true;
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
        private boolean matchingTypes = false;
        private String[] requiredFormat = null;

        public ConstraintCore(String[] sourceTypes, String[] targetTypes) {
            this.allowedSourceFieldTypes = sourceTypes;
            this.allowedTargetFieldTypes = targetTypes;
        }

        public ConstraintCore(String[] sourceTypes, String[] targetTypes, Boolean matchingTypes) {
            this(sourceTypes, targetTypes);
            this.matchingTypes = matchingTypes;
        }

        public ConstraintCore(String[] sourceTypes, String[] targetTypes, String[] requiredFormat) {
            this(sourceTypes, targetTypes);
            this.requiredFormat = requiredFormat;
        }

        public ConstraintCore(String[] sourceTypes, String[] targetTypes, Boolean matchingTypes, String[] requiredFormat) {
            this(sourceTypes, targetTypes, matchingTypes);
            this.requiredFormat = requiredFormat;
        }

        // For the clueless external calls
        public boolean validateData(JsonElement source, JsonElement target, String sourceType, String targetType) {
            switch (targetType) {
                case "boolean":
                    return isDataValid(source, asBoolean(target), sourceType);
                case "number":
                    return isDataValid(source, asFloat(target), sourceType);
                case "string":
                    return isDataValid(source, asString(target), sourceType);
                case "array":
                    return isDataValid(source, asJsonArray(target), sourceType);
                case "object":
                    return isDataValid(source, asJsonObject(target), sourceType);
                default:
                    return true;
            }
        }

        public boolean validateDataOfMatchingTypes(JsonElement source, JsonElement target, String fieldsType) {
            switch (fieldsType) {
                case "boolean":
                    return isDataValid(asBoolean(source), asBoolean(target));
                case "number":
                    return isDataValid(asFloat(source), asFloat(target));
                case "string":
                    return isDataValid(asString(source), asString(target));
                case "array":
                    return isDataValid(asJsonArray(source), asJsonArray(target));
                case "object":
                    return isDataValid(asJsonObject(source), asJsonObject(target));
                default:
                    return true;
            }
        }

        // This block has type-specific implementations
        public boolean isDataValid(Boolean source, Boolean target) { throw new IllegalStateException("Constraint not implemented for source: boolean & target: boolean"); }
        public boolean isDataValid(Float source, Float target) { throw new IllegalStateException("Constraint not implemented for source: number & target: number"); }
        public boolean isDataValid(String source, String target) { throw new IllegalStateException("Constraint not implemented for source: string & target: string"); }
        public boolean isDataValid(JsonArray source, JsonArray target) { throw new IllegalStateException("Constraint not implemented for source: array & target: array"); }
        public boolean isDataValid(JsonObject source, JsonObject target) { throw new IllegalStateException("Constraint not implemented for source: object & target: object"); }
        public boolean isDataValid(JsonElement source, Boolean target, String sourceType) { throw new IllegalStateException("Constraint not implemented for source: unknown & target: boolean"); }
        public boolean isDataValid(JsonElement source, Float target, String sourceType) { throw new IllegalStateException("Constraint not implemented for source: unknown & target: boolean"); }
        public boolean isDataValid(JsonElement source, String target, String sourceType) { throw new IllegalStateException("Constraint not implemented for source: unknown & target: boolean"); }
        public boolean isDataValid(JsonElement source, JsonArray target, String sourceType) { throw new IllegalStateException("Constraint not implemented for source: unknown & target: boolean"); }
        public boolean isDataValid(JsonElement source, JsonObject target, String sourceType) { throw new IllegalStateException("Constraint not implemented for source: unknown & target: boolean"); }

        private boolean arrayHas(String[] array, String value) {
            boolean result = false;
            for (String s : array) {
                result = result || s.equals(value);
            }
            return result;
        }

        private boolean verifyFieldsValidity(String sourceType, String targetType, String valueFormat) {
            return arrayHas(allowedSourceFieldTypes, sourceType) && arrayHas(allowedTargetFieldTypes, targetType)
                    && (!matchingTypes||sourceType.equals(targetType)) && (requiredFormat==null||arrayHas(requiredFormat, valueFormat));
        }

        public boolean check(JsonElement sourceValue, JsonElement targetValue, String sourceType, String targetType, String valueFormat) {
            return verifyFieldsValidity(sourceType, targetType, valueFormat)
                    && (matchingTypes ? validateDataOfMatchingTypes(sourceValue, targetValue, sourceType) : validateData(sourceValue, targetValue, sourceType, targetType));
        }

        public boolean asBoolean(JsonElement element) {
            return element.getAsJsonPrimitive().getAsBoolean();
        }
        public float asFloat(JsonElement element) {
            return element.getAsJsonPrimitive().getAsNumber().floatValue();
        }
        public String asString(JsonElement element) {
            return element.getAsJsonPrimitive().getAsString();
        }
        public JsonArray asJsonArray(JsonElement element) {
            return element.getAsJsonArray();
        }
        public JsonObject asJsonObject(JsonElement element) {
            return element.getAsJsonObject();
        }
    }
}

enum ConstraintType {
    valueEqualTo,
    valueNotEqualTo,
    valueGreaterThan,
    valueGreaterThanOrEqualTo,
    valueLesserThan,
    valueLesserThanOrEqualTo,
    inArray,
    notInArray,
    priorTo,
    subsequentTo;

    public static ConstraintType getFromText(String typeName) throws IllegalArgumentException {
        try {
            return ConstraintType.valueOf(typeName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Constraint type unknown : " + typeName);
        }
    }
}