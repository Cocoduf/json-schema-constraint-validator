package com.cocoduf.jscv;

import java.util.ArrayList;

/**
 * Created by cocoduf on 17-05-17.
 */
public class Constraint {
    private String field;
    private ConstraintType type;
    private String target;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = ConstraintType.get(type);
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}

enum ConstraintType {
    requiredIfNull,
    requiredIfnotNull,
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

    public static ConstraintType get(String text) {
        return ConstraintType.valueOf(text);
    }
}