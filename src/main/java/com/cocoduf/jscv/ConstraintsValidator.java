package com.cocoduf.jscv;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

/**
 * Created by cocoduf on 17-05-17.
 */
public class ConstraintsValidator {

    final String KEY_CONSTRAINT_TYPE = "type";
    final String KEY_TARGET_FIELD = "target";

    private JsonObject schemaRootObject;
    private JsonObject dataRootObject;
    private ArrayList<Constraint> constraints;

    public ConstraintsValidator(JsonObject schemaRootObject) {
        this.constraints = new ArrayList<>();
        setSchema(schemaRootObject);
    }

    public void setSchema(JsonObject schemaRootObject) throws IllegalArgumentException {
        this.schemaRootObject = schemaRootObject;
        this.constraints.clear();
        buildConstraints();
    }

    public boolean isJsonValid(JsonObject dataRootObject) {
        this.dataRootObject = dataRootObject;
        return isDataValid();
    }

    /******************************************************************************************************************/

    private boolean isDataValid() {
        boolean valid = true;
        for (Constraint constraint : constraints) {
            valid = valid && ConstraintDictionary.validate(
                    constraint,
                    getJsonElementFromPath(constraint.getSourceFieldPath(), dataRootObject),
                    getJsonElementFromPath(constraint.getTargetFieldPath(), dataRootObject),
                    getSchemaPropertyAttribute(constraint.getSourceFieldPath(), "format")
            );
        }
        return valid;
    }

    private JsonElement getJsonElementFromPath(JsonPointer path, JsonObject rootObject) throws IllegalArgumentException {
        JsonObject property = null;
        JsonElement value = null;
        String node;
        String[] nodes = path.toArray();
        for (int i = 0; i < nodes.length; i++) {
            node = nodes[i];

            if (node.equals("#")) {
                property = rootObject;
            } else if (property != null) {
                if (i != nodes.length-1) {
                    property = property.get(node).getAsJsonObject();
                } else {
                    value = property.get(node);
                }
            }
        }
        if (value == null) {
            throw new IllegalArgumentException("Path "+path+" is invalid");
        }
        return value;
    }

    private JsonPointer convertDataPathToSchemaPath(JsonPointer path) {
        return new JsonPointer(path.toString().replaceAll("/","/properties/"));
    }

    private String getSchemaPropertyAttribute(JsonPointer path, String attribute) {
        JsonObject property = getJsonElementFromPath(convertDataPathToSchemaPath(path), schemaRootObject).getAsJsonObject();
        if (property.has(attribute)) {
            return property.get(attribute).getAsString();
        } else {
            return null;
        }
    }

    private void buildConstraints() throws IllegalArgumentException {
        recursiveConstraintsBrowsing(schemaRootObject, new JsonPointer("#"));
    }

    private void makeConstraintsFromJson(JsonArray jsonConstraints, JsonPointer sourceFieldPath, String sourceFieldType) throws IllegalArgumentException {
        for (JsonElement constraintElement : jsonConstraints) {
            if (constraintElement.isJsonObject()) {
                JsonObject constraintObject = constraintElement.getAsJsonObject();
                if (constraintObject.has(KEY_CONSTRAINT_TYPE) && constraintObject.has(KEY_TARGET_FIELD)) {

                    String constraintType = constraintObject.get(KEY_CONSTRAINT_TYPE).getAsString();
                    JsonPointer targetFieldPath = new JsonPointer(constraintObject.get(KEY_TARGET_FIELD).getAsString());
                    String targetFieldType = getSchemaPropertyAttribute(targetFieldPath, "type");

                    Constraint c = new Constraint(constraintType, sourceFieldPath, targetFieldPath, sourceFieldType, targetFieldType);
                    constraints.add(c);

                } else {
                    throw new IllegalArgumentException("Bad constraint definition in " + sourceFieldPath);
                }
            } else {
                throw new IllegalArgumentException("Unexpected element in " + sourceFieldPath + ".constraints");
            }
        }
    }

    private void recursiveConstraintsBrowsing(JsonObject o, JsonPointer currentPath) throws IllegalArgumentException {
        if (o.has("properties")) {
            JsonObject properties = o.get("properties").getAsJsonObject();
            Set<Map.Entry<String, JsonObject>> entrySet = filterJsonObjectsEntriesOnly(properties);
            for (Map.Entry<String, JsonObject> entry : entrySet) {
                JsonObject property = entry.getValue();
                currentPath.push(entry.getKey());

                if (property.has("constraints") && property.get("constraints").isJsonArray()) {
                    JsonArray constraints = property.get("constraints").getAsJsonArray();
                    makeConstraintsFromJson(constraints, new JsonPointer(currentPath), property.get("type").getAsString());
                }

                recursiveConstraintsBrowsing(property, currentPath);
                currentPath.pop();
            }
        }
    }

    private Set<Map.Entry<String, JsonObject>> filterJsonObjectsEntriesOnly(JsonObject e) {
        Set<Map.Entry<String, JsonElement>> rawEntrySet = e.entrySet();
        Set<Map.Entry<String, JsonObject>> jsonObjectsEntrySet = new HashSet<>();
        for (Map.Entry<String, JsonElement> entry : rawEntrySet) {
            if (entry.getValue().isJsonObject()) {
                jsonObjectsEntrySet.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().getAsJsonObject()));
            }
        }
        return jsonObjectsEntrySet;
    }

}