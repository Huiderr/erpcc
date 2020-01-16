package com.cwca.lot;

import com.cwca.worship.manager.dao.DmTablesRepository;
import com.cwca.worship.manager.dao.DmTradeRepository;
import com.cwca.worship.manager.dao.RouteRepository;
import com.cwca.worship.manager.dao.UserRepository;
import com.cwca.worship.manager.entity.DmTables;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ErpccApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DmTradeRepository dmTradeRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private DmTablesRepository dmTablesRepository;

    @Test
    void testUser() {
/*        LotRoute lotRoute = new LotRoute();
        lotRoute.setPid(1);
        lotRoute.setCode("U_ROUTE");
        lotRoute.setBusiness("权限管理");
        lotRoute.setDescription("权限管理");
        lotRoute.setIcon("#");
        lotRoute.setRouteUrl("/route/");
        lotRoute.setRouteType("manager");
        lotRoute.setCreater("cwcaAdmin");
        routeRepository.save(lotRoute);*/
/*        LotUser lotUser = new LotUser();
        lotUser.setUserName("超级管理员");
        lotUser.setUserCode("cwcaAdmin");
        lotUser.setId(1);
        lotUser.setUserType("manager");
        lotUser.setPassword(new BCryptPasswordEncoder().encode("cwca") );
        userRepository.save(lotUser);*/
/*        DmLotTrade dmLotTrade = new DmLotTrade();
        dmLotTrade.setCode("GAS");
        dmLotTrade.setDescription("加油站");
        dmLotTrade.setRemark("");
        dmLotTrade.setCreater("cwcaAdmin");
        dmTradeRepository.save(dmLotTrade);*/
        DmTables dmTables = new DmTables();
        dmTables.setDmTableCode("DM_LOT_SYSTEM");
        dmTables.setDmTableName("系统代码表");
        dmTablesRepository.save(dmTables);
    }

}
