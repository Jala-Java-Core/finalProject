package entertainment.helpers;

import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import entertainment.models.BaseModel;
import entertainment.models.Channel;

public class JsonHelper<T extends BaseModel> {
    private final Class<T> type;

    public JsonHelper(Class<T> type) {
        this.type = type;
    }

    private String getCollectionPath() {
        return type.getName() + ".json";
    }

//    public Map<String, T> getCollection() {
//        return getCollection(this.type);
//    }

    public Map<String, T> getCollection() {
        Gson gson = new GsonBuilder().create();
        FileHelper fh = new FileHelper(getCollectionPath());
        String jsonStr = fh.read();
//        Type collectionType = new TypeToken<ArrayList<Channel>>() {}.getType();
//        Type collectionType = TypeToken.getParameterized(List.class, this.type).getType();
        Type collectionType = TypeToken.getParameterized(Map.class, String.class, this.type).getType();
//        List<T> list = gson.fromJson(jsonStr, collectionType);
//        Map<String, T> map = new HashMap<String, T>();
        Map<String, T> map = gson.fromJson(jsonStr, collectionType);

        if (map == null) {
            return new HashMap<>();
        }
//
//        for (T element: list) {
//            map.put(element.getId(), element);
//        }
        return map;
    }

    public boolean saveCollection(Map<String, T> collection) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileHelper fh = new FileHelper(getCollectionPath());
//        List<T> list = new ArrayList<>();
//        for (String id: collection.keySet()) {
//            list.add(collection.get(id));
//        }

        String jsonStr = gson.toJson(collection);
        return fh.write(jsonStr);
    }

    public String toJson(T jsonObj) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(jsonObj);
    }
}