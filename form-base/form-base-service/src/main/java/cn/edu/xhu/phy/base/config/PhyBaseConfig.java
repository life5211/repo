package cn.edu.xhu.phy.base.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @date 2020/11/22 23:40
 */
@Configuration
@MapperScan(basePackages = "cn.edu.xhu.phy.**.mapper")
public class PhyBaseConfig {

}
