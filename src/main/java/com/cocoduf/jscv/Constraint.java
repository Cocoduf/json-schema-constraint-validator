package com.cocoduf.jscv;

/**
 * Created by cocoduf on 17-05-17.
 */
public class Constraint {
    private ConstraintCore core;
    private JsonPointer sourceField;
    private JsonPointer targetField;

    Constraint(String typeName, JsonPointer sourceField, JsonPointer targetField) {
        setSourceField(sourceField);
        setTargetField(targetField);
        setCore(typeName);
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

    public ConstraintCore getCore() {
        return core;
    }

    public void setCore(ConstraintCore core) {
        this.core = core;
    }

    public void setCore(String typeName) {
        this.core = ConstraintDictionary.getConstraintCoreFromTypeName(typeName);
    }
}