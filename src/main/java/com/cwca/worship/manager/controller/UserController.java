package com.cwca.worship.manager.controller;

import com.cwca.common.base.BaseController;
import com.cwca.common.result.Result;
import com.cwca.worship.manager.entity.DmLotTrade;
import com.cwca.worship.manager.entity.LotRole;
import com.cwca.worship.manager.entity.LotUser;
import com.cwca.worship.manager.service.ManagerService;
import com.cwca.worship.manager.service.SetService;
import com.cwca.worship.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author     ：wongs.
 * @date       ：Created in 2018/12/12 - 9:35
 * @description：
 */
@Controller
@Slf4j
@RequestMapping(value = "/user")
@PreAuthorize("hasRole('ROLE_U_USER')")
public class UserController extends BaseController {

    private final UserService userService;
    private final ManagerService managerService;
    private final SetService setService;

    public UserController(UserService userService, ManagerService managerService, SetService setService) {
        this.userService = userService;
        this.managerService = managerService;
        this.setService = setService;
    }

    @RequestMapping(value = "/")
    public String user() {
        modelMap().addAttribute("user",getUserInfo());
        return "admin/user";
    }

    @RequestMapping(value = "/listPage")
    @ResponseBody
    public Result<Page<LotUser>> listPage() {
        Map<String, String> map = getAllParameter();
        return userService.loadUserlistPage(map);
    }

    @GetMapping(value = "/change")
    public String change(String id) {
        if(StringUtils.isNotBlank(id)){
            modelMap().addAttribute("user",userService.loadUserByUserId(id).getData());
        }
        return "admin/user_form";
    }

    @RequestMapping(value = "/listTrade")
    @ResponseBody
    public Result<List<DmLotTrade>> listTrade() {
        return setService.loadDmLotTradeList();
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public Result<LotUser> insert(LotUser lotUser) {
        lotUser.setPassword(new BCryptPasswordEncoder().encode(lotUser.getPassword()));
        lotUser.setCreater(getUserInfo().getUserCode());
        return userService.saveUserInfo(lotUser);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Result<LotUser> update(LotUser lotUser) {
        lotUser.setUpdater(getUserInfo().getUserCode());
        return userService.saveUserInfo(lotUser);
    }

    @RequestMapping(value = "/vaild")
    @ResponseBody
    public Result<String> vaild(String id,String status) {
        return userService.vaild(id,status);
    }

    @RequestMapping(value = "/listRole")
    @ResponseBody
    public Result<List<LotRole>> listRole() {
        Map<String, String> map = getAllParameter();
        return managerService.loadRoleList(map);
    }

    @RequestMapping(value = "/authorize")
    @ResponseBody
    public Result<String> authorize(String userIds,String roleCode) {
        return userService.authorize(userIds,roleCode);
    }

}