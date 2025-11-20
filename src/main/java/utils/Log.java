package utils;

import java.time.LocalDateTime;

public final class Log {

    private Log() {}

    public static void info(String message) {
        System.out.println(time() + " [INFO] " + message);
    }

    public static void warn(String message) {
        System.out.println(time() + " [WARN] " + message);
    }

    public static void error(String message) {
        System.err.println(time() + " [ERROR] " + message);
    }

    private static String time() {
        return LocalDateTime.now().toString();
    }
}
