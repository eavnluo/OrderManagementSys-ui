/*
 * 项目名称:platform-plus
 * 类名称:ResourceHandlerConfig.java
 * 包名称:com.platform.config
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2019/3/15 09:58    tp      初版完成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源配置
 *
 * @author tp
 */
@Component
public class SwaggerResourceHandlerConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //swagger
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/static/swagger-ui/");
    }
}
