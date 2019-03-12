package com.yj.oa.project.service.equip;

import com.yj.oa.project.po.enterprise.Equip;

import java.util.List;

public interface IEquipService {

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param equip
     * @return
     */
    int insertSelective(Equip equip);

    /**
     * id查询
     * @param id
     * @return
     */
    Equip selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param equip
     * @return
     */
    int updateByPrimaryKeySelective(Equip equip);

    /**
     * 列表
     * @param equip
     * @return
     */
    List<Equip> selectEquipList(Equip equip);
}
