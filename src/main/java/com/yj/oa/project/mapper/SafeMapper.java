package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Safe;

import java.util.List;

public interface SafeMapper {

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param safe
     * @return
     */
    int insertSelective(Safe safe);

    /**
     * id查询
     * @param id
     * @return
     */
    Safe selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param safe
     * @return
     */
    int updateByPrimaryKeySelective(Safe safe);

    /**
     * 列表
     * @param safe
     * @return
     */
    List<Safe> selectSafeList(Safe safe);

}
