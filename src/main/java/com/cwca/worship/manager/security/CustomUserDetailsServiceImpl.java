package com.cwca.worship.manager.security;

import com.cwca.common.Constants;
import com.cwca.common.utils.ConfigUtils;
import com.cwca.worship.manager.entity.LotRole;
import com.cwca.worship.manager.entity.LotUser;
import com.cwca.worship.manager.service.ManagerService;
import com.cwca.worship.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 11:08
 * @description ：认证和授权
 */
@Service
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final ManagerService managerService;

    public CustomUserDetailsServiceImpl(UserService userService, ManagerService managerService) {
        this.userService = userService;
        this.managerService = managerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("验证登陆用户 ：：：：：> {}", username);
        LotUser lotUser = userService.loadUserByUserCode(username).getData();
        if (lotUser == null) {
            log.info("验证登陆结果 ：：：：：> 账号不存在");
            throw new UsernameNotFoundException("账号不存在");
        }
        if (Constants.Env.STATUS_DISABLED.equals(lotUser.getStatus())) {
            log.info("验证登陆结果 ：：：：：> 账号已被禁用");
            throw new DisabledException("账号已被禁用");
        }
        log.info("授权用户权限 ：：：：：> {}", username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        /*
         * Spring Boot 2.0 版本踩坑
         * 必须要 ROLE_ 前缀
         * 如果不加前缀一般就会出现403错误
         * 在给用户赋权限时,数据库存储必须是完整的权限标识ROLE_LEVEL1
         */
        if(!ConfigUtils.getValue("super_user").equals(username)){
            LotRole lotRole = lotUser.getLotRole();
            if (lotRole.getLotRoute() != null && lotRole.getLotRoute().size()>0){
                lotRole.getLotRoute().forEach(lotRoute ->  grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+lotRoute.getCode())));
            }
        }else{
            Map<String, String> map = new HashMap<>(1);
            map.put("status",Constants.Env.STATUS_ENABLED);
            managerService.loadRouteList(map).getData().forEach(lotRoute ->  grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+lotRoute.getCode())));
        }
        return new User(username,lotUser.getPassword(),grantedAuthorities);
    }

}