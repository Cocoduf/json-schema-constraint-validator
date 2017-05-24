package com.cocoduf.jscv;

import java.util.Stack;

/**
 * Created by cocoduf on 17-05-24.
 */
public class JsonPointer extends Stack<String> {

    public JsonPointer() {}

    public JsonPointer(JsonPointer pointer) {
        for (String node : pointer) {
            this.push(node);
        }
    }

    public JsonPointer(String path) {
        String[] nodes = path.split("/");
        for (String node : nodes) {
            this.push(node);
        }
    }

    public String toString() {
        return String.join("/", this);
    }

}
