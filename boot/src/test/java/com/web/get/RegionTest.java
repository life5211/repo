package com.web.get;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class RegionTest {

    Region region = new Region();

    @Test
    public void run() {
        Http http = new Http();
        System.out.println(http.getReferer("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/21/01/05/210105003.html"));
        System.out.println(http.getReferer("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/12/01/120103.html"));
        System.out.println(http.getReferer("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/11/1101.html"));
        System.out.println(http.getReferer("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/11.html"));
    }

    @Test
    public void runParse() {
        region.run();

    }

    @Test
    public void runLoad() {
        region.runLoadAndPrint();
    }

    @Test
    public void crt() {
        try {
            boolean newFile = new File("c://d/1.json").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
