package com.cwca.worship.manager.controller;

import com.cwca.common.base.BaseController;
import com.cwca.common.result.Result;
import com.cwca.common.utils.TreeUtils;
import com.cwca.worship.manager.entity.LotRole;
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
@RequestMapping(value = "/role")
@PreAuthorize("hasRole('ROLE_U_ROLE')")
public class RoleController extends BaseController {

    private final ManagerService managerService;

    public RoleController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @RequestMapping(value = "/")
    public String role() {
        modelMap().addAttribute("",getUserInfo());
        return "admin/role";
    }

    @RequestMapping(value = "/listPage")
    @ResponseBody
    public Result<Page<LotRole>> listPage() {
        Map<String, String> map = getAllParameter();
        return managerService.loadRoleListPage(map);
    }

    @GetMapping(value = "/change")
    public String change(String id) {
        if(StringUtils.isNotBlank(id)){
            modelMap().addAttribute("role",managerService.loadRoleById(id).getData());
        }
        return "admin/role_form";
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public Result<String> insert(LotRole lotRole) {
        lotRole.setCreater(getUserInfo().getUserCode());
        return managerService.saveRoleInfo(lotRole);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Result<String> update(LotRole lotRole) {
        lotRole.setUpdater(getUserInfo().getUserCode());
        return managerService.saveRoleInfo(lotRole);
    }

    @RequestMapping(value = "/vaild")
    @ResponseBody
    public Result<String> roleVaild(String id,String status) {
        return managerService.vaild("role",id,status);
    }

    @RequestMapping(value = "/listRoute")
    @ResponseBody
    public Result<List<LotRoute>> listRoute() {
        Map<String, String> map = getAllParameter();
        List<LotRoute> lotRoutes = managerService.loadRouteList(map).getData();
        List<Map<Integer,Integer>> roleRoutes = managerService.loadRoleRouteById(map.get("roleId")).getData();
        for (LotRoute route : lotRoutes) {
            for (Map<Integer,Integer> map1 : roleRoutes) {
                if (route.getId() == map1.get("route_id")) {
                    route.setRemark("true");
                    break;
                }else{
                    route.setRemark("false");
                }
            }
        }
        return new Result<>(TreeUtils.switchNodeListToTree(lotRoutes));
    }

    @RequestMapping(value = "/authorize")
    @ResponseBody
    public Result<String> authorize(String roleId,String routeIds) {
        return managerService.authorize(roleId,routeIds);
    }

}