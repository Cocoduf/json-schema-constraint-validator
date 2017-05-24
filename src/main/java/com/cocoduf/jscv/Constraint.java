package com.cocoduf.jscv;

/**
 * Created by cocoduf on 17-05-17.
 */
public class Constraint {
    private ConstraintCore core;
    private String sourceField;
    private String targetField;

    Constraint(String typeName, String sourceField, String target) {

    }

    public String getSourceField() {
        return sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
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

    public String getTargetField() {
        return targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }
}