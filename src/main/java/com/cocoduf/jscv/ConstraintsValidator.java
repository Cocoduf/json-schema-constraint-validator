package com.cocoduf.jscv;

import com.google.gson.JsonObject;

/**
 * Created by cocoduf on 17-05-17.
 */
public class ConstraintsValidator {

    private JsonObject cons_rootObject;
    private JsonObject data_rootObject;

    public ConstraintsValidator(JsonObject cons) {
        cons_rootObject = cons;
    }

    public boolean isJsonValid(JsonObject data) {
        data_rootObject = data;
        return true;
    }

    public ConstraintsValidator setSchema(JsonObject cons) {
        cons_rootObject = cons;
        return this;
    }

}
