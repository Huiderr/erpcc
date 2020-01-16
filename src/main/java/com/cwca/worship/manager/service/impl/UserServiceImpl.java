package com.cwca.worship.manager.service.impl;

import com.cwca.common.Exception.LotException;
import com.cwca.common.result.Result;
import com.cwca.common.utils.ConfigUtils;
import com.cwca.worship.manager.dao.DmTradeRepository;
import com.cwca.worship.manager.dao.RouteRepository;
import com.cwca.worship.manager.dao.UserRepository;
import com.cwca.worship.manager.entity.DmLotTrade;
import com.cwca.worship.manager.entity.LotUser;
import com.cwca.worship.manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.Map;

/**
 * @author ：wongs.
 * @date ：Created in 2019/10/31 - 11:11
 * @description ：**
 */
@Transactional(rollbackFor=Exception.class)
@Service
@Slf4j
@CacheConfig(cacheNames = {"lotUser"})
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DmTradeRepository dmTradeRepository;
    private final RouteRepository routeRepository;

    public UserServiceImpl(UserRepository userRepository, DmTradeRepository dmTradeRepository, RouteRepository routeRepository) {
        this.userRepository = userRepository;
        this.dmTradeRepository = dmTradeRepository;
        this.routeRepository = routeRepository;
    }

    @Cacheable(key = "#username")
    @Override
    public Result<LotUser> loadUserByUserCode(String username) {
        LotUser lotUser = userRepository.findByUserCode(username);
        return new Result<>(lotUser);
    }

    @CachePut(key = "#result.data.userCode")
    @Override
    public Result<LotUser> saveUserInfo(LotUser lotUser) {
        if(lotUser.getDmLotTrade() != null){
            DmLotTrade dmLotTrade = lotUser.getDmLotTrade();
            lotUser.setDmLotTrade(dmTradeRepository.findByCode(dmLotTrade.getCode()));
        }
        userRepository.save(lotUser);
        return new Result<>(lotUser);
    }

    @Override
    public Result<Page<LotUser>> loadUserlistPage(Map<String, String> map) {
        int page = Integer.parseInt(map.get("page"));
        int limit = Integer.parseInt(map.get("limit"));
        log.info("pageNo==>{}, pageSize===> {}", page, limit);
        Specification<LotUser> specification = (Specification<LotUser>) (Root<LotUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate.getExpressions().add(
                    criteriaBuilder.notEqual(root.<String>get("userCode"),ConfigUtils.getValue("super_user")));
            if (StringUtils.isNotBlank(map.get("userCode"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("userCode"),map.get("userCode")));
            }
            if (StringUtils.isNotBlank(map.get("userName"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.like(root.<String>get("userName"),"%" + map.get("userName") + "%"));
            }
            if (StringUtils.isNotBlank(map.get("userType"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("userType"),map.get("userType")));
            }
            if (StringUtils.isNotBlank(map.get("tradeCode"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<DmLotTrade>get("dmLotTrade").get("code"),map.get("tradeCode")));
            }
            if (StringUtils.isNotBlank(map.get("createTime"))) {
                String[] date = map.get("createTime").split("到");
                predicate.getExpressions().add(
                        criteriaBuilder.between(root.<String>get("createTime"), date[0], date[1])
                );
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
        Pageable pageable = PageRequest.of(page - 1, limit, "desc".equals(order) ?Sort.Direction.DESC:Sort.Direction.ASC,sort);
        return new Result<>(userRepository.findAll(specification, pageable));
    }

    @Override
    public Result<String> vaild(String id, String status) throws LotException {
        return new Result<>(userRepository.updateStatus(id, status)+"");
    }

    @Override
    public Result<LotUser> loadUserByUserId(String id) throws LotException {
        return new Result<>(userRepository.findById(Integer.parseInt(id)).get());
    }

    @CacheEvict(allEntries=true)
    @Override
    public Result<String> authorize(String userIds, String roleCode) throws LotException {
        String[] userId = userIds.split(",");
        for (String s : userId) {
            userRepository.authoRole(s,roleCode);
        }
        return new Result<>();
    }

}
