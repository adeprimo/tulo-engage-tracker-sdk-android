package adeprimo.com.tuloengagetracker.utils;

import android.util.Log;

public class Logger {

    private static int level = 0;

    /**
     * Error Level Logging
     *
     * @param tag the log tag
     * @param msg the log message
     * @param args extra arguments to be formatted
     */
    public static void error(String tag, String msg, Object... args) {
        if (level >= 1) {
            Log.e(getTag(tag), getMessage(msg, args));
        }
    }

    /**
     * Debug Level Logging
     *
     * @param tag the log tag
     * @param msg the log message
     * @param args extra arguments to be formatted
     */
    public static void debug(String tag, String msg, Object... args) {
        if (level >= 2) {
            Log.d(getTag(tag), getMessage(msg, args));
        }
    }

    /**
     * Verbose Level Logging
     *
     * @param tag the log tag
     * @param msg the log message
     * @param args extra arguments to be formatted
     */
    public static void warning(String tag, String msg, Object... args) {
        if (level >= 3) {
            Log.w(getTag(tag), getMessage(msg, args));
        }
    }

    public static void verbose(String tag, String msg, Object... args) {
        if (level >= 4) {
            Log.v(getTag(tag), getMessage(msg, args));
        }
    }

    /**
     * Returns a formatted logging String
     *
     * @param msg The message to log
     * @param args Any extra args to log
     * @return the formatted message
     */
    private static String getMessage(String msg, Object... args) {
        return getThread() + "|" + String.format(msg, args);
    }

    /**
     * Returns the updated tag.
     *
     * @param tag the tag to be appended to
     * @return the appended tag
     */
    private static String getTag(String tag) {
        return "Tulo Engage Tracker: " + tag;
    }

    /**
     * Returns the name of the current
     * thread.
     *
     * @return the threads name
     */
    private static String getThread() {
        return Thread.currentThread().getName();
    }

    /**
     * Set the logging level.
     *
     * @param newLevel The new log-level to use
     */
    public static void setLogLevel(LogLevel newLevel) {
        level = newLevel.getLevel();
    }
}
