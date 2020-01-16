package com.cwca.worship.manager.dao;

import com.cwca.worship.manager.entity.DmLotCommon;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author ：wongs.
 * @date ：Created in 2019/11/25 - 11:36
 * @description ：**
 */
@Component
public class DmCommonRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<DmLotCommon> loadDmLotCommonById(String dmTableCode, String id){
        String sql= String.format(" SELECT *,'%s' as tableName FROM %s WHERE ID= %s;", dmTableCode, dmTableCode, id);
        Query query= entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public List<DmLotCommon> loadDmLotCommonList(String tableName){
        String sql= String.format(" SELECT *,'%s' as tableName FROM %s ORDER BY id;", tableName, tableName);
        Query query= entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public void insertDmLotCommon(DmLotCommon dmLotCommon){
        String values = "'"+dmLotCommon.getId()+"','"+dmLotCommon.getCode()+"','"+dmLotCommon.getDescription()+"','"+dmLotCommon.getRemark()+"','"+dmLotCommon.getCreater()+"','"+dmLotCommon.getCreateTime()+"'";
        String sql=" insert into "+dmLotCommon.getTableName()+"((id,code,description,remark,creater,createTime)) values ("+values+");";
        Query query = entityManager.createNativeQuery(sql);
        int rows = query.executeUpdate();
    }

    public void updateDmLotCommon(DmLotCommon dmLotCommon){
        String values = "code='"+dmLotCommon.getCode()+"',description='"+dmLotCommon.getDescription()+"',remark='"+dmLotCommon.getRemark()+"',updater='"+dmLotCommon.getUpdater()+"',updateTime='"+dmLotCommon.getUpdateTime()+"'";
        String sql= String.format(" update %s set %s where id = %s", dmLotCommon.getTableName(), values, dmLotCommon.getId());
        Query query = entityManager.createNativeQuery(sql);
        int rows = query.executeUpdate();
    }

    public void updateStatus(String table, String id, String status){
        String values = "status='"+status+"'";
        String sql= String.format(" update %s set %s where id = %s", table, values, id);
        Query query = entityManager.createNativeQuery(sql);
        int rows = query.executeUpdate();
    }

    public void deleteById(String table, String id){
        String sql= String.format(" delete from %s where id = %s", table, id);
        Query query = entityManager.createNativeQuery(sql);
        int rows = query.executeUpdate();
    }
}
