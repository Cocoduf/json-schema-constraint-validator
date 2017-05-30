package com.cocoduf.jscv;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URL;

/**
 * Created by cocoduf on 17-05-28.
 */
public abstract class TestJsonValidator {

    //TODO method: generic string json constraint for each field type, called with args constraint type and compared fields name instead of ALL OF THOSE JSON FILES

    protected String fieldType;

    protected File getFileResource(String fileName) throws FileNotFoundException {
        URL fileUrl = getClass().getClassLoader().getResource(fileName);
        if (fileUrl != null) {
            return new File(fileUrl.getFile());
        } else {
            throw new FileNotFoundException("Resource "+fileName+" not found.");
        }
    }

    protected String getFileResourceAsString(String fileName) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(getFileResource(fileName));
        Reader reader = new InputStreamReader(inputStream);
        JsonParser parser = new JsonParser();
        return parser.parse(reader).getAsJsonObject().toString();
    }

    protected String getFormattedJsonData(String fileName, String constraintType, String sourceName, String targetName) throws FileNotFoundException {
        return getFileResourceAsString(fileName).replaceAll("CONSTRAINT_TYPE", constraintType).replaceAll("SOURCE_NAME", sourceName).replaceAll("TARGET_NAME", targetName);
    }

    protected boolean validate(String constraintType, String sourceName, String targetName) throws FileNotFoundException, IOException, ProcessingException {
        JsonValidator jsonValidator = new JsonValidator();
        jsonValidator.setSchema(getFormattedJsonData("schemas/"+fieldType+".json", constraintType, sourceName, targetName));
        return jsonValidator.validateJson(getFileResource("data/"+fieldType+".json"));
    }

    protected void error() {
        throw new Error("fail!");
    }

}
