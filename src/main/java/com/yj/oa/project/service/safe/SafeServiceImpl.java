package com.yj.oa.project.service.safe;

import com.yj.oa.project.mapper.SafeMapper;
import com.yj.oa.project.po.Safe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SafeServiceImpl implements ISafeService {

    @Autowired
    SafeMapper mapper;

    @Override
    public int deleteByPrimaryKeys(Integer[] id) {
        return mapper.deleteByPrimaryKeys(id);
    }

    @Override
    public int insertSelective(Safe safe) {
        return mapper.insertSelective(safe);
    }

    @Override
    public Safe selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Safe safe) {
        return mapper.updateByPrimaryKeySelective(safe);
    }

    @Override
    public List<Safe> selectSafeList(Safe safe) {
        return mapper.selectSafeList(safe);
    }
}
