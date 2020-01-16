package com.cwca.common.accept;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ Author     ：wongs.
 * @ Date       ：Created in 2019/1/24 - 16:02
 * @ Description：日志统一处理器，
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("@annotation(com.cwca.common.accept.LotLogAop)")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String uid = (String) request.getSession().getAttribute("uid");

        if (StringUtils.isNotBlank(uid)) {
/*            TaxUser taxUser = (TaxUser) redisUtils.get(Constants.Env.USER + uid);
            TaxLog taxLog = new TaxLog();
            taxLog.setId(UUIDUtils.getNumID());
            taxLog.setOperate(taxUser.getNsrmc());
            taxLog.setOperateUrl(request.getRequestURL().toString());
            taxLog.setOperateTime(DateUtils.getDateTime(DateUtils.DATE_FORMAT_DEFAULT));
            taxLog.setDescription("IP={" + request.getRemoteAddr() + "}\\n HTTP_METHOD={" + request.getMethod() + "}");
            managerService.insertLog(taxLog);*/
        }
    }

/*    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturing(Object ret){
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log.info("请求结果信息为={}",ret);
    }*/
}
