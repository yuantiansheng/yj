package com.yj.oa.project.service.project;

import com.yj.oa.project.po.Project;

import java.util.List;

public interface IProjectService {
    /**
     * 添加
     */
    int insertSelective(Project project);

    /**
     * 批量删除
     */
    int deleteByPrimaryKeys(Integer[] ids);

    /**
     * 修改
     */
    int updateByPrimaryKeySelective(Project project);

    /**
     * 列表查询
     */
    List<Project> selectProjectList(Project project);

    /**
     * 名字唯一性
     * @date
     */
    String checkNameUnique(Project project);

    /**
     * 主键查询
     * @date
     */
    Project selectByPrimaryKey(Integer id);

    /**
     * 项目名称查询
     * @date
     */
    Project selectByName(String name);

    /**
     * 项目状态查询
     * @date
     */
    List<Project> selectByStatus(Integer status);

    /**
     * 根据项目名称修改状态 房间名唯一
     * @date
     */
    int updateByName(Project project);
}
