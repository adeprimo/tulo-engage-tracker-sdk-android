package adeprimo.com.tuloengagetracker.utils;

public enum LogLevel {

    OFF(0),
    ERROR(1),
    WARNING(2),
    DEBUG(3),
    VERBOSE(4);


    private int level;

    LogLevel(int c) {
        level = c;
    }

    public int getLevel() {
        return level;
    }
}
