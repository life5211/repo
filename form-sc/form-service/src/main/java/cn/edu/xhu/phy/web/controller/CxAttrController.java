package cn.edu.xhu.phy.web.controller;

import cn.edu.xhu.phy.base.web.controller.CxAttrBaseController;
import cn.edu.xhu.phy.service.CxAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @date 2020/11/21 19:02
 */
@RestController
public class CxAttrController extends CxAttrBaseController {
    @Autowired
    private CxAttrService cxAttrService;

    @GetMapping("get")
    public Long get() {

        return System.currentTimeMillis() ;
    }
}
