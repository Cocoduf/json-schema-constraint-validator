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
        System.out.println("LOG> ConstraintsValidator()");
        this.constraints = new ArrayList<>();
        setSchema(schemaRootObject);
    }

    public void setSchema(JsonObject schemaRootObject) throws IllegalArgumentException {
        System.out.println("LOG> ConstraintsValidator.setSchema");
        this.schemaRootObject = schemaRootObject;
        this.constraints.clear();
        buildConstraints();
    }

    public boolean isJsonValid(JsonObject dataRootObject) {
        System.out.println("LOG> ConstraintsValidator.isJsonValid");
        this.dataRootObject = dataRootObject;
        return isDataValid();
    }

    /******************************************************************************************************************/

    private boolean isDataValid() {
        System.out.println("LOG> ConstraintsValidator.isDataValid");
        boolean valid = true;
        for (Constraint constraint : constraints) {
            System.out.println("LOG> " + constraint);
            valid = valid && ConstraintDictionary.isDataValid(
                    constraint,
                    getJsonElementFromPath(constraint.getSourceField()),
                    getJsonElementFromPath(constraint.getTargetField())
            );
        }
        return valid;
    }

    private JsonElement getJsonElementFromPath(JsonPointer path) {
        System.out.println("LOG> ConstraintValidator.getJsonElementFromPath " + path);
        JsonObject property = null;
        JsonElement value = null;
        String node;
        String[] nodes = path.toArray();
        for (int i = 0; i < nodes.length; i++) {
            node = nodes[i];

            if (node.equals("#")) {
                property = dataRootObject;
            } else if (property != null) {
                if (i == nodes.length + 1) {
                    property = property.get(node).getAsJsonObject();
                } else {
                    value = property.get(node);
                }
            }
        }
        return value;
    }

    private void buildConstraints() throws IllegalArgumentException {
        recursiveConstraintsBrowsing(schemaRootObject, new JsonPointer("#"));
    }

    private void makeConstraintsFromJson(JsonArray jsonConstraints, JsonPointer sourceFieldPath, String sourceFieldType) throws IllegalArgumentException {
        System.out.println("LOG> ConstraintsValidator.makeConstraintsFromJson");
        for (JsonElement constraintElement : jsonConstraints) {
            if (constraintElement.isJsonObject()) {
                JsonObject constraintObject = constraintElement.getAsJsonObject();
                if (constraintObject.has(KEY_CONSTRAINT_TYPE) && constraintObject.has(KEY_TARGET_FIELD)) {

                    Constraint c = new Constraint(constraintObject.get(KEY_CONSTRAINT_TYPE).getAsString(), sourceFieldType,
                            sourceFieldPath, new JsonPointer(constraintObject.get(KEY_TARGET_FIELD).getAsString()));
                    constraints.add(c);
                    System.out.println("LOG> " + c);

                } else {
                    throw new IllegalArgumentException("Contrainte incomplète dans " + sourceFieldPath.toString() + ".constraints");
                }
            } else {
                throw new IllegalArgumentException("Erreur de syntaxe dans " + sourceFieldPath.toString() + ".constraints");
            }
        }
    }

    private void recursiveConstraintsBrowsing(JsonObject o, JsonPointer currentPath) throws IllegalArgumentException {
        System.out.println("LOG> ConstraintsValidator.recursiveConstraintsBrowsing");
        if (o.has("properties")) {
            JsonObject properties = o.get("properties").getAsJsonObject();
            Set<Map.Entry<String, JsonObject>> entrySet = filterJsonObjectsEntriesOnly(properties);
            for (Map.Entry<String, JsonObject> entry : entrySet) {
                JsonObject property = entry.getValue();
                currentPath.push(entry.getKey());
                System.out.println("LOG> browsing " + currentPath);

                if (property.has("constraints")) {
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