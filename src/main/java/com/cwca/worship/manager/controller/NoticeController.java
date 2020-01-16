package com.cwca.worship.manager.controller;

import com.cwca.common.base.BaseController;
import com.cwca.common.result.Message;
import com.cwca.common.result.Result;
import com.cwca.common.utils.ConfigUtils;
import com.cwca.common.utils.UUIDUtils;
import com.cwca.worship.manager.entity.DmLotTrade;
import com.cwca.worship.manager.entity.LotNotice;
import com.cwca.worship.manager.service.SetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author     ：wongs.
 * @date       ：Created in 2018/12/12 - 9:35
 * @description： 公告视图
 */
@Controller
@Slf4j
@RequestMapping(value = "/set/notice")
@PreAuthorize("hasRole('ROLE_S_NOTICE')")
public class NoticeController extends BaseController {

    private final SetService setService;

    public NoticeController(SetService setService) {
        this.setService = setService;
    }

    @RequestMapping(value = "/")
    public String notice() {
        modelMap().addAttribute("user",getUserInfo());
        return "admin/notice";
    }

    @GetMapping(value = "/add")
    public String add() {
        return "admin/notice_form";
    }

    @RequestMapping(value = "/listPage")
    @ResponseBody
    public Result<Page<LotNotice>> listPage() {
        Map<String, String> map = getAllParameter();
        return setService.loadNoticeListPage(map);
    }

    @RequestMapping(value = "/listTrade")
    @ResponseBody
    public Result<List<DmLotTrade>> listTrade() {
        return setService.loadDmLotTradeList();
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public Result<String> insert(LotNotice lotNotice) {
        lotNotice.setCreater(getUserInfo().getUserCode());
        String[] times = lotNotice.getStart().split("到");
        lotNotice.setStart(times[0].trim());
        lotNotice.setEnd(times[1].trim());
        return setService.saveNoticeInfo(lotNotice);
    }

    @RequestMapping(value = "/image")
    @ResponseBody
    public Result<String> image() {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request()).getFiles("file");
        String base = ConfigUtils.getValue("file.uploadFolder")+"notice/";
        log.info("通知公告图片上传存储路径：{}", base);
        Result<String> result;
        if (files.get(0) != null && !StringUtils.isBlank(files.get(0).getOriginalFilename())) {
            try {
                //原文件名
                String fileOriginalName = files.get(0).getOriginalFilename();
                //文件后缀
                String extension = FilenameUtils.getExtension(fileOriginalName);
                String fileName = UUIDUtils.getNumID() + "." + extension;
                //上传文件
                files.get(0).transferTo(new File(base, fileName));
                String accessUrl = ConfigUtils.getValue("file.staticAccessPath")+"notice/";
                return new Result<>(accessUrl + fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
                result = new Result<>(Message.FAILURE);
                result.addMessage("文件上传异常！");
                return result;
            }
        }else{
            result = new Result<>(Message.FAILURE);
            result.addMessage("文件上传异常！");
            return result;
        }
    }

    @RequestMapping(value = "/vaild")
    @ResponseBody
    public Result<String> vaild(String id,String status) {
        return setService.vaild("notice",id,status);
    }

}