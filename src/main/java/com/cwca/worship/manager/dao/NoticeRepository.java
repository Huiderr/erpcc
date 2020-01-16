package com.cwca.worship.manager.dao;

import com.cwca.common.base.BaseJpaRepository;
import com.cwca.worship.manager.entity.LotNotice;
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
public interface NoticeRepository extends BaseJpaRepository<LotNotice,Integer> {

    List<LotNotice> findByStatus(String status);

    @Modifying
    @Query(value = "update lot_notice set status = ?2 where id = ?1", nativeQuery = true)
    int updateStatus(String id, String status);
}