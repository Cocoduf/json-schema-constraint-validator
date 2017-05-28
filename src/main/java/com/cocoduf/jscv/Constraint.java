package com.cocoduf.jscv;

/**
 * Created by cocoduf on 17-05-17.
 */
public class Constraint {
    private ConstraintType type;
    private String fieldsType;
    private JsonPointer sourceField;
    private JsonPointer targetField;

    Constraint(String typeName, String sourceFieldType, JsonPointer sourceField, JsonPointer targetField) {
        setType(typeName);
        this.fieldsType = sourceFieldType;
        this.sourceField = sourceField;
        this.targetField = targetField;
    }

    public String getFieldsType() {
        return fieldsType;
    }

    public void setFieldsType(String fieldsType) {
        this.fieldsType = fieldsType;
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

    public void setType(String text) throws IllegalArgumentException {
            this.type = ConstraintType.getFromText(text);
    }

    public String toString() {
        return sourceField + " " + type + " " + targetField;
    }
}