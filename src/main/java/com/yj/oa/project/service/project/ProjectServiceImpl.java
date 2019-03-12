package com.yj.oa.project.service.project;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.ProjectMapper;
import com.yj.oa.project.po.MeetingRoom;
import com.yj.oa.project.po.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    ProjectMapper mapper;

    @Override
    public int insertSelective(Project project) {
        return mapper.insertSelective(project);
    }

    @Override
    public int deleteByPrimaryKeys(Integer[] ids) {
        try {
            return mapper.deleteByPrimaryKeys(ids);
        } catch (Exception e) {
            throw new RuntimeException("操作失败！");
        }
    }

    @Override
    public int updateByPrimaryKeySelective(Project project) {
        return mapper.updateByPrimaryKeySelective(project);
    }

    @Override
    public List<Project> selectProjectList(Project project) {
        return mapper.selectProjectList(project);
    }

    @Override
    public String checkNameUnique(Project project) {
        Integer id = StringUtils.isNull(project.getId()) ? -1 : project.getId();
        Project info = mapper.checkNameUnique(project.getName());
        if (StringUtils.isNotNull(info) && !info.getId().equals(id))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }

    @Override
    public Project selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Project selectByName(String name) {
        return mapper.selectByName(name);
    }

    @Override
    public List<Project> selectByStatus(Integer status) {
        return mapper.selectByStatus(status);
    }

    @Override
    public int updateByName(Project project) {
        return mapper.updateByName(project);
    }
}
