package com.cwca.worship.manager.controller;

import com.cwca.common.Constants;
import com.cwca.common.base.BaseController;
import com.cwca.common.utils.ConfigUtils;
import com.cwca.common.utils.TreeUtils;
import com.cwca.worship.manager.entity.LotUser;
import com.cwca.worship.manager.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author     ：wongs.
 * @date       ：Created in 2018/12/12 - 9:35
 * @description：
 */
@Controller
@Slf4j
public class LoginController extends BaseController {

    private final ManagerService managerService;

    public LoginController( ManagerService managerService) {
        this.managerService = managerService;
    }

    @RequestMapping(value = "/")
    public String entry() {
        return "login";
    }
    /**
     * @ Description 登陆
     **/
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String invalid() {
        return "Session 已过期，请重新登录";
    }

    @RequestMapping(value = "/index")
    public String index() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        request().getSession().setAttribute("userCode",username);
        log.info("用户登陆 ：：：：：> {} ", username);
        LotUser lotUser = getUserInfo();
        modelMap().addAttribute("user",lotUser);
        if(!ConfigUtils.getValue("super_user").equals(lotUser.getUserCode())){
            modelMap().addAttribute("routes", TreeUtils.switchNodeListToTree(managerService.loadRoleRoutesById(lotUser.getLotRole().getId()+"").getData()));
        }else{
            Map<String, String> map = new HashMap<>(1);
            map.put("status", Constants.Env.STATUS_ENABLED);
            map.put("routeType", lotUser.getUserType());
            modelMap().addAttribute("routes",TreeUtils.switchNodeListToTree(managerService.loadRouteList(map).getData()));
        }
        //customer,manager
        if("customer".equals(lotUser.getUserType())){
            return "index";
        }else{
            return "admin/index";
        }
    }

    @RequestMapping(value = "/403")
    public String autho() {
        return "403";
    }

    @RequestMapping(value = "/404")
    public String none() {
        return "404";
    }

    @RequestMapping(value = "/500")
    public String error() {
        return "500";
    }


}