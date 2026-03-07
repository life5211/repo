package com.tydic.work.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.tydic.work.service.AppnamespaceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * (Attr)表控制层
 *
 * @author makejava
 * @since 2021-01-18 16:49:13
 */
@RestController
@RequestMapping("name")
public class NamespaceController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AppnamespaceService appnamespaceService;


    @PostMapping
    public void add(String name) {

        Arrays.stream(("third\n" +
                "taglib\n" +
                "system_realTimeTagInCfg\n" +
                "system_function\n" +
                "system_environment\n" +
                "system_data_columnCodeDefine2000\n" +
                "system_data_columnCodeDefine1000\n" +
                "system_data_channelAbl\n" +
                "system_data\n" +
                "openapi_complex\n" +
                "mkt_openapijt_message\n" +
                "mkt_offer_message\n" +
                "job_execute\n" +
                "hive_es\n" +
                "hbase_order\n" +
                "file_hive\n" +
                "event_collect\n" +
                "cache\n" +
                "common-configuration\n" +
                "common-datasource\n" +
                "common-mktEventSourceConsumer\n" +
                "common-mktOpenapijtMsgConsumer\n" +
                "common-mktOrderRecycleConsumer\n" +
                "common-mktScheduleConsumer\n" +
                "common-mycat-datasource\n" +
                "common-oracle-datasource\n" +
                "common-test-datasource\n" +
                "common-mktOfferingConsumer\n" +
                "mkt_adapter_message_5GVolte\n" +
                "mkt_openapijt_message_smart_call\n" +
                "common-MktAdapterMsg5GConsumer"
        ).split("\n")).sorted()
                .forEach(appnamespaceService::insert);


    }


}
