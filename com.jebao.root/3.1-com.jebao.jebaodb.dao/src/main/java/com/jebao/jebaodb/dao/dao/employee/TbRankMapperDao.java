package com.jebao.jebaodb.dao.dao.employee;

import com.jebao.jebaodb.dao.mapper.employee.TbRankMapper;
import com.jebao.jebaodb.entity.employee.TbRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TbRankMapperDao {
    @Autowired
    private TbRankMapper mapper;

    public int insert(TbRank record){return mapper.insert(record);}

    public TbRank selectByPrimaryKey(Integer rankId){return mapper.selectByPrimaryKey(rankId);}

    public int updateByPrimaryKeySelective(TbRank record){return mapper.updateByPrimaryKeySelective(record);}

    public int updateByPrimaryKey(TbRank record){return mapper.updateByPrimaryKey(record);}
}