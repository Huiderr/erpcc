package com.cwca.worship.manager.dao;

import com.cwca.common.base.BaseJpaRepository;
import com.cwca.worship.manager.entity.LotRoute;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 13:16
 * @description ：**
 */
@Repository
public interface RouteRepository extends BaseJpaRepository<LotRoute,Integer> {

    @Modifying
    @Query(value = "update lot_route set status = ?2 where id = ?1", nativeQuery = true)
    int updateStatus(String id,String status);

    @Modifying
    @Query(value = "select route.* from lot_role_route role_route left join lot_route route on role_route.route_id = route.id where role_route.role_id = ?1", nativeQuery = true)
    List<LotRoute> loadRoleRouteById(String roleId);

}