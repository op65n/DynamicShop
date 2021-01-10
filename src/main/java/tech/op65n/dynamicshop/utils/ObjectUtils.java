package tech.op65n.dynamicshop.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import tech.op65n.dynamicshop.Core;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class ObjectUtils {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

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
        return gson.toJson(object);
    }

    public static boolean isValidJson(String s) {
        try {
            gson.fromJson(s, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException e) {
            return false;
        }
    }


    /**
     * This will be removed soon, don't use unless necessary!
     *
     * @param fileName N/A
     * @param object   N/A
     */
    public static void saveGsonFile(String fileName, Object object) {
        try {
            Writer writer = new FileWriter(Core.gCore().core.getDataFolder() + "/" + fileName, StandardCharsets.UTF_8);
            gson.toJson(object, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This will be removed soon, don't use unless necessary!
     *
     * @param object N/A
     * @param cl     N/A
     * @param <T>    N/A
     * @return N/A
     */
    @Deprecated
    public static <T> T getClassFromGson(Object object, Class<T> cl) {
        return gson.fromJson(gson.toJson(object), cl);
    }

    /**
     * This will be removed soon, don't use unless necessary!
     *
     * @param fileName N/A
     * @param cl       N/A
     * @param <T>      N/A
     * @return N/A
     */
    public static <T> T getGsonFile(String fileName, Class<T> cl) {
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(Core.gCore().core.getDataFolder() + "/" + fileName, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return gson.fromJson(reader, cl);
    }

    /**
     * This will be removed soon, don't use unless necessary!
     *
     * @param fileName N/A
     * @return N/A
     */
    @Deprecated
    public static JsonObject getJson(String fileName) {
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(Core.gCore().core.getDataFolder() + "/" + fileName, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        JsonElement jsonElement = new JsonParser().parse(reader);
        return jsonElement.getAsJsonObject();
    }


    public static JsonObject getJsonString(String s) {
        JsonElement jsonElement = new JsonParser().parse(s);
        return jsonElement.getAsJsonObject();
    }
}
