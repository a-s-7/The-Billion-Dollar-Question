package persistence;

import org.json.JSONObject;

// Represents an interface with a method that converts and returns this as JSON object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
