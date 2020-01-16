package com.cwca.worship.manager.dao;

import com.cwca.common.base.BaseJpaRepository;
import com.cwca.worship.manager.entity.DmTables;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 13:16
 * @description ：**
 */
@Repository
public interface DmTablesRepository extends BaseJpaRepository<DmTables,Integer> {

    @Modifying
    @Query(value = "update dm_tables set status = ?2 where id = ?1", nativeQuery = true)
    int updateStatus(String id,String status);

}