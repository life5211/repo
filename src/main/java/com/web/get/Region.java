package com.web.get;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @date 2020-2-23 17:08
 */
public class Region {

    private final Charset gb2312 = Charset.forName("GBK");

    private final Pattern pattern14 = Pattern
            .compile("<tr[^>]*><td><a[^>]*>(\\d{12})</a></td><td><a[^>]*>([^<]+)</a></td></tr>");
    private final Pattern pattern5 = Pattern
            .compile("<tr[^>]*><td>(\\d{12})</td><td>(\\d{3})</td><td>([^<]+)</td></tr>");

        private final String pathParent = "C:\\download\\www.stats.gov.cn\\tjsj\\tjbz\\tjyqhdmhcxhfdm\\";
//        private final String pathParent = "C:\\download\\www.stats.gov.cn\\tjsj\\tjbz\\tjyqhdmhcxhfdm\\";
//    private final String pathParent = "C:\\Export\\xzqh\\www.stats.gov.cn\\tjsj\\tjbz\\tjyqhdmhcxhfdm\\";
    private final String dir = "2019";

    private final StringBuilder regions = new StringBuilder();
    private final StringBuilder errorHtml = new StringBuilder();
    private final long time = System.currentTimeMillis();
    private final File errorFile = new File(pathParent + time + "error.htmls." + dir);
    private final File regionFile = new File(pathParent + time + "region.csv." + dir);

    public static void main(String[] args) {
        new Region().run();
    }

    public void run() {
//        createErrorDir();
        regions.append("\"code\",\"name\",\"cx_code\",\"par_code\",\"level\"\n");
        doFile(new File(pathParent + dir));

//        try {
//            FileUtils.write(regionFile, regions, gb2312, true);
//            FileUtils.write(errorFile, errorHtml, gb2312, true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void doFile(File fileRead) {
        if (fileRead.isDirectory()) {
            for (File file : Objects.requireNonNull(fileRead.listFiles())) {
                doFile(file);
            }
            return;
        }

        String html = "";
        try {
            html = FileUtils.readFileToString(fileRead, gb2312);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = fileRead.getPath();
        if (!path.endsWith(".html")) {
            return;
        }
        String parCode = fileRead.getName().replace(".html", "");
        int count = 0;
//        int level = (fileRead.getName().length() - 3) / 2;
        int level = parCode.length() / 2 + 1;
        if (level < 5) {
            Matcher matcher = pattern14.matcher(html);
            while (matcher.find()) {
                count++;
                String code = matcher.group(1);
                String name = matcher.group(2);
                print(code, name, "", parCode);
            }
        } else {
            Matcher matcher = pattern5.matcher(html);
            while (matcher.find()) {
                count++;
                String code = matcher.group(1);
                String cxcode = matcher.group(2);
                String name = matcher.group(3);
                print(code, name, cxcode, parCode);
            }
        }
        if (count < 1) {
            printErrorInfo(html, parCode, fileRead);
        }
    }

    private void print(String code, String name, String cxcode, String pcode) {
        regions.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n", code, name, cxcode, getParCode(pcode), getLevel(code)));
    }

    private String getLevel(String code) {
        if (code.endsWith("0000000000"))
            return "1";
        if (code.endsWith("0000000"))
            return "2";
        if (code.endsWith("00000"))
            return "3";
        if (code.endsWith("000"))
            return "4";
        return "5";
    }

    private String getParCode(String parCode) {
        switch (parCode.length()) {
            case 9:
                return parCode + "000";
            case 6:
                return parCode + "000000";
            case 4:
                return parCode + "00000000";
            case 2:
                return parCode + "0000000000";
            default:
                return "000000000000";
        }
    }

    private void createErrorDir() {
        new File(pathParent + time + "error" + dir).mkdir();
    }

    private void printErrorInfo(String html, String href, File file) {
//        boolean removeFlag = file.renameTo(new File(pathParent + time + "error" + dir + "\\" + file.getName()));
        System.out.println("--===" + href);
        errorHtml.append(href).append("\n");
    }

}
