package com.web.get;

import org.junit.Test;

public class RegionTest {
    @Test
    public void run() {
        Http http = new Http();
        System.out.println(http.getReferer("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/21/01/05/210105003.html"));
        System.out.println(http.getReferer("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/12/01/120103.html"));
        System.out.println(http.getReferer("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/11/1101.html"));
        System.out.println(http.getReferer("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/11.html"));
    }

    @Test
    public void runA() {
        new Region().run();
    }
}
