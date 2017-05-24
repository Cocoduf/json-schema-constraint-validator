package com.cocoduf.jscv;

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



}