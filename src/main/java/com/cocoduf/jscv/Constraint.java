package com.cocoduf.jscv;

/**
 * Created by cocoduf on 17-05-17.
 */
public class Constraint {
    private ConstraintType type;
    private JsonPointer sourceFieldPath;
    private JsonPointer targetFieldPath;
    private String sourceFieldType;
    private String targetFieldType;

    Constraint(String typeName, JsonPointer sourceFieldPath, JsonPointer targetFieldPath, String sourceFieldType, String targetFieldType) {
        setType(typeName);
        this.sourceFieldPath = sourceFieldPath;
        this.targetFieldPath = targetFieldPath;
        this.sourceFieldType = sourceFieldType;
        this.targetFieldType = targetFieldType;
    }

    public JsonPointer getSourceFieldPath() {
        return sourceFieldPath;
    }

    public void setSourceFieldPath(JsonPointer sourceFieldPath) {
        this.sourceFieldPath = sourceFieldPath;
    }

    public JsonPointer getTargetFieldPath() {
        return targetFieldPath;
    }

    public void setTargetFieldPath(JsonPointer targetFieldPath) {
        this.targetFieldPath = targetFieldPath;
    }

    public String getSourceFieldType() {
        return sourceFieldType;
    }

    public void setSourceFieldType(String sourceFieldType) {
        this.sourceFieldType = sourceFieldType;
    }

    public String getTargetFieldType() {
        return targetFieldType;
    }

    public void setTargetFieldType(String targetFieldType) {
        this.targetFieldType = targetFieldType;
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
        return sourceFieldPath + " " + type + " " + targetFieldPath;
    }
}