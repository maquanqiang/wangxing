package com.jebao.p2p.web.api.controllerApi.employee;

import com.jebao.erp.service.inf.employee.IRankServiceInf;
import com.jebao.jebaodb.entity.employee.TbRank;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import com.jebao.p2p.web.api.responseModel.base.JsonResult;
import com.jebao.p2p.web.api.responseModel.base.JsonResultList;
import com.jebao.p2p.web.api.responseModel.employee.RankVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/11/21.
 */
@RestController
@RequestMapping("api/rank")
public class RankController {
    @Autowired
    private IRankServiceInf rankService;

    @RequestMapping("list")
    public JsonResult list()
    {
        PageWhere pageWhere = new PageWhere(0,10000);
        List<TbRank> rankList = rankService.getRankList(pageWhere);
        List<RankVM> vmList = new ArrayList<>();
        rankList.forEach(o->vmList.add(new RankVM(o)));
        return new JsonResultList<>(vmList);
    }
}
