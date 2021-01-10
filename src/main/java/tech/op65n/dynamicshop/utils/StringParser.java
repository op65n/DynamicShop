package tech.op65n.dynamicshop.utils;

import java.util.regex.Pattern;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class StringParser {

    private final String string;
    private Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");

    public StringParser(String string) {
        this.string = string;
    }

    public StringParser(String string, boolean applyStrictPattern) {
        this.string = string;
        if (applyStrictPattern) {
            this.pattern = Pattern.compile("^[a-zA-Z]+$");
            return;
        }
        this.pattern = Pattern.compile("^[a-zA-Z0-9]+$");
    }

    /**
     * Checks if string contains any illegal characters.
     *
     * @return True if no illegals are found, false if pattern flags at least one.
     */
    public boolean isSafe() {
        return pattern.matcher(string).matches();
    }

    /**
     * Colorizes string with green (Allowed chars) and red (illegal chars) colors
     * for visual representation of illegal characters
     *
     * @return Colorized String
     */
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
