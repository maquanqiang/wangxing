package com.jebao.erp.service.impl.employee;

import com.jebao.erp.service.inf.employee.IRankServiceInf;
import com.jebao.jebaodb.dao.dao.employee.TbRankDao;
import com.jebao.jebaodb.entity.employee.TbRank;
import com.jebao.jebaodb.entity.extEntity.PageWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jack on 2016/11/21.
 */
@Service
public class RankServiceImpl implements IRankServiceInf {
@Autowired
    private TbRankDao rankDao;
    public List<TbRank> getRankList(PageWhere pageWhere){
        return rankDao.selectList(pageWhere);
    }
}
