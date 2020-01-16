package com.cwca.worship.manager.security;

import com.cwca.common.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/**
 * @author ：wongs.
 * @date ：Created in 2019/11/7 - 13:59
 * @description ：**
 */
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    private ObjectMapper objectMapper = new ObjectMapper();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        // Map -> Json
        String json = objectMapper.writeValueAsString(new Result<>('0',"已经另一台机器登录，您被迫下线。" + event.getSessionInformation().getLastRequest(),""));
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(json);
        redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "/login");
    }
}
