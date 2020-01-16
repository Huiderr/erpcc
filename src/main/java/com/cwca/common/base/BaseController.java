package com.cwca.common.base;

import com.cwca.worship.manager.entity.LotUser;
import com.cwca.worship.manager.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：wongs.
 * @ Date       ：Created in 2018/12/12 - 9:01
 * @ Description：
 */
public class BaseController {

    @Autowired
    private UserService userService;

    private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<>();
    private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<>();
    private static ThreadLocal<ModelMap> currentModelMap = new ThreadLocal<>();

    @ModelAttribute
    protected void initReqAndRep(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        currentRequest.set(request);
        currentResponse.set(response);
        currentModelMap.set(modelMap);
    }

    protected ModelMap modelMap() {
        return currentModelMap.get();
    }

    protected HttpSession getSession() {
        return request().getSession();
    }

    protected HttpServletRequest request() {
        return (HttpServletRequest) currentRequest.get();
    }

    protected HttpServletResponse response() {
        return (HttpServletResponse) currentResponse.get();
    }

    protected boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    protected Map<String, String> getAllParameter() {
        Map<String, String> result = new HashMap<>();
        Enumeration<String> e = request().getParameterNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            String[] values = request().getParameterValues(key);
            String value = StringUtils.join(values, ",");
            if (StringUtils.isBlank(value)) {
                value = null;
            }
            result.put(key, value);
        }
        return result;
    }

    protected LotUser getUserInfo() {
        String userCode = (String) request().getSession().getAttribute("userCode");
        //redis获取
        return userService.loadUserByUserCode(userCode).getData();
    }

    /**
     * 获取当前访问的用户IP地址
     */
    public static String getUserIp() {
        HttpServletRequest request = (HttpServletRequest) currentRequest.get();
        // 获取X-Forwarded-For
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // 获取Proxy-Client-IP
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // WL-Proxy-Client-IP
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // 获取的IP实际上是代理服务器的地址，并不是客户端的IP地址
            ip = request.getRemoteAddr();
        }
        /*
         * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
         * X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
         */
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

}
