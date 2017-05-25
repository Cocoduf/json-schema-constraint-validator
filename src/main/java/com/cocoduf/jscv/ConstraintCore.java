package com.cocoduf.jscv;

import com.google.gson.JsonObject;

/**
 * Created by cocoduf on 17-05-23.
 */
public class ConstraintCore {

    private String[] allowedSourceFieldTypes;
    private String[] allowedTargetFieldTypes;
    private String requiredFormat;

    public ConstraintCore(String[] sourceTypes, String[] targetTypes) {
        this.allowedSourceFieldTypes = sourceTypes;
        this.allowedTargetFieldTypes = targetTypes;
    }

    public ConstraintCore(String[] sourceTypes, String[] targetTypes, String format) {
        this(sourceTypes, targetTypes);
        this.requiredFormat = format;
    }

    public boolean someMethodThatChecksIfTheConstraintIsRespected(JsonObject source, JsonObject target) {
        return false; // needs type-specific implementation override
    }

    private boolean someMethodThatVerifiesIfSourceAndTargetAreValidFields(JsonObject source, JsonObject target) {
        return false;
    }

    public boolean someMethodThatRunsTheWholeProcess(JsonObject source, JsonObject target) {
        return someMethodThatVerifiesIfSourceAndTargetAreValidFields(source, target)
            && someMethodThatChecksIfTheConstraintIsRespected(source, target);
    }

}