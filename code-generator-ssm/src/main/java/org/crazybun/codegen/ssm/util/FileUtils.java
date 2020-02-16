package org.crazybun.codegen.ssm.util;

import java.io.*;

public class FileUtils {

    public static File createFile(String path, String fileName) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static void copyFile(String sourceFile, String targetPath, String targetFile) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bos = new BufferedOutputStream(new FileOutputStream(createFile(targetPath, targetFile)));
            int len = -1;
            while ((len = bis.read()) != -1) {
                bos.write(len);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
