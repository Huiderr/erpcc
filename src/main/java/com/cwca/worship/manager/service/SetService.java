package com.cwca.worship.manager.service;

import com.cwca.common.Exception.LotException;
import com.cwca.common.result.Result;
import com.cwca.worship.manager.entity.DmLotCommon;
import com.cwca.worship.manager.entity.DmLotTrade;
import com.cwca.worship.manager.entity.DmTables;
import com.cwca.worship.manager.entity.LotNotice;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 11:08
 * @description ：系统设置
 */
public interface SetService {

    Result<LotNotice> loadNoticeById(String id) throws LotException;

    Result<String> vaild(String table, String id, String status) throws LotException;

    Result<String> saveNoticeInfo(LotNotice lotNotice) throws LotException;

    Result<List<LotNotice>> loadNoticeList(Map<String, String> map) throws LotException;

    Result<Page<LotNotice>> loadNoticeListPage(Map<String, String> map) throws LotException;

    Result<String> saveDmLotTradeInfo(DmLotTrade dmLotTrade) throws LotException;

    Result<List<DmLotTrade>> loadDmLotTradeList() throws LotException;

    Result<Page<DmLotTrade>> loadDmLotTradeListPage(Map<String, String> map) throws LotException;

    Result<DmLotTrade> loadDmLotTradeById(String id) throws LotException;

    Result<Page<DmTables>> loadDmTablesListPage(Map<String, String> map) throws LotException;

    Result<List<DmLotCommon>> loadDmLotCommonList(String dmTableCode) throws LotException;

    Result<DmLotCommon> loadDmLotCommonById(String dmTableCode,String id) throws LotException;

    Result<String> saveDmLotCommon(DmLotCommon dmLotCommon) throws LotException;

    Result<String> delDmLotCommon(String tableName,String id) throws LotException;

    Result<String> dmVaild(String table,String id,String status);

    Result<String> dmDelete(String table,String id);
}
