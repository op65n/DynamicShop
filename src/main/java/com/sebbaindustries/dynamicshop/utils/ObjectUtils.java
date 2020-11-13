package com.sebbaindustries.dynamicshop.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.sebbaindustries.dynamicshop.Core;

import java.io.*;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class ObjectUtils {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Gson normalGson = new Gson();

    private ObjectUtils() {
        throw new UnsupportedOperationException("Instantiation of this class is not permitted in case you are using reflection.");
    }

    /**
     * This method is responsible for de-serializing the Java Object into Json String.
     *
     * @param object Object to be de-serialized.
     * @return String
     */
    public static String deserializeObjectToString(final Object object) {
        return GSON.toJson(object);
    }


    public static void saveGsonFile(String fileName, Object object) {
        try {
            Writer writer = new FileWriter(Core.gCore().core.getDataFolder() + "/" + fileName + ".json");
            GSON.toJson(object, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getClassFromGson(Object object, Class<T> cl) {
        return normalGson.fromJson(normalGson.toJson(object), cl);
    }

    public static <T> T getGsonFile(String fileName, Class<T> cl) {
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(Core.gCore().core.getDataFolder() + "/" + fileName + ".json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return normalGson.fromJson(reader, cl);
    }
}
