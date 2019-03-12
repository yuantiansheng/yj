package com.yj.oa.project.service.enterprise;

import com.yj.oa.project.mapper.EnterpriseBaseInfoMapper;
import com.yj.oa.project.po.enterprise.EnterpriseBaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BaseInfoServiceImpl implements IBaseInfoService{

    @Autowired
    EnterpriseBaseInfoMapper mapper;

    @Override
    public int deleteByPrimaryKeys(Integer[] id) {
        return mapper.deleteByPrimaryKeys(id);
    }

    @Override
    public int insertSelective(EnterpriseBaseInfo enterpriseBaseInfo) {
        return mapper.insertSelective(enterpriseBaseInfo);
    }

    @Override
    public EnterpriseBaseInfo selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(EnterpriseBaseInfo enterpriseBaseInfo) {
        return mapper.updateByPrimaryKeySelective(enterpriseBaseInfo);
    }

    @Override
    public List<EnterpriseBaseInfo> selectEnterpriseBaseInfoList(EnterpriseBaseInfo enterpriseBaseInfo) {
        return mapper.selectEnterpriseBaseInfoList(enterpriseBaseInfo);
    }
}
