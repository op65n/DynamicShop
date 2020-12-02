package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.Core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SebbaIndustries
 */
public class Message {

    public Message() {

        multiLineMessage.add(0, "line 0");
        multiLineMessage.add(1, "line 1");
        multiLineMessage.add(2, "line 2");
        multiLineMessage.add(3, "line 3");
        multiLineMessage.add(4, "line 4");

    }

    public String noPermission = "&cYou lack security clearance! This incident will be reported to the general.";

    public String shopSuccessfulTransaction = "#<3ee524>%player% #<F8B4FF>%amount% #<580062>%material% #>9E9C46>%price_buy% #<00ccff>%price_sell%";

    public List<String> multiLineMessage = new ArrayList<>();


    public static Message get() {
        return Core.gCore().message;
    }

}
