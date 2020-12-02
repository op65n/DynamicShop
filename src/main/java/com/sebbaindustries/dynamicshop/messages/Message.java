package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.Core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SebbaIndustries
 */
public class Message {

    public Message() {

        multiLineMessage.add(0, "#<3ee524>line 0");
        multiLineMessage.add(1, "#<3ee524>%player%");
        multiLineMessage.add(2, "#<F8B4FF>%amount%");
        multiLineMessage.add(3, "line 3");
        multiLineMessage.add(4, "#<9E9C46>%price_buy% #<00ccff>%price_sell%");

    }

    public String noPermission = "&cYou lack security clearance! This incident will be reported to the general.";

    public String shopSuccessfulTransaction = "#<3ee524>%player% #<F8B4FF>%amount% #<580062>%material% #<9E9C46>%price_buy% #<00ccff>%price_sell%";

    public List<String> multiLineMessage = new ArrayList<>();


    public static Message get() {
        return Core.gCore().message;
    }

}
