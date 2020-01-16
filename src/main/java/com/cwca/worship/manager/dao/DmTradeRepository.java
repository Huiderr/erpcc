package com.cwca.worship.manager.dao;

import com.cwca.common.base.BaseJpaRepository;
import com.cwca.worship.manager.entity.DmLotTrade;
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
public interface DmTradeRepository extends BaseJpaRepository<DmLotTrade,Integer> {

    List<DmLotTrade> findByStatus(String status);

    DmLotTrade findByCode(String code);

    @Modifying
    @Query(value = "update dm_lot_trade set status = ?2 where id = ?1", nativeQuery = true)
    int updateStatus(String id,String status);
}