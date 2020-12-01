package com.sebbaindustries.dynamicshop.engine;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.CommandManager;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DynEngine {

    public void initialize() {
        Core.gCore().commandManager = new CommandManager(Core.gCore().core);

        /*
        messages.js
         */
        File messages = new File(Core.gCore().core.getDataFolder(), "messages.js");
        if (!messages.exists()) {
            ObjectUtils.saveGsonFile("messages", new Message());
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new java.io.FileWriter(Core.gCore().core.getDataFolder() + "/" + "messages" + ".js", StandardCharsets.UTF_8, true));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            try {
                out.newLine();
                out.write("// Hello!");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            List<String> lines = Collections.emptyList();
            try {
                lines = Files.readAllLines(Paths.get(Core.gCore().core.getDataFolder() + "/" + "messages" + ".js"), StandardCharsets.UTF_8);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            List<String> commentedFile = new ArrayList<>();
            //"noPermission":
            for (String line : lines) {
                if (line.contains("\"noPermission\":")) {
                    commentedFile.add("// Well hello there!");
                    commentedFile.add("// haven't seen ya in a while");
                }
                commentedFile.add(line);
            }

            commentedFile.forEach(System.out::println);
        }
        Core.gCore().message = ObjectUtils.getGsonFile("messages", Message.class);

        /*
        configuration.js
         */
        //File configuration = new File(core.getDataFolder(), "configuration.js");
        //if (!configuration.exists()) {
        //    ObjectUtils.saveGsonFile("configuration", new Configuration());
        //}
        //Core.gCore().configuration = ObjectUtils.getGsonFile("configuration", Configuration.class);
    }
}
