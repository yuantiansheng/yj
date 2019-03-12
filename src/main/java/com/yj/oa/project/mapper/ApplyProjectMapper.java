package com.yj.oa.project.mapper;

import com.yj.oa.project.po.ApplyProject;

import java.util.List;

public interface ApplyProjectMapper {

    /**
     *
     * 批量删除
     * @mbggenerated
     */
    int deleteByprocInstIds(String[] ids);

    /**
     *添加
     * @mbggenerated
     */
    int insertSelective(ApplyProject record);

    /**
     *主键查询
     *
     * @mbggenerated
     */
    ApplyProject selectByPrimaryKey(String id);

    /**
     * 修改状态
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ApplyProject record);


    /**
     * 列表
     * @mbggenerated
     */
    List<ApplyProject> selectApplyProjectList(ApplyProject a);
}
