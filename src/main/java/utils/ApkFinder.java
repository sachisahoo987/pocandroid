package utils;

import java.io.File;

public class ApkFinder {

    public static String findApkOrIpa(String folderPath, String extension) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            return null;
        }

        File[] files = folder.listFiles((dir, name) ->
                name.toLowerCase().endsWith("." + extension)
        );

        if (files != null && files.length > 0) {
            return files[0].getAbsolutePath(); // return first match
        }

        return null;
    }
}
