package com.cwca.worship.manager.dao;

import com.cwca.common.base.BaseJpaRepository;
import com.cwca.worship.manager.entity.LotRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 13:16
 * @description ：**
 */
@Repository
public interface RoleRepository extends BaseJpaRepository<LotRole,Integer> {

    List<LotRole> findByStatus(String status);

    @Modifying
    @Query(value = "update lot_role set status = ?2 where id = ?1", nativeQuery = true)
    int updateStatus(String id,String status);

    @Modifying
    @Query(value = "select * from lot_role_route where role_id = ?1", nativeQuery = true)
    List<Map<Integer,Integer>> loadRoleRouteById(String roleId);

    @Modifying
    @Query(value = "delete from lot_role_route where role_id = ?1", nativeQuery = true)
    int authoDel(String roleId);

    @Modifying
    @Query(value = "insert into lot_role_route values (?1,?2)", nativeQuery = true)
    int authoRoute(String roleId,String routeId);
}