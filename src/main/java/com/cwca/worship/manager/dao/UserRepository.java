package com.cwca.worship.manager.dao;

import com.cwca.common.base.BaseJpaRepository;
import com.cwca.worship.manager.entity.LotUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 13:16
 * @description ：**
 */
@Repository
public interface UserRepository extends BaseJpaRepository<LotUser,Integer> {

    LotUser findByUserCode(String userCode);

    @Modifying
    @Query(value = "update lot_user set status = ?2 where id = ?1", nativeQuery = true)
    int updateStatus(String id,String status);

    @Modifying
    @Query(value = "update lot_user set role_code = ?2 where id = ?1", nativeQuery = true)
    int authoRole(String id,String roleCode);

}