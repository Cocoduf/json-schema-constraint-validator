package com.cocoduf.jscv;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

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

    public void setSchema(JsonObject schemaRootObject) {
        this.schemaRootObject = schemaRootObject;
        this.constraints.clear();
        buildConstraints();
    }

    public boolean isJsonValid(JsonObject dataRootObject) {
        this.dataRootObject = dataRootObject;
        return validateData();
    }

    /******************************************************************************************************************/

    private boolean validateData() {
        for (Constraint c : constraints) {

        }

        return false;
    }

    private void buildConstraints() {
        recursiveConstraintsBrowsing(schemaRootObject, new JsonPointer("#"));
    }

    private void makeConstraintsFromJson(JsonArray jsonConstraints, JsonPointer sourceFieldPath, String sourceFieldType) throws IllegalArgumentException {
        for (JsonElement constraintElement : jsonConstraints) {
            if (constraintElement.isJsonObject()) {
                JsonObject constraintObject = constraintElement.getAsJsonObject();
                if (constraintObject.has(KEY_CONSTRAINT_TYPE) && constraintObject.has(KEY_TARGET_FIELD)) {

                    Constraint c = new Constraint(constraintObject.get(KEY_CONSTRAINT_TYPE).getAsString(), sourceFieldType, sourceFieldPath, new JsonPointer(constraintObject.get(KEY_TARGET_FIELD).getAsString()));
                    constraints.add(c);

                } else {
                    throw new IllegalArgumentException("Contrainte incomplète dans " + sourceFieldPath.toString() + ".constraints");
                }
            } else {
                throw new IllegalArgumentException("Erreur de syntaxe dans " + sourceFieldPath.toString() + ".constraints");
            }
        }
    }

    private void recursiveConstraintsBrowsing(JsonObject o, JsonPointer currentPath) {
        if (o.has("properties")) {
            JsonObject properties = o.get("properties").getAsJsonObject();
            Set<Map.Entry<String, JsonObject>> entrySet = filterJsonObjectsEntriesOnly(properties);
            for (Map.Entry<String, JsonObject> entry : entrySet) {
                JsonObject property = entry.getValue();
                currentPath.push(entry.getKey());

                if (property.has("constraints")) {
                    JsonArray constraints = property.get("constraints").getAsJsonArray();
                    makeConstraintsFromJson(constraints, currentPath, property.get("type").getAsString());
                }

                System.out.println(currentPath);
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