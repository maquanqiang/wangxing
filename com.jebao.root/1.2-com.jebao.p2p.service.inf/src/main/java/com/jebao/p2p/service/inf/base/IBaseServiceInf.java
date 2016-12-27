package com.jebao.p2p.service.inf.base;

import com.jebao.jebaodb.entity.base.TbBaseBankInfo;
import com.jebao.jebaodb.entity.base.TbBaseRegionInfo;

import java.util.List;

/**
 * Created by Jack on 2016/12/26.
 */
public interface IBaseServiceInf {
    /**
     * 获取银行列表
     * @return
     */
    List<TbBaseBankInfo> getBankList();
    /**
     * 获取省市区列表
     * @return
     */
    List<TbBaseRegionInfo> getRegionList();

}
