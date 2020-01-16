package com.cwca.worship.manager.controller;

import com.cwca.common.base.BaseController;
import com.cwca.common.result.Result;
import com.cwca.worship.manager.entity.LotRoute;
import com.cwca.worship.manager.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(value = "/route")
@PreAuthorize("hasRole('ROLE_U_ROUTE')")
public class RouteController extends BaseController {

    private final ManagerService managerService;

    public RouteController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @RequestMapping(value = "/")
    public String route() {
        modelMap().addAttribute("",getUserInfo());
        return "admin/route";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Result<List<LotRoute>> list() {
        Map<String, String> map = getAllParameter();
        return managerService.loadRouteList(map);
    }

    @RequestMapping(value = "/listPage")
    @ResponseBody
    public Result<Page<LotRoute>> listPage() {
        Map<String, String> map = getAllParameter();
        return managerService.loadRouteListPage(map);
    }

    @GetMapping(value = "/change")
    public String change(String id) {
        if(StringUtils.isNotBlank(id)){
            modelMap().addAttribute("route",managerService.loadRouteById(id).getData());
        }
        return "admin/route_form";
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public Result<String> insert(LotRoute lotRoute) {
        lotRoute.setCreater(getUserInfo().getUserCode());
        return managerService.saveRouteInfo(lotRoute);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Result<String> update(LotRoute lotRoute) {
        lotRoute.setUpdater(getUserInfo().getUserCode());
        return managerService.saveRouteInfo(lotRoute);
    }

    @RequestMapping(value = "/vaild")
    @ResponseBody
    public Result<String> routeVaild(String id,String status) {
        return managerService.vaild("route",id,status);
    }

}