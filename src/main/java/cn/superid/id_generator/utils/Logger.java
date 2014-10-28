package cn.superid.id_generator.utils;

import java.util.logging.Level;

/**
 * Created by 维 on 2014/9/4.
 */
public class Logger {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("id_generator");

    public static void info(final Object message) {
        logger.info(message != null ? message.toString() : null);
    }

    public static void error(final String message) {
        logger.log(Level.SEVERE, message);
    }

    public static void error(final String message, final Throwable t) {
        logger.log(Level.SEVERE, message, t);
    }
}
