package com.sebbaindustries.dynamicshop.utils;

import java.util.regex.Pattern;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class StringParser {

    private final String string;
    private final Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");

    public StringParser(String string) {
        this.string = string;
    }

    public boolean isSafe() {
        return pattern.matcher(string).matches();
    }

    public String returnColoredIllegals() {
        StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (pattern.matcher(String.valueOf(c)).matches()) {
                sb.append("§a").append(c);
                continue;
            }
            sb.append("§c").append(c);
        }
        return sb.toString();
    }

}
