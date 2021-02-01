package tech.op65n.dynamicshop.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sebba
 * @version 1.0
 */
public interface ILog {

    /**
     * Debug logger for DynamicMarket Plugin, it accepts an object
     * and prints out deserialized information to the console,
     * permanent logging is disabled with this logger
     *
     * @param o Object for deserialization and logging
     */
    void debug(Object o);

    /**
     * INFO style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is disabled with this logger
     *
     * @param message Message for logger
     */
    void log(String message);

    /**
     * WARN style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is disabled with this logger
     *
     * @param message Message for logger
     */
    void warn(String message);

    /**
     * ERROR style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is disabled with this logger
     *
     * @param message Message for logger
     */
    void error(String message);

    /**
     * INFO style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is enabled with this logger.
     * File name is taken from the config.
     *
     * @param message Message for logger
     */
    void permLog(String message);

    /**
     * WARN style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is enabled with this logger.
     * File name is taken from the config.
     *
     * @param message Message for logger
     */
    void permWarn(String message);

    /**
     * ERROR style logger for DynamicMarket Plugin, it accepts a String and prints
     * it out to the console, permanent logging is enabled with this logger.
     * File name is taken from the config.
     *
     * @param message Message for logger
     */
    void permError(String message);

    /**
     * TODO: Add transaction component here
     * INFO style logger for DynamicMarket Plugin, it accepts a ShopTransaction object
     * and prints it out to the console, permanent logging is enabled with this logger.
     * File name is taken from the config, this is logged to separate file.
     *
     * @param transaction Transaction for logger
     */
    void transactionLog(String transaction);

}
