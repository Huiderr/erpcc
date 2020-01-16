package com.cwca.worship.manager.service.impl;

import com.cwca.common.Constants;
import com.cwca.common.Exception.LotException;
import com.cwca.common.result.Message;
import com.cwca.common.result.Result;
import com.cwca.worship.manager.dao.DmTradeRepository;
import com.cwca.worship.manager.dao.RoleRepository;
import com.cwca.worship.manager.dao.RouteRepository;
import com.cwca.worship.manager.entity.LotRole;
import com.cwca.worship.manager.entity.LotRoute;
import com.cwca.worship.manager.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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
@CacheConfig(cacheNames = {"lotUser"})
public class ManagerServiceImpl implements ManagerService {

    private final RoleRepository roleRepository;
    private final RouteRepository routeRepository;
    private final DmTradeRepository dmTradeRepository;

    public ManagerServiceImpl(RouteRepository routeRepository, DmTradeRepository dmTradeRepository, RoleRepository roleRepository) {
        this.routeRepository = routeRepository;
        this.dmTradeRepository = dmTradeRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Result<String> vaild(String table, String id, String status) throws LotException {
        switch (table){
            case "role":
                roleRepository.updateStatus(id, status);
                break;
            case "route":
                routeRepository.updateStatus(id, status);
                break;
            default:
                break;
        }
        return new Result<>();
    }

    @Override
    public Result<String> saveRoleInfo(LotRole lotRole) throws LotException {
        roleRepository.save(lotRole);
        return new Result<>(Message.getMessage(Message.SUCCESS));
    }

    @Override
    public Result<List<LotRole>> loadRoleList(Map<String, String> map) throws LotException {
        Specification<LotRole> specification = (Specification<LotRole>) (Root<LotRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(map.get("roleType"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("roleType"),map.get("roleType")));
            }
            predicate.getExpressions().add(
                    criteriaBuilder.equal(root.<String>get("status"),Constants.Env.STATUS_ENABLED));
            return predicate;
        };
        return new Result<>(roleRepository.findAll(specification));
    }

    @Override
    public Result<Page<LotRole>> loadRoleListPage(Map<String, String> map) throws LotException {
        int page = Integer.parseInt(map.get("page"));
        int limit = Integer.parseInt(map.get("limit"));
        log.info("pageNo==>{}, pageSize===> {}", page, limit);
        Specification<LotRole> specification = (Specification<LotRole>) (Root<LotRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(map.get("roleType"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("roleType"),map.get("roleType")));
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
        return new Result<>(roleRepository.findAll(specification,pageable));
    }

    @Override
    public Result<LotRole> loadRoleById(String id) throws LotException {
        return new Result<>(roleRepository.findById(Integer.parseInt(id)).get());
    }

    @Override
    public Result<List<Map<Integer,Integer>>> loadRoleRouteById(String roleId) throws LotException {
        return new Result<>(roleRepository.loadRoleRouteById(roleId));
    }

    @Override
    public Result<List<LotRoute>> loadRoleRoutesById(String roleId) throws LotException {
        return new Result<>(routeRepository.loadRoleRouteById(roleId));
    }

    @CacheEvict(allEntries=true)
    @Override
    public Result<String> authorize(String roleId, String routeIds) throws LotException {
        //Step one： delete old
        roleRepository.authoDel(roleId);
        //Step one： insert new
        String[] routeId = routeIds.split(",");
        for (String s : routeId) {
            roleRepository.authoRoute(roleId,s);
        }
        return new Result<>();
    }

    @Override
    public Result<String> saveRouteInfo(LotRoute lotRoute) throws LotException {
        routeRepository.save(lotRoute);
        return new Result<>(Message.getMessage(Message.SUCCESS));
    }

    @Override
    public Result<List<LotRoute>> loadRouteList(Map<String, String> map) {
        Specification<LotRoute> specification = (Specification<LotRoute>) (Root<LotRoute> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(map.get("routeType"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("routeType"),map.get("routeType")));
            }
            if (StringUtils.isNotBlank(map.get("pid"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("pid"),map.get("pid")));
            }
            if (StringUtils.isNotBlank(map.get("status"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("status"),map.get("status")));
            }
            return predicate;
        };
        return new Result<>(routeRepository.findAll(specification));
    }

    @Override
    public Result<Page<LotRoute>> loadRouteListPage(Map<String, String> map) {
        int page = Integer.parseInt(map.get("page"));
        int limit = Integer.parseInt(map.get("limit"));
        log.info("pageNo==>{}, pageSize===> {}", page, limit);
        Specification<LotRoute> specification = (Specification<LotRoute>) (Root<LotRoute> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(map.get("routeType"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("routeType"),map.get("routeType")));
            }
            if (StringUtils.isNotBlank(map.get("pid"))) {
                predicate.getExpressions().add(
                        criteriaBuilder.equal(root.<String>get("pid"),map.get("pid")));
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
        return new Result<>(routeRepository.findAll(specification,pageable));
    }

    @Override
    public Result<LotRoute> loadRouteById(String id) throws LotException {
        return new Result<>(routeRepository.findById(Integer.parseInt(id)).get());
    }

    @Override
    public Result<String> delRouteById(String id) throws LotException {
        return null;
    }

}
