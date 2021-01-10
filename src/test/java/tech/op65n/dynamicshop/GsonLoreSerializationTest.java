package tech.op65n.dynamicshop;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GsonLoreSerializationTest {

    private Gson gson = new Gson();

    private List<String> lore;

    @Test
    public void serializeLoreList() {
        String nullLore = gson.toJson(lore);
        List<String> serLore0 = gson.fromJson(nullLore, new TypeToken<List<String>>() {}.getType());
        Assertions.assertEquals(nullLore, "null");
        Assertions.assertEquals(gson.toJson(serLore0), "null");

        lore = new ArrayList<>();
        String emptyLore = gson.toJson(lore);
        List<String> serLore1 = gson.fromJson(emptyLore, new TypeToken<List<String>>() {}.getType());
        Assertions.assertEquals(emptyLore, "[]");
        Assertions.assertEquals(gson.toJson(serLore1), "[]");

        lore.add("Test String");
        String normalLore = gson.toJson(lore);
        List<String> serLore2 = gson.fromJson(normalLore, new TypeToken<List<String>>() {}.getType());
        Assertions.assertEquals(normalLore, "[\"Test String\"]");
        Assertions.assertEquals(gson.toJson(serLore2), "[\"Test String\"]");
    }

}
