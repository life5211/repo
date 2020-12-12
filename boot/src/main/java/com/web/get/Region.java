package com.web.get;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @date 2020-2-23 17:08
 */
public class Region {

    /**
     * 异常计数器
     */
    AtomicInteger errNo = new AtomicInteger();

    final Charset charset = Charset.forName("gbk");
//     final Charset charset = StandardCharsets.UTF_8;

    final Pattern pattern14 = Pattern
            .compile("<tr[^>]*><td><a[^>]*>(\\d{12})</a></td><td><a[^>]*>([^<]+)</a></td></tr>");
    final Pattern pattern14NoChild = Pattern.compile("<tr[^>]*><td>(\\d{12})</td><td>([^<]+)</td></tr>");
    final Pattern pattern5 = Pattern
            .compile("<tr[^>]*><td>(\\d{12})</td><td>(\\d{3})</td><td>([^<]+)</td></tr>");
    final Pattern patternHome = Pattern.compile("<td><a href='(\\d\\d).html'>([^<]+)<br/></a></td>");

    static final String year = "2020";
    static final String pathParent = "C:\\download\\www.stats.gov.cn\\tjsj\\tjbz\\tjyqhdmhcxhfdm\\";
    //     final String pathParent = "C:\\IDEA-Workspace2\\xzqh2\\www.stats.gov.cn\\tjsj\\tjbz\\tjyqhdmhcxhfdm\\";

    static final String path = pathParent + year;
    static final String errPath = path + "\\error";

    /**
     * 大量json文件数据
     */
    final Map<String, List<String[]>> codeHtmlAreas = new TreeMap<>();
//     final Map<String, List<String[]>> codeHtmlAreas = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new Region().run();
    }

    public void run() {
//        this.createErrorDir();
        doFile(new File(path));

        // 输出json文件
        this.printJsonFile();
    }

    /**
     * 循环所有html文件
     */
    void doFile(File fileRead) {
        if (fileRead.isDirectory()) {
            if (fileRead.getName().equals(".git")) return;
            for (File file : Objects.requireNonNull(fileRead.listFiles())) {
                doFile(file);
            }
            return;
        }
        if (!fileRead.getName().endsWith(".html")) return;

        // 处理html单文件数据
        String html = file2String(fileRead);
        // 字符集转换
//        FileUtils.write(fileRead, html.replace("charset=gb2312", "charset=utf-8"), StandardCharsets.UTF_8);
        String parCode = fileRead.getName().replace(".html", "");
        boolean emptyHtml = true;
        int level = parCode.length() / 2 + 1;
        if (level < 5) {
            // 村级以上
            Matcher matcher = pattern14NoChild.matcher(html);
            while (matcher.find()) {
                emptyHtml = false;
                this.addArea(matcher.group(1), matcher.group(2), "", parCode, 0);
            }
            matcher = pattern14.matcher(html);
            while (matcher.find()) {
                emptyHtml = false;
                this.addArea(matcher.group(1), matcher.group(2), "", parCode, 1);
            }

        } else {
            // 村级
            Matcher matcher = pattern5.matcher(html);
            while (matcher.find()) {
                emptyHtml = false;
                String code = matcher.group(1);
                String cxcode = matcher.group(2);
                String name = matcher.group(3);
                this.addArea(code, name, cxcode, parCode, 0);
            }
        }
        if (emptyHtml) {
            // 首页
            Matcher matcher = patternHome.matcher(html);
            while (matcher.find()) {
                emptyHtml = false;
                this.addArea(matcher.group(1) + "0000000000", matcher.group(2), "", "", 1);
            }
        }
        System.out.print(".");
        if (emptyHtml) {
            this.printErrorInfo(html, parCode, fileRead);
        }
    }


    /**
     * 输出单条行政区划数据
     */
    void addArea(String code, String name, String cxCode, String pCode, int isPar) {
        String type = getType(code, cxCode);
        String parCode = getParCode(pCode);
        // 添加json文件数据
        List<String[]> htmlAreas = codeHtmlAreas.computeIfAbsent(parCode, k -> new LinkedList<>());
        htmlAreas.add(new String[]{code, name, type, String.valueOf(isPar)});
    }

    /**
     * 输出 json文件
     */
    void printJsonFile() {
        String tableName = "xzqh_stats_" + year;
        String sql = "\n" +
                "SET NAMES utf8mb4;\n" +
                "DROP TABLE IF EXISTS `" + tableName + "`;\n" +
                "CREATE TABLE `" + tableName + "`  (\n" +
                "  `id` bigint(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `code` bigint(16) NOT NULL,\n" +
                "  `name` varchar(48) NOT NULL,\n" +
                "  `par` bigint(16) NOT NULL,\n" +
                "  `type` int(11) NOT NULL,\n" +
                "  `has` int(11) NOT NULL,\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE = InnoDB AUTO_INCREMENT = 0 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;\n" +
                "\n";
        File sqlFile = new File(path + "\\" + tableName + ".sql");
        this.write(sqlFile, sql, false);
        if (codeHtmlAreas.size() > 1) {
            codeHtmlAreas.forEach((par, ls) -> {
                String codeHtmlAreasJson = ls.stream()
                        .map(s -> String.format("  [%s,\"%s\",%s,%s]", s[0], s[1], s[2], s[3]))
                        .collect(Collectors.joining(",\n", "[\n", "\n]"));
                File jsonFile = new File(path + "\\json\\" + par + ".json");
                // 输出 json 文件用于web
                this.write(jsonFile, codeHtmlAreasJson, false);

                String codeHtmlAreasSql = ls.stream()
                        .map(s -> String.format("(null,%s,'%s',%s,%s,%s)", s[0], s[1], par, s[2], s[3]))
                        .collect(Collectors.joining(",", "INSERT INTO " + tableName + " VALUES", ";\n"));
                // 输出 sql文件 文件用于web
                this.write(sqlFile, codeHtmlAreasSql, true);
            });
        }
        System.out.println("Json print exit");
    }

    void write(File file, CharSequence data, boolean append) {
        try {
            FileUtils.write(file, data, StandardCharsets.UTF_8, append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getType(String code, String type) {
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

    String getParCode(String parCode) {
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

    String file2String(File file) {
        try (
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))
        ) {
            return new BufferedReader(new InputStreamReader(inputStream, charset)).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
//        return FileUtils.readFileToString(file, charset);
    }

    void createErrorDir() {
        new File(errPath).mkdir();
    }

    /**
     * 处理异常HTML
     */
    void printErrorInfo(String html, String href, File file) {
        if (!href.equals("index")) {
            System.err.printf("%s %s %s %s \t [HTML CONTENT]: %s  %n",
                    errNo.addAndGet(1),
                    file.getAbsolutePath().replace(path, ""),
                    Thread.currentThread().getName(),
//                    file.delete(),
                    file.renameTo(new File(errPath + "\\" + file.getName())),
                    html.replaceAll("[\n\r]", "")
            );
        }
    }

}
