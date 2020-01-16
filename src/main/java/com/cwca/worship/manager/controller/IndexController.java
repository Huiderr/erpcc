package com.cwca.worship.manager.controller;

import com.cwca.common.base.BaseController;
import com.cwca.common.result.Message;
import com.cwca.common.result.Result;
import com.cwca.common.utils.DateUtils;
import com.cwca.worship.manager.entity.LotNotice;
import com.cwca.worship.manager.entity.LotUser;
import com.cwca.worship.manager.service.SetService;
import com.cwca.worship.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：wongs.
 * @date ：Created in 2019/11/25 - 14:25
 * @description ：**
 */
@Controller
@Slf4j
public class IndexController extends BaseController {

    private final UserService userService;
    private final SetService setService;

    public IndexController(UserService userService, SetService setService) {
        this.userService = userService;
        this.setService = setService;
    }

    @RequestMapping(value = "/index/uinfo")
    public String uinfo() {
        LotUser lotUser = getUserInfo();
        modelMap().addAttribute("user",lotUser);
        return "uinfo";
    }

    @RequestMapping(value = "/index/resetInfo")
    @ResponseBody
    public Result<LotUser> resetInfo(String userName, String phone, String address) {
        LotUser lotUser = getUserInfo();
        lotUser.setUserName(userName);
        lotUser.setPhone(phone);
        lotUser.setAddress(address);
        return userService.saveUserInfo(lotUser);
    }

    @RequestMapping(value = "/index/upassword")
    public String upassword() {
        LotUser lotUser = getUserInfo();
        modelMap().addAttribute("user",lotUser);
        return "upassword";
    }

    @RequestMapping(value = "/index/resetPwd")
    @ResponseBody
    public Result<LotUser> resetPwd(String oldPassword,String newPassword) {
        LotUser lotUser = getUserInfo();
        if(!new BCryptPasswordEncoder().matches(oldPassword,lotUser.getPassword())) {
            return new Result<>(Message.Login.FAILURE_UPDATE_PWDERR);
        }
        lotUser.setUpdater(lotUser.getUserCode());
        lotUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        return userService.saveUserInfo(lotUser);
    }

    @RequestMapping(value = "/console")
    public String console() {
        return "admin/console";
    }

    /**
     * @ 客户端接口部分
     */
    @RequestMapping(value = "/index/notice")
    @ResponseBody
    public Result<List<LotNotice>> notice(String trade, String notice) {
        Map<String,String> map = new HashMap<>(3);
        map.put("vaildTime", DateUtils.getDateTime(DateUtils.DATE_FORMAT_TYPE_TWO));
        map.put("trade",trade);
        map.put("notice",notice);
        return setService.loadNoticeList(map);
    }

    @RequestMapping(value = "/index/notice/detail")
    public String noticeDetail(String id) {
        modelMap().addAttribute("lotNotice",setService.loadNoticeById(id).getData());
        return "detail";
    }

    @RequestMapping(value = "/index/notice/list")
    @ResponseBody
    public Result<List<LotNotice>> list() {
        Map<String, String> map = getAllParameter();
        map.put("vaildTime", DateUtils.getDateTime(DateUtils.DATE_FORMAT_TYPE_TWO));
        return setService.loadNoticeList(map);
    }
}
