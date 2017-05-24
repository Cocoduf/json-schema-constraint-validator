package com.cocoduf.jscv;

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

        });
    }

    private static String[] array(String... args) {
        return args;
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