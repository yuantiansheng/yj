package com.yj.oa.project.service.ACT.applyProject;

import com.yj.oa.project.po.ApplyProject;

import java.util.List;

public interface IActApplyProjectService {

    /**
     * @ 描述 提交申请
     * @date 2018/9/21 16:09
     */
    public void apply(ApplyProject applyProject);


    /**
     *
     * @描述:  删除
     *
     * @params:
     * @return:
     * @date: 2018/9/26 15:26
     */
    int deleteByPrimaryKeys(String[] ids)throws Exception;

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
}
