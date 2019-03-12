package com.yj.oa.project.mapper;


import com.yj.oa.project.po.enterprise.EnterpriseBaseInfo;

import java.util.List;

public interface EnterpriseBaseInfoMapper {

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param enterpriseBaseInfo
     * @return
     */
    int insertSelective(EnterpriseBaseInfo enterpriseBaseInfo);

    /**
     * id查询
     * @param id
     * @return
     */
    EnterpriseBaseInfo selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param enterpriseBaseInfo
     * @return
     */
    int updateByPrimaryKeySelective(EnterpriseBaseInfo enterpriseBaseInfo);

    /**
     * 列表
     * @param enterpriseBaseInfo
     * @return
     */
    List<EnterpriseBaseInfo> selectEnterpriseBaseInfoList(EnterpriseBaseInfo enterpriseBaseInfo);
}
