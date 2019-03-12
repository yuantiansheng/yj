package com.yj.oa.project.service.progress;

import com.yj.oa.project.po.Progress;

import java.util.List;

public interface IProgressService {

    /**
     * 添加
     */
    int insertSelective(Progress progress);

    /**
     * 批量删除
     */
    int deleteByPrimaryKeys(Integer[] ids);

    /**
     * 修改
     */
    int updateByPrimaryKeySelective(Progress progress);

    /**
     * 列表查询
     */
    List<Progress> selectProgressList(Progress progress);

    /**
     * 主键查询
     * @date
     */
    Progress selectByPrimaryKey(Integer id);

    /**
     * 项目名称查询
     * @date
     */
    Progress selectByName(String name);
}
