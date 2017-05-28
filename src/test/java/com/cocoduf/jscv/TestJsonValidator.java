package com.cocoduf.jscv;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by cocoduf on 17-05-28.
 */
public abstract class TestJsonValidator {

    protected String fieldType;

    protected File getFileResource(String fileName) throws FileNotFoundException {
        URL fileUrl = getClass().getClassLoader().getResource(fileName);
        if (fileUrl != null) {
            return new File(fileUrl.getFile());
        } else {
            throw new FileNotFoundException("Resource "+fileName+" not found.");
        }
    }

    protected boolean validate(String constraintType) throws FileNotFoundException, IOException, ProcessingException {
        JsonValidator jsonValidator = new JsonValidator();
        jsonValidator.setSchema(getFileResource("schemas/"+fieldType+"/"+constraintType+".json"));
        return jsonValidator.validateJson(getFileResource("data/"+fieldType+".json"));
    }

    protected void error() {
        throw new Error("fail!");
    }

}
