package com.cwca;

import com.cwca.common.utils.ConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：wongs.
 * @date ：Created in 2019/11/22 - 11:01
 * @description ：**
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(ConfigUtils.getValue("file.staticAccessPath")+"**").addResourceLocations("file:/" + ConfigUtils.getValue("file.uploadFolder"));
    }

}
