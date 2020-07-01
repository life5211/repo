package com.hnf.main;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @date 2020-2-23 17:08
 */
public class Region {

    //    private final Charset charset = Charset.forName("gbk");
    private final Charset charset = StandardCharsets.UTF_8;

    private final Pattern pattern14 = Pattern
            .compile("<tr[^>]*><td><a[^>]*>(\\d{12})</a></td><td><a[^>]*>([^<]+)</a></td></tr>");
    private final Pattern pattern142 = Pattern
            .compile("<tr[^>]*><td>(\\d{12})</td><td>([^<]+)</td></tr>");
    private final Pattern pattern5 = Pattern
            .compile("<tr[^>]*><td>(\\d{12})</td><td>(\\d{3})</td><td>([^<]+)</td></tr>");

    private final String pathParent = "C:\\IDEA-Workspace2\\xzqh2\\www.stats.gov.cn\\tjsj\\tjbz\\tjyqhdmhcxhfdm\\";
    //    private final String pathParent = "C:\\IDEA-Workspace2\\webZip\\www.stats.gov.cn\\tjsj\\tjbz\\tjyqhdmhcxhfdm\\";
//    private final String pathParent = "C:\\IDEA-Workspace2\\webZip\\";
    private final String dir = "2018";

    private final StringBuilder regions = new StringBuilder();
    private final StringBuilder errorHtml = new StringBuilder();
    private final Map<String, List<String>> json = new ConcurrentHashMap<>();

    private final long time = System.currentTimeMillis();
    private final File errorFile = new File(pathParent + time + "error" + dir + ".txt");
    private final File regionFile = new File(pathParent + time + "region" + dir + ".csv");

    public static void main(String[] args) {
        new Region().run();
    }

    public void run() {
        regions.append("\"code\",\"name\",\"type\",\"par\",\"is_par\"");
        try {
            doFile(new File(pathParent + dir));
//            输出.csv
//            FileUtils.write(regionFile, regions.toString(), StandardCharsets.UTF_8, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        printJsonFile();
    }

    private void doFile(File fileRead) throws IOException {
        if (fileRead.isDirectory()) {
            if (fileRead.getName().equals(".git")) return;
            for (File file : Objects.requireNonNull(fileRead.listFiles())) {
                doFile(file);
            }
            return;
        }
        String html = file2String(fileRead);
//        FileUtils.write(fileRead, html.replace("charset=gb2312", "charset=utf-8"), StandardCharsets.UTF_8);
        doData(fileRead, html);
    }

    private void doData(File fileRead, String html) {
        String path = fileRead.getPath();
        if (!path.endsWith(".html")) {
            return;
        }
        String parCode = fileRead.getName().replace(".html", "");
        int count = 0;
        int level = parCode.length() / 2 + 1;
        if (level < 5) {
            Matcher matcher = pattern142.matcher(html);
            while (matcher.find()) {
                count++;
                String code = matcher.group(1);
                String name = matcher.group(2);
                print(code, name, "", parCode, 0);
            }
            matcher = pattern14.matcher(html);
            while (matcher.find()) {
                count++;
                String code = matcher.group(1);
                String name = matcher.group(2);
                print(code, name, "", parCode, 1);
            }

        } else {
            Matcher matcher = pattern5.matcher(html);
            while (matcher.find()) {
                count++;
                String code = matcher.group(1);
                String cxcode = matcher.group(2);
                String name = matcher.group(3);
                print(code, name, cxcode, parCode, 0);
            }
        }
        if (count < 1) {
            printErrorInfo(html, parCode, fileRead);
        }
    }


    private void print(String code, String name, String cxcode, String pcode, int isPar) {
        String type = getType(code, cxcode);
        String parCode = getParCode(pcode);
        regions.append(String.format("\n\"%s\",\"%s\",\"%s\",\"%s\",\"%d\"", code, name, type, parCode, isPar));
        printJson(code, name, type, parCode, isPar);

    }

    private void printJson(String code, String name, String type, String parCode, int isPar) {
        List<String> strings = json.computeIfAbsent(parCode, k -> new LinkedList<>());
        strings.add(String.format("[\"%s\",\"%s\",\"%s\",\"%d\"]", code, name, type, isPar));
    }

    private void printJsonFile() {
        if (json.size() > 1) {
            String pathname = pathParent + "json\\" + dir;
            json.forEach((par, ls) -> {
                File file = new File(pathname + "\\" + par + ".json");
                String arr = ls.stream().collect(Collectors.joining(",\n  ", "[\n  ", "\n]"));
                try {
                    FileUtils.write(file, arr, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("Json print exit");
    }

    private String getType(String code, String type) {
        if (code.endsWith("0000000000"))
            return "1";
        if (code.endsWith("00000000"))
            return "2";
        if (code.endsWith("000000"))
            return "3";
        if (code.endsWith("000"))
            return "4";
        return type;
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

    private String file2String(File file) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        return new BufferedReader(new InputStreamReader(inputStream, charset))
                .lines().collect(Collectors.joining("\n"));

//        return FileUtils.readFileToString(file, charset);
    }

    private void createErrorDir() {
        new File(pathParent + time + "error" + dir).mkdir();
    }

    private void printErrorInfo(String html, String href, File file) {
//        boolean removeFlag = file.renameTo(new File(pathParent + time + "error" + dir + "\\" + file.getName()));
        System.out.println(dir + "--===" + href);
        errorHtml.append(href).append("\n");
    }

}
