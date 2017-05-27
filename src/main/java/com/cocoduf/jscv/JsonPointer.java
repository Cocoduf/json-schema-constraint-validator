package com.cocoduf.jscv;

import javax.annotation.Nonnull;
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
        for (String node : path.split("/")) {
            this.push(node);
        }
    }

    public String toString() {
        return String.join("/", this);
    }

    @Nonnull
    public String[] toArray() {
        return toString().split("/");
    }

}
