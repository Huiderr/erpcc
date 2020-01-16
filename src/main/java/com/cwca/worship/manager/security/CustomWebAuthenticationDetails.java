package com.cwca.worship.manager.security;

import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：wongs.
 * @date ：Created in 2019/11/7 - 14:12
 * @description ：获取用户登录时携带的额外信息
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = 6975601077710753878L;
    @Getter
    private final String flag;

    CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        flag = request.getParameter("flag");
    }

    @Override
    public String toString() {
        return super.toString() + "; flag: " + this.getFlag();
    }
}
