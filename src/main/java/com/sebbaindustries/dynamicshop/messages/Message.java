package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.Core;

/**
 * @author SebbaIndustries
 */
public class Message {

    public String noPermission = "&cYou lack security clearance! This incident will be reported to the general.";

    public static Message get() {
        return Core.gCore().message;
    }

}
