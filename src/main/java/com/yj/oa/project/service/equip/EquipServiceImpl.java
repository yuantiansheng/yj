package com.yj.oa.project.service.equip;

import com.yj.oa.project.mapper.EquipMapper;
import com.yj.oa.project.po.enterprise.Equip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EquipServiceImpl implements IEquipService{

    @Autowired
    EquipMapper mapper;


    @Override
    public int deleteByPrimaryKeys(Integer[] id) {
        return mapper.deleteByPrimaryKeys(id);
    }

    @Override
    public int insertSelective(Equip equip) {
        return mapper.insertSelective(equip);
    }

    @Override
    public Equip selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Equip equip) {
        return mapper.updateByPrimaryKeySelective(equip);
    }

    @Override
    public List<Equip> selectEquipList(Equip equip) {
        return mapper.selectEquipList(equip);
    }
}
