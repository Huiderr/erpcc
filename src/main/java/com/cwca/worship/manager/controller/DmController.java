package com.cwca.worship.manager.controller;

import com.cwca.common.base.BaseController;
import com.cwca.common.result.Result;
import com.cwca.common.utils.DateUtils;
import com.cwca.worship.manager.entity.DmLotCommon;
import com.cwca.worship.manager.entity.DmTables;
import com.cwca.worship.manager.service.SetService;
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
 * @description： 行业视图
 */
@Controller
@Slf4j
@RequestMapping(value = "/set/dm")
@PreAuthorize("hasRole('ROLE_S_DM')")
public class DmController extends BaseController {

    private final SetService setService;

    public DmController(SetService setService) {
        this.setService = setService;
    }

    @RequestMapping(value = "/")
    public String dm() {
        modelMap().addAttribute("user",getUserInfo());
        return "admin/dm";
    }

    @RequestMapping(value = "/listPage")
    @ResponseBody
    public Result<Page<DmTables>> listPage() {
        Map<String, String> map = getAllParameter();
        return setService.loadDmTablesListPage(map);
    }

    @RequestMapping(value = "/vaild")
    @ResponseBody
    public Result<String> vaild(String id,String status) {
        return setService.vaild("dm",id,status);
    }


    @RequestMapping(value = "/table/List")
    @ResponseBody
    public Result<List<DmLotCommon>> tableList(String dmTableCode) {
        Result<List<DmLotCommon>> result = setService.loadDmLotCommonList(dmTableCode);
        return result;
    }

    @GetMapping(value = "/table/change")
    public String change(String table,String id) {
        DmLotCommon dmLotCommon = new DmLotCommon();
        dmLotCommon.setTableName(table);
        if(StringUtils.isNotBlank(id)){
            modelMap().addAttribute("dmTable",setService.loadDmLotCommonById(table,id).getData());
        }else{
            modelMap().addAttribute("dmTable",dmLotCommon);
        }
        return "admin/dm_form";
    }

    @RequestMapping(value = "/table/insert")
    @ResponseBody
    public Result<String> insert(DmLotCommon dmLotCommon) {
        dmLotCommon.setCreater(getUserInfo().getUserCode());
        dmLotCommon.setCreateTime(DateUtils.getDateTime(DateUtils.DATE_FORMAT_DEFAULT));
        return setService.saveDmLotCommon(dmLotCommon);
    }

    @RequestMapping(value = "/table/update")
    @ResponseBody
    public Result<String> update(DmLotCommon dmLotCommon) {
        dmLotCommon.setUpdater(getUserInfo().getUserCode());
        dmLotCommon.setUpdateTime(DateUtils.getDateTime(DateUtils.DATE_FORMAT_DEFAULT));
        return setService.saveDmLotCommon(dmLotCommon);
    }

    @RequestMapping(value = "/table/vaild")
    @ResponseBody
    public Result<String> vaild(String table, String id,String status) {
        return setService.dmVaild(table,id,status);
    }

    @RequestMapping(value = "/table/delete")
    @ResponseBody
    public Result<String> delete(String table, String id) {
        return setService.dmDelete(table,id);
    }
}