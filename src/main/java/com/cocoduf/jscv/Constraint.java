package com.cocoduf.jscv;

import com.google.gson.JsonObject;

/**
 * Created by cocoduf on 17-05-17.
 */
public class Constraint {
    private ConstraintType type;
    private String sourceFieldType;
    private JsonPointer sourceField;
    private JsonPointer targetField;

    Constraint(String typeName, String sourceFieldType, JsonPointer sourceField, JsonPointer targetField) {
        setType(typeName);
        this.sourceFieldType = sourceFieldType;
        this.sourceField = sourceField;
        this.targetField = targetField;
    }

    public String getSourceFieldType() {
        return sourceFieldType;
    }

    public void setSourceFieldType(String sourceFieldType) {
        this.sourceFieldType = sourceFieldType;
    }

    public JsonPointer getSourceField() {
        return sourceField;
    }

    public void setSourceField(JsonPointer sourceField) {
        this.sourceField = sourceField;
    }

    public JsonPointer getTargetField() {
        return targetField;
    }

    public void setTargetField(JsonPointer targetField) {
        this.targetField = targetField;
    }

    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }

    public void setType(String text) {
        this.type = ConstraintType.getFromText(text);
    }
}