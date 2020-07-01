package com.web.get;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Http {

    private Charset gb2312 = Charset.forName("gb2312");

    private String[] userAgents;
    private Set<String> cookies;

    {
        userAgents = userAgentStr.split("\n");
        cookies = new HashSet<>();
        cookies.add("");
    }

    public String requestForText(String url) {
        try {
            // 创建url
            URL realurl = new URL(url);
            // 打开连接
            URLConnection connection = realurl.openConnection();
            // 设置连接方式：get
//            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 设置通用的请求属性
            connection.setRequestProperty("Accept", "*/*,text/html");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cookie", getCookie());
            connection.setRequestProperty("Host", "www.stats.gov.cn");
            connection.setRequestProperty("Referer", getReferer(url));
            connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
            connection.setRequestProperty("User-agent", getUserAgent());
            // 建立连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段，获取到cookies等
            addCookie(map);
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            return toSring(connection.getInputStream(), "gb2312");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void addCookie(Map<String, List<String>> map) {
        cookies.addAll(map.get("cookie"));
        cookies.addAll(map.get("Cookie"));
    }

    private String getCookie() {
        for (String cookie : cookies) {
            return cookie;
        }
        return null;
    }

    private String getUserAgent() {
        return userAgents[(int) ((Math.random() * userAgents.length))];
    }

    public String getReferer(String url) {
        int i = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019".length();
        String path = url.substring(i, url.lastIndexOf("/"));
        int ei = path.lastIndexOf("/");
        return ei == -1 ? url.substring(0, i) :
                url.substring(0, i)
                        + path.substring(0, ei) + "/"
                        + path.replace("/", "") + ".html";
    }

    private String toSring(InputStream inputStream, String charset) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int length;
        try {
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static String userAgentStr =
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36\n" +
                    "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36\n" +
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36\n" +
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.35\n" +
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.18363\n" +
                    "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko\n" +
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1\n" +
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0\n" +
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50\n" +
                    "Opera/9.80 (Windows NT 6.1; U; zh-cn) Presto/2.9.168 Version/11.5\n" +
                    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; Tablet PC 2.0; .NET4.0E)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; InfoPath.3)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB7.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; ) AppleWebKit/534.12 (KHTML, like Gecko) Maxthon/3.0 Safari/534.12\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E; SE 2.X MetaSr 1.0)\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.33 Safari/534.3 SE 2.X MetaSr 1.0\n" +
                    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)\n" +
                    "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1 QQBrowser/6.9.11079.201\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E) QQBrowser/6.9.11079.201\n" +
                    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)\n" +
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1\n" +
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11\n" +
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50\n" +
                    "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11\n" +
                    "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11\n" +
                    "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko\n" +
                    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0;\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB7.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)\n" +
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1\n" +
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11\n" +
                    "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko\n" +
                    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0;\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB7.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)\n" +
                    "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)\n" +
                    "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)\n" +
                    "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)\n" +
                    "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; Acoo Browser 1.98.744; .NET CLR 3.5.30729)\n" +
                    "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; Acoo Browser 1.98.744; .NET CLR 3.5.30729)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; Acoo Browser; GTB5; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; InfoPath.1; .NET CLR 3.5.30729; .NET CLR 3.0.30618)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; SV1; Acoo Browser; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; Avant Browser)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; GTB5; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; Maxthon; InfoPath.1; .NET CLR 3.5.30729; .NET CLR 3.0.30618)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; GTB5;\n" +
                    "Mozilla/4.0 (compatible; Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; Acoo Browser 1.98.744; .NET CLR 3.5.30729); Windows NT 5.1; Trident/4.0)\n" +
                    "Mozilla/4.0 (compatible; Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB6; Acoo Browser; .NET CLR 1.1.4322; .NET CLR 2.0.50727); Windows NT 5.1; Trident/4.0; Maxthon; .NET CLR 2.0.50727; .NET CLR 1.1.4322; InfoPath.2)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; Acoo Browser; GTB6; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; InfoPath.1; .NET CLR 3.5.30729; .NET CLR 3.0.30618)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; Acoo Browser; GTB5; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; InfoPath.1; .NET CLR 3.5.30729; .NET CLR 3.0.30618)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; GTB6; Acoo Browser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Trident/4.0; Acoo Browser; GTB5; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; InfoPath.1; .NET CLR 3.5.30729; .NET CLR 3.0.30618)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; GTB5; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; GTB5; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; InfoPath.1; .NET CLR 3.5.30729; .NET CLR 3.0.30618)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Acoo Browser; InfoPath.2; .NET CLR 2.0.50727; Alexa Toolbar)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Acoo Browser; .NET CLR 2.0.50727; .NET CLR 1.1.4322)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Acoo Browser; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR 2.0.50727; FDM; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; InfoPath.2)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Acoo Browser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; America Online Browser 1.1; Windows NT 5.1; (R1 1.5); .NET CLR 2.0.50727; InfoPath.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; America Online Browser 1.1; rev1.5; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; America Online Browser 1.1; rev1.5; Windows NT 5.1; .NET CLR 1.1.4322)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; America Online Browser 1.1; rev1.5; Windows NT 5.1; .NET CLR 1.0.3705; .NET CLR 1.1.4322; Media Center PC 4.0; InfoPath.1; .NET CLR 2.0.50727; Media Center PC 3.0; InfoPath.2)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; America Online Browser 1.1; rev1.2; Windows NT 5.1; SV1; .NET CLR 1.1.4322)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; SV1; HbTools 4.7.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; SV1; FunWebProducts; .NET CLR 1.1.4322; InfoPath.1; HbTools 4.8.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; SV1; FunWebProducts; .NET CLR 1.0.3705; .NET CLR 1.1.4322; Media Center PC 3.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; SV1; .NET CLR 1.1.4322; HbTools 4.7.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; SV1; .NET CLR 1.1.4322)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; SV1; .NET CLR 1.0.3705; .NET CLR 1.1.4322; Media Center PC 3.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; SV1; .NET CLR 1.0.3705; .NET CLR 1.1.4322)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; SV1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; FunWebProducts; (R1 1.5); HbTools 4.7.7)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1; FunWebProducts)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.1)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows NT 5.0)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; Windows 98)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; rev1.5; Windows NT 5.1; SV1; FunWebProducts; .NET CLR 1.1.4322)\n" +
                    "Mozilla/4.0 (compatible; MSIE 6.0; America Online Browser 1.1; rev1.5; Windows NT 5.1; SV1; .NET CLR 1.1.4322; InfoPath.1)\n" +
                    "Mozilla/5.0 (compatible; MSIE 9.0; AOL 9.7; AOLBuild 4343.19; Windows NT 6.1; WOW64; Trident/5.0; FunWebProducts)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; AOL 9.7; AOLBuild 4343.27; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; AOL 9.7; AOLBuild 4343.21; Windows NT 5.1; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; .NET4.0E)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; AOL 9.7; AOLBuild 4343.19; Windows NT 5.1; Trident/4.0; GTB7.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)\n" +
                    "Mozilla/4.0 (compatible; MSIE 8.0; AOL 9.7; AOLBuild 4343.19; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; .NET4.0E)\n" +
                    "Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.7; AOLBuild 4343.19; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; .NET4.0E)\n" +
                    "Mozilla/5.0 (X11; U; SunOS sun4u; en-US; rv:1.8.1.12) Gecko/20080210 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.0.1) Gecko/2008072610 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.8.1.12) Gecko/20080214 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.8.1.12) Gecko/20080203 SUSE/2.0.0.12-0.1 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.12) Gecko/20080207 Ubuntu/7.10 (gutsy) Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.12) Gecko/20080203 SUSE/2.0.0.12-0.1 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux x86_64; de; rv:1.8.1.12) Gecko/20080208 Fedora/2.0.0.12-1.fc8 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux x86_64; de; rv:1.8.1.12) Gecko/20080203 SUSE/2.0.0.12-6.1 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux x86; sv-SE; rv:1.8.1.12) Gecko/20080207 Ubuntu/8.04 (hardy) Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; fr; rv:1.8.1.12) Gecko/20080208 Fedora/2.0.0.12-1.fc8 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; fr-FR; rv:1.8.1.6) Gecko/20080208 Ubuntu/7.10 (gutsy) Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; es-ES; rv:1.8.1.12) Gecko/20080213 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; es-AR; rv:1.8.1.12) Gecko/20080207 Ubuntu/7.10 (gutsy) Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.14) Gecko/20080419 Ubuntu/8.04 (hardy) Firefox/2.0.0.12 MEGAUPLOAD 1.0\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.12) Gecko/20080208 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.12) Gecko/20080208 Fedora/2.0.0.12-1.fc8 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.12) Gecko/20080201 Firefox/2.0.0.12 Mnenhy/0.7.5.666\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.12) Gecko/20080129 Firefox/2.0.0.12 (Debian-2.0.0.12-0etch1)\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:1.8.1.12) Gecko/20080203 SUSE/2.0.0.12-2.1 Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; de; rv:1.8.1.12) Gecko/20080207 Ubuntu/7.10 (gutsy) Firefox/2.0.0.12\n" +
                    "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.8.1.10) Gecko/20061201 Firefox/2.0.0.10 (Ubuntu-feisty)\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.8.1.10) Gecko/20071213 Fedora/2.0.0.10-3.fc8 Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.8.1.10) Gecko/20071128 Fedora/2.0.0.10-2.fc7 Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; pl-PL; rv:1.8.1.10) Gecko/20071126 Ubuntu/7.10 (gutsy) Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.17) Gecko/20080827 Firefox/2.0.0.10 (Debian-2.0.0.17-0etch1)\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.10) Gecko/20071213 Fedora/2.0.0.10-3.fc8 Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.10) Gecko/20071203 Ubuntu/7.10 (gutsy) Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.10) Gecko/20071128 Fedora/2.0.0.10-2.fc7 Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.10) Gecko/20071126 Ubuntu/7.10 (gutsy) Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.10) Gecko/20071115 Firefox/2.0.0.10 (Debian-2.0.0.10-0etch1)\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.10) Gecko/20071115 Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.10) Gecko/20071015 SUSE/2.0.0.10-0.2 Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.10) Gecko/20061201 Firefox/2.0.0.10 (Ubuntu-feisty)\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.10) Gecko/20060601 Firefox/2.0.0.10 (Ubuntu-edgy)\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; en-GB; rv:1.8.1.10) Gecko/20071126 Ubuntu/7.10 (gutsy) Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686; de; rv:1.8.1.10) Gecko/20071126 Ubuntu/7.10 (gutsy) Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686 (x86_64); en-US; rv:1.8.1.10) Gecko/20071115 Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686 (x86_64); en-US; rv:1.8.1.10) Gecko/20071015 SUSE/2.0.0.10-0.2 Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; Linux i686 (x86_64); en-US; rv:1.8.1.10) Gecko/20071015 SUSE/2.0.0.10-0.1 Firefox/2.0.0.10\n" +
                    "Mozilla/5.0 (X11; U; FreeBSD i386; en-US; rv:1.8.1.4) Gecko/20070515 Firefox/2.0.0.10\n" +
                    "Opera/9.64(Windows NT 5.1; U; en) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux x86_64; U; pl) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux x86_64; U; hr) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux x86_64; U; en-GB) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux x86_64; U; en) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux x86_64; U; de) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux x86_64; U; cs) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux i686; U; tr) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux i686; U; sv) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux i686; U; pl) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux i686; U; nb) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux i686; U; Linux Mint; nb) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux i686; U; Linux Mint; it) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux i686; U; en) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux i686; U; de) Presto/2.1.1\n" +
                    "Opera/9.64 (X11; Linux i686; U; da) Presto/2.1.1\n" +
                    "Opera/9.64 (Windows NT 6.1; U; MRA 5.5 (build 02842); ru) Presto/2.1.1\n" +
                    "Opera/9.64 (Windows NT 6.1; U; de) Presto/2.1.1\n" +
                    "Opera/9.64 (Windows NT 6.0; U; zh-cn) Presto/2.1.1\n" +
                    "Opera/9.64 (Windows NT 6.0; U; pl) Presto/2.1.1\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; tr-TR) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; ko-KR) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; fr-FR) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; cs-CZ) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.0; ja-JP) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_5_8; zh-cn) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10_5_8; ja-jp) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_7; ja-jp) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; zh-cn) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; sv-se) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; ko-kr) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; ja-jp) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; it-it) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; fr-fr) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; es-es) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; en-us) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; en-gb) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; de-de) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27\n" +
                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.803.0 Safari/535.1\n" +
                    "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.1 (KHTML, like Gecko) Ubuntu/11.04 Chromium/14.0.803.0 Chrome/14.0.803.0 Safari/535.1\n" +
                    "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.803.0 Safari/535.1\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.803.0 Safari/535.1\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_7) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.803.0 Safari/535.1\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_8) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.803.0 Safari/535.1\n" +
                    "Mozilla/5.0 Slackware/13.37 (X11; U; Linux x86_64; en-US) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41\n" +
                    "Mozilla/5.0 ArchLinux (X11; Linux x86_64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.1 (KHTML, like Gecko) Ubuntu/11.04 Chromium/13.0.782.41 Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (Windows NT 6.0; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (Windows NT 5.2; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_7) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_3) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1\n" +
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_2) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1";
}
