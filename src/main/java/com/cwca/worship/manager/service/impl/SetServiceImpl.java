package com.cwca.worship.manager.service.impl;

import com.cwca.common.Constants;
import com.cwca.common.Exception.LotException;
import com.cwca.common.result.Message;
import com.cwca.common.result.Result;
import com.cwca.common.utils.UUIDUtils;
import com.cwca.worship.manager.dao.DmCommonRepository;
import com.cwca.worship.manager.dao.DmTablesRepository;
import com.cwca.worship.manager.dao.DmTradeRepository;
import com.cwca.worship.manager.dao.NoticeRepository;
import com.cwca.worship.manager.entity.DmLotCommon;
import com.cwca.worship.manager.entity.DmLotTrade;
import com.cwca.worship.manager.entity.DmTables;
import com.cwca.worship.manager.entity.LotNotice;
import com.cwca.worship.manager.service.SetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 11:11
 * @description ：**
 */
@Transactional(rollbackFor=Exception.class)
@Service
@Slf4j
public class SetServiceImpl implements SetService {

    private final NoticeRepository noticeRepository;
    private final DmTradeRepository dmTradeRepository;
    private final DmTablesRepository dmTablesRepository;
    private final DmCommonRepository dmCommonRepository;

    public SetServiceImpl(NoticeRepository noticeRepository, DmTradeRepository dmTradeRepository, DmTablesRepository dmTablesRepository, DmCommonRepository dmCommonRepository) {
        this.noticeRepository = noticeRepository;
        this.dmTradeRepository = dmTradeRepository;
        this.dmTablesRepository = dmTablesRepository;
        this.dmCommonRepository = dmCommonRepository;
    }

    @Override
    public Result<String> vaild(String table, String id, String status) throws LotException {
        switch (table){
            case "notice":
                noticeRepository.updateStatus(id, status);
                break;
            case "trade":
                dmTradeRepository.updateStatus(id, status);
                break;
            case "dm":
                dmTablesRepository.updateStatus(id, status);
                break;
            default:
                break;
        }
        return new Result<>();
    }

    @Override
    public Result<LotNotice> loadNoticeById(String id) throws LotException {
        return new Result<>(noticeRepository.findById(Integer.parseInt(id)).get());
    }

    @Override
    public Result<String> saveNoticeInfo(LotNotice lotNotice) throws LotException {
        noticeRepository.save(lotNotice);
        return new Result<>(Message.getMessage(Message.SUCCESS));
    }

    @Override
    public Result<List<LotNotice>> loadNoticeList(Map<String, String> map) throws LotException {
        Specification<LotNotice> specification = (Specification<LotNotice>) (Root<LotNotice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate.getExpressions().add(
                    criteriaBuilder.equal(root.<String>get("status"), Constants.Env.STATUS_ENABLED));
            if (StringUtils.isNotBlank(map.get("vaildTime"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("start"),map.get("vaildTime")));
                predicate.getExpressions().add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("end"),map.get("vaildTime")));
            }
            if (StringUtils.isNotBlank(map.get("notice"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("notice"),map.get("notice")));
            }
            if (StringUtils.isNotBlank(map.get("trade"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("trade"),map.get("trade")));
            }
            return predicate;
        };
        Sort sort = Sort.by(Sort.Order.asc("notice"),Sort.Order.desc("start"));
        return new Result<>(noticeRepository.findAll(specification,sort));
    }

    @Override
    public Result<Page<LotNotice>> loadNoticeListPage(Map<String, String> map) throws LotException {
        int page = Integer.parseInt(map.get("page"));
        int limit = Integer.parseInt(map.get("limit"));
        log.info("pageNo==>{}, pageSize===> {}", page, limit);
        Specification<LotNotice> specification = (Specification<LotNotice>) (Root<LotNotice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(map.get("trade"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("trade"),map.get("trade")));
            }
            if (StringUtils.isNotBlank(map.get("notice"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("notice"),map.get("notice")));
            }
            if (StringUtils.isNotBlank(map.get("status"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("status"),map.get("status")));
            }
            if (StringUtils.isNotBlank(map.get("createTime"))) {
                String[] createTime = map.get("createTime").split("到");
                predicate.getExpressions().add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"),createTime[0].trim()));
                predicate.getExpressions().add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("createTime"),createTime[1].trim()));
            }
            if (StringUtils.isNotBlank(map.get("vaildTime"))) {
                String[] vaildTime = map.get("vaildTime").split("到");
                predicate.getExpressions().add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("start"),vaildTime[0].trim()));
                predicate.getExpressions().add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("end"),vaildTime[1].trim()));
            }
            return predicate;
        };
        String sort = "createTime";
        String order = "desc";
        if (StringUtils.isNotBlank(map.get("field"))) {
            sort= map.get("field");
        }
        if (StringUtils.isNotBlank(map.get("order"))) {
            order= map.get("order");
        }
        Pageable pageable = PageRequest.of(page - 1, limit, "desc".equals(order) ? Sort.Direction.DESC:Sort.Direction.ASC,sort);
        return new Result<>(noticeRepository.findAll(specification,pageable));
    }

    @Override
    public Result<String> saveDmLotTradeInfo(DmLotTrade dmLotTrade) throws LotException {
        dmTradeRepository.save(dmLotTrade);
        return new Result<>(Message.getMessage(Message.SUCCESS));
    }

    @Override
    public Result<List<DmLotTrade>> loadDmLotTradeList() {
        return new Result<>(dmTradeRepository.findByStatus(Constants.Env.STATUS_ENABLED));
    }

    @Override
    public Result<Page<DmLotTrade>> loadDmLotTradeListPage(Map<String, String> map) {
        int page = Integer.parseInt(map.get("page"));
        int limit = Integer.parseInt(map.get("limit"));
        log.info("pageNo==>{}, pageSize===> {}", page, limit);
        Specification<DmLotTrade> specification = (Specification<DmLotTrade>) (Root<DmLotTrade> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(map.get("code"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("code"),map.get("code")));
            }
            if (StringUtils.isNotBlank(map.get("description"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("description"),map.get("description")));
            }
            if (StringUtils.isNotBlank(map.get("status"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("status"),map.get("status")));
            }
            return predicate;
        };
        String sort = "createTime";
        String order = "desc";
        if (StringUtils.isNotBlank(map.get("field"))) {
            sort= map.get("field");
        }
        if (StringUtils.isNotBlank(map.get("order"))) {
            order= map.get("order");
        }
        Pageable pageable = PageRequest.of(page - 1, limit, "desc".equals(order) ? Sort.Direction.DESC:Sort.Direction.ASC,sort);
        return new Result<>(dmTradeRepository.findAll(specification,pageable));
    }

    @Override
    public Result<DmLotTrade> loadDmLotTradeById(String id) throws LotException {
        return null;
    }

    @Override
    public Result<Page<DmTables>> loadDmTablesListPage(Map<String, String> map) throws LotException {
        int page = Integer.parseInt(map.get("page"));
        int limit = Integer.parseInt(map.get("limit"));
        log.info("pageNo==>{}, pageSize===> {}", page, limit);
        Specification<DmTables> specification = (Specification<DmTables>) (Root<DmTables> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(map.get("dmTableCode"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("dmTableCode"),map.get("dmTableCode")));
            }
            if (StringUtils.isNotBlank(map.get("dmTableName"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("dmTableName"),map.get("dmTableName")));
            }
            if (StringUtils.isNotBlank(map.get("status"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("status"),map.get("status")));
            }
            return predicate;
        };
        String sort = "createTime";
        String order = "desc";
        if (StringUtils.isNotBlank(map.get("field"))) {
            sort= map.get("field");
        }
        if (StringUtils.isNotBlank(map.get("order"))) {
            order= map.get("order");
        }
        Pageable pageable = PageRequest.of(page - 1, limit, "desc".equals(order) ? Sort.Direction.DESC:Sort.Direction.ASC,sort);
        return new Result<>(dmTablesRepository.findAll(specification,pageable));
    }

    @Override
    public Result<List<DmLotCommon>> loadDmLotCommonList(String dmTableCode) throws LotException {
        return new Result<>(dmCommonRepository.loadDmLotCommonList(dmTableCode));
    }

    @Override
    public Result<DmLotCommon> loadDmLotCommonById(String dmTableCode,String id) throws LotException {
        return new Result<>(dmCommonRepository.loadDmLotCommonById(dmTableCode,id).get(0));
    }

    @Override
    public Result<String> saveDmLotCommon(DmLotCommon dmLotCommon) throws LotException {
        if(null == dmLotCommon.getId()){
            dmLotCommon.setId(UUIDUtils.getNumID());
            dmCommonRepository.insertDmLotCommon(dmLotCommon);
        }else {
            dmCommonRepository.updateDmLotCommon(dmLotCommon);
        }
        return new Result<>(Message.getMessage(Message.SUCCESS));
    }

    @Override
    public Result<String> delDmLotCommon(String tableName, String id) throws LotException {
        return null;
    }

    @Override
    public Result<String> dmVaild(String table, String id, String status) {
        dmCommonRepository.updateStatus(table,id,status);
        return new Result<>(Message.getMessage(Message.SUCCESS));
    }

    @Override
    public Result<String> dmDelete(String table, String id) {
        dmCommonRepository.deleteById(table,id);
        return new Result<>(Message.getMessage(Message.SUCCESS));
    }
}
