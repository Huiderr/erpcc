package com.cwca.common.Exception;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author ：wongs.
 * @date ：Created in 2019/11/21 - 17:35
 * @description ：**
 */
@Component
public class OpenApiErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        switch (String.valueOf(status)){
            case "500":
                return new ModelAndView("/500");
            case "404":
            default:
                return new ModelAndView("/404");
        }
    }
}
