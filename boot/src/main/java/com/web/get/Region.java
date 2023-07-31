package com.web.get;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
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
    static final String pathParent = "C:\\IDEA-Workspace2\\webZip\\www.stats.gov.cn\\tjsj\\tjbz\\tjyqhdmhcxhfdm\\";
    //     final String pathParent = "C:\\IDEA-Workspace2\\xzqh2\\www.stats.gov.cn\\tjsj\\tjbz\\tjyqhdmhcxhfdm\\";

    static final String path = pathParent + year + "\\";
    static final String errPath = path + "error\\";

    /**
     * 大量json文件数据
     */
    Map<String, List<String[]>> codeHtmlAreas = new TreeMap<>();
//     final Map<String, List<String[]>> codeHtmlAreas = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new Region().run();
    }

    public void run() {
//        this.createErrorDir();
        this.parseHtmlFile(new File(path));
        this.objectSave();

    }

    public void runLoadAndPrint() {
        this.objectLoad();
        // 输出json文件
        this.printJsonFile();
    }

    /**
     * 循环所有html文件
     */
    void parseHtmlFile(File fileRead) {
        String fileName = fileRead.getName();
        if (fileRead.isDirectory()) {
            if (".git".equals(fileName) || "json".equals(fileName)) return;
            for (File file : Objects.requireNonNull(fileRead.listFiles())) this.parseHtmlFile(file);
            return;
        }
        if (!fileName.endsWith(".html")) return;

        // 处理html单文件数据
        String html = file2String(fileRead);
        // 字符集转换
//        FileUtils.write(fileRead, html.replace("charset=gb2312", "charset=utf-8"), StandardCharsets.UTF_8);
        String parCode = fileName.replace(".html", "");
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
                this.addArea(matcher.group(1) + "0000000000", matcher.group(2), "", "0", 1);
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
    void addArea(String code, String name, String cxCode, String shortParCode, int isPar) {
        String type = getType(code, cxCode);
        String parCode = getLongCode(shortParCode);
        String shortCode = getShortCode(code);
        // 添加json文件数据
        List<String[]> htmlAreas = codeHtmlAreas.computeIfAbsent(parCode, k -> new LinkedList<>());
        htmlAreas.add(new String[]{code, name, type, String.valueOf(isPar), shortParCode, shortCode});
    }

    /**
     * 输出 json文件
     */
    void printJsonFile() {

        codeHtmlAreas.keySet().parallelStream().forEach(par -> {
            List<String[]> ls = codeHtmlAreas.get(par);
            String codeHtmlAreasJson = ls.parallelStream()
                    .map(s -> String.format("  [%s,\"%s\",%s,%s]", s[5], s[1], s[2], s[3]))
                    .collect(Collectors.joining(",\n", "[\n", "\n]"));
            File jsonFile = new File(this.getJsonFilePath(par));
            // 输出 json 文件用于web
            this.write(jsonFile, codeHtmlAreasJson);
        });

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
        File sqlFile = new File(path + tableName + ".sql");
        final PrintWriter sqlWriter;
        try {
            sqlWriter = new PrintWriter(new BufferedWriter(new FileWriter(sqlFile)));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        sqlWriter.print(sql);
        codeHtmlAreas.forEach((par, ls) -> {
            String codeHtmlAreasSql = ls.stream()
                    .map(s -> String.format("(null,%s,'%s',%s,%s,%s)", s[5], s[1], s[4], s[2], s[3]))
                    .collect(Collectors.joining(",", "INSERT INTO " + tableName + " VALUES", ";\n"));
            // 输出 sql文件 文件用于web
            sqlWriter.print(codeHtmlAreasSql);
        });
        closeStream(sqlWriter);
        System.out.println("Json print exit");
    }

    public void write(File file, CharSequence data) {
        try {
            FileUtils.write(file, data, StandardCharsets.UTF_8, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeStream(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getJsonFilePath(String parCode) {
        String shortCode = this.getShortCode(parCode);
        String dirs = String.format("\\json\\%s\\%s\\%s\\%s\\",
                subDir(shortCode, 0, 2), subDir(shortCode, 2, 4),
                subDir(shortCode, 4, 6), subDir(shortCode, 6, 9));
        File file;
        if (!(file = new File(Region.path + dirs)).exists()) {
            System.out.printf("%b %s %n", file.mkdirs(), dirs);
        }
        return file.getAbsolutePath() + "\\" + shortCode + ".json";
    }

    public String subDir(String name, int s, int e) {
        if (name.length() <= e) return "";
        String dir = name.substring(s, e);
        if ("0".equals(dir) || "00".equals(dir) || "000".equals(dir)) return "";
        return dir;
    }

    public String getShortCode(String code) {
        if (code.endsWith("0000000000"))
            return code.substring(0, 2);
        if (code.endsWith("00000000"))
            return code.substring(0, 4);
        if (code.endsWith("000000"))
            return code.substring(0, 6);
        if (code.endsWith("000"))
            return code.substring(0, 9);
        return code;
    }

    public String getType(String code, String type) {
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

    public String getLongCode(String parCode) {
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

    public String file2String(File file) {
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

    void objectSave() {
        File file = new File(path + "object.db");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            outputStream.writeObject(codeHtmlAreas);
            outputStream.flush();
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    void objectLoad() {
        File file = new File(path + "object.db");
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object o = objectInputStream.readObject();
            codeHtmlAreas = (Map<String, List<String[]>>) o;
            System.out.println("read object success!" + codeHtmlAreas.size());
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void createErrorDir() {
        File errDir = new File(errPath);
        if (!errDir.exists()) {
            errDir.mkdir();
        }
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
                    file.renameTo(new File(errPath + file.getName())),
                    html.replaceAll("[\n\r]", "")
            );
        }
    }

}
