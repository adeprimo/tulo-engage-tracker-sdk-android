package adeprimo.com.tuloengagetracker;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class GsonToJsonConverter {

    private final Gson gson;

    public GsonToJsonConverter() {
        this.gson = new Gson();
    }

    public String serialize(Object object) {
        return gson.toJson(object);
    }

    public Map<String, Object> deserialize(String json) {
        try {
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            return gson.fromJson(json, mapType);

        }
        catch (JsonParseException e) {
            return null;
        }
    }

}
