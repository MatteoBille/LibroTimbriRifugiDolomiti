package com.example.librotimbririfugidolomiti;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {

    public static void copyFile(FileInputStream fromFile, String toFile) throws IOException {
        File outFile = new File(toFile);
        outFile.getParentFile().mkdirs();
        outFile.createNewFile();
        copyFile(fromFile, new FileOutputStream(outFile));
        fromFile.close();
    }


    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {

        byte[] b = new byte[1024];
        int len;
        while ((len = fromFile.read(b, 0, 1024)) > 0) {
            toFile.write(b, 0, len);
        }

    }

}