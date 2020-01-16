package com.cwca.worship.manager.controller;

import com.cwca.common.base.BaseController;
import com.cwca.common.result.Result;
import com.cwca.worship.manager.entity.DmLotTrade;
import com.cwca.worship.manager.service.SetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author     ：wongs.
 * @date       ：Created in 2018/12/12 - 9:35
 * @description： 行业视图
 */
@Controller
@Slf4j
@RequestMapping(value = "/set/trade")
@PreAuthorize("hasRole('ROLE_S_TRADE')")
public class TradeController extends BaseController {

    private final SetService setService;

    public TradeController(SetService setService) {
        this.setService = setService;
    }

    @RequestMapping(value = "/")
    public String trade() {
        modelMap().addAttribute("user",getUserInfo());
        return "admin/trade";
    }

    @GetMapping(value = "/add")
    public String add() {
        return "admin/trade_form";
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public Result<String> insert(DmLotTrade dmLotTrade) {
        dmLotTrade.setCreater(getUserInfo().getUserCode());
        return setService.saveDmLotTradeInfo(dmLotTrade);
    }

    @RequestMapping(value = "/listPage")
    @ResponseBody
    public Result<Page<DmLotTrade>> listPage() {
        Map<String, String> map = getAllParameter();
        return setService.loadDmLotTradeListPage(map);
    }

    @RequestMapping(value = "/vaild")
    @ResponseBody
    public Result<String> vaild(String id,String status) {
        return setService.vaild("trade",id,status);
    }

}