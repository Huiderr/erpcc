package com.cwca.worship.manager.service;

import com.cwca.common.Exception.LotException;
import com.cwca.common.result.Result;
import com.cwca.worship.manager.entity.LotUser;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 11:08
 * @description ：**
 */
public interface UserService {

    /**
     * @description 获取用户信息
     * @param username::userCode
     * @return Result<LotUser>
     */
    Result<LotUser> loadUserByUserCode(String username) throws LotException;

    Result<LotUser> saveUserInfo(LotUser lotUser) throws LotException;

    Result<Page<LotUser>> loadUserlistPage(Map<String, String> map) throws LotException;

    Result<String> vaild(String id,String status) throws LotException;

    Result<LotUser> loadUserByUserId(String id) throws LotException;

    Result<String> authorize(String userIds,String roleCode) throws LotException;

}
