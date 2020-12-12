package com.web.get;

import java.io.File;

public class RegionMove {

    public static void main(String[] args) {
        new RegionMove().run();
    }

    void run() {
        doFile(new File(Region.errPath));
    }

    void doFile(File file) {
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                doFile(listFile);
            }
        }
        String fileName = file.getName();
        if (!fileName.contains(".")) return;
        String newPath = getPath(fileName);
        System.out.printf("%b\t%s -> %s",
                file.renameTo(new File(Region.path + newPath)), fileName, newPath);
    }

    String getPath(String fileName) {
        String code = fileName.substring(0, fileName.lastIndexOf("."));
        // none
        String dirs = String.format("/%s/%s/%s/%s/",
                getDir(code, 0, 2), getDir(code, 2, 4), getDir(code, 4, 6), getDir(code, 6, 9));
        File file;
        if (!(file = new File(Region.path + dirs)).exists()) {
            System.out.printf("%b %s", file.mkdirs(), dirs);
        }
        return dirs + fileName;
    }

    String getDir(String name, int s, int e) {
        if (name.length() <= e) return "";
        String dir = name.substring(s, e);
        if ("00".equals(dir) || "000".equals(dir)) return "";
        return dir;
    }

}
