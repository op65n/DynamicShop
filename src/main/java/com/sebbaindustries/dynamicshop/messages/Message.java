package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.Core;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public class Message {

    public String no_permission = "&cYou lack security clearance! This incident will be reported to the general.";
    public String console_cannot_execute = "&cConsole can't execute this command, if you think this is wrong... too bad I don't care.";

    public String json_message = "{\"message\" = \"Hello there!\" \"onHover\" = \"Click me!\" \"clickAction\" = \"copy\" \"onClick\" = \"You copied this!\"}";

    /**
     * Gets an message class instance form the global core
     *
     * @return message class instance
     */
    public static Message get() {
        return Core.gCore().getMessage();
    }

}
