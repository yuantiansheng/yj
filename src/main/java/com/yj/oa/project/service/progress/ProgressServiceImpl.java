package com.yj.oa.project.service.progress;

import com.yj.oa.project.mapper.ProgressMapper;
import com.yj.oa.project.po.Progress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProgressServiceImpl implements IProgressService {

    @Autowired
    ProgressMapper mapper;

    @Override
    public int insertSelective(Progress progress) {
        return mapper.insertSelective(progress);
    }

    @Override
    public int deleteByPrimaryKeys(Integer[] ids) {
        return mapper.deleteByPrimaryKeys(ids);
    }

    @Override
    public int updateByPrimaryKeySelective(Progress progress) {
        return mapper.updateByPrimaryKeySelective(progress);
    }

    @Override
    public List<Progress> selectProgressList(Progress progress) {
        return mapper.selectProgressList(progress);
    }

    @Override
    public Progress selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Progress selectByName(String name) {
        return mapper.selectByName(name);
    }
}
