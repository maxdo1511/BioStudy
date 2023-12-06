package ru.hbb.learnstudio.image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageUtils {

    private static final String DIR = "images/";

    public static void saveImage(byte[] bytes, String outputPath) throws IOException {
        if (outputPath == null) {
            outputPath = UUID.randomUUID().toString() + ".png";
        }
        outputPath = DIR + outputPath;
        Path path = Paths.get(outputPath);
        Files.write(path, bytes);
    }

    public static void removeImage(String uuid) throws IOException {
        String removePath = DIR + uuid;
        Path path = Paths.get(removePath);
        Files.delete(path);
    }

    public static File getImage(String uuid) throws IOException {
        String removePath = DIR + uuid;
        return new File(removePath);
    }

}
