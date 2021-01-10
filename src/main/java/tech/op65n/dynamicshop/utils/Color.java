package tech.op65n.dynamicshop.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class Color {

    private static final Pattern HEX_PATTERN = Pattern.compile("#<([A-Fa-f0-9]){6}>");

    /**
     * Formats hex (#>ffffff<) and '&' (&6) codes, treat this like a blackbox.
     *
     * @param s Un-Formatted string
     * @return Formatted string
     */
    public static String format(String s) {

        Matcher matcher = HEX_PATTERN.matcher(s);

        while (matcher.find()) {
            String hexString = matcher.group();

            hexString = "#" + hexString.substring(2, hexString.length() - 1);
            ChatColor hex = ChatColor.of(hexString);
            String before = s.substring(0, matcher.start());
            String after = s.substring(matcher.end());

            s = before + hex + after;
            matcher = HEX_PATTERN.matcher(s);
        }


        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
