package com.cwca.worship.manager.service;

import com.cwca.common.Exception.LotException;
import com.cwca.common.result.Result;
import com.cwca.worship.manager.entity.LotRole;
import com.cwca.worship.manager.entity.LotRoute;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 11:08
 * @description ：管理聚合服务
 */
public interface ManagerService {

    Result<String> vaild(String table,String id,String status) throws LotException;

    Result<String> saveRoleInfo(LotRole lotRole) throws LotException;

    Result<List<LotRole>> loadRoleList(Map<String, String> map) throws LotException;

    Result<Page<LotRole>> loadRoleListPage(Map<String, String> map) throws LotException;

    Result<LotRole> loadRoleById(String id) throws LotException;

    Result<List<Map<Integer,Integer>>> loadRoleRouteById(String roleId) throws LotException;

    Result<List<LotRoute>> loadRoleRoutesById(String roleId) throws LotException;

    Result<String> authorize(String roleId,String routeIds) throws LotException;

    Result<String> saveRouteInfo(LotRoute lotRoute) throws LotException;

    Result<List<LotRoute>> loadRouteList(Map<String, String> map) throws LotException;

    Result<Page<LotRoute>> loadRouteListPage(Map<String, String> map) throws LotException;

    Result<LotRoute> loadRouteById(String id) throws LotException;

    Result<String> delRouteById(String id) throws LotException;



}
