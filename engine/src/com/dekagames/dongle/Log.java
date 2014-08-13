package com.dekagames.dongle;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by deka on 09.07.14.
 */
public class Log {
    public static boolean NEED_LOGGING = true;

    private static Logger logger;

    static {
//        try {
        logger = Logger.getLogger(Game.class.getName());
//            handler = new FileHandler("%h/dongle.log", false);
//            handler.setFormatter(new SimpleFormatter());
//            logger.addHandler(handler);
//        } catch (IOException e) {
//            logger.log(Level.SEVERE, "Do not create log file");
//            e.printStackTrace();
//        }
    }

    public static void info(String msg){
        if (!NEED_LOGGING) return;
        logger.log(Level.INFO, msg);
    }

    public static void error(String msg){
        if (!NEED_LOGGING) return;
        logger.log(Level.SEVERE, msg);
    }

    public static void exception(String msg, Exception e){
        logger.log(Level.SEVERE, msg, e);
    }


}
