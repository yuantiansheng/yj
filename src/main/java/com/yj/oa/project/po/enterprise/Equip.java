package com.yj.oa.project.po.enterprise;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;

/**
 * 设备卡片
 */
public class Equip extends BasePo {

    private String id;

    /**
     * 所属企业
     */
    private String code;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 描述
     */
    private String description;

    /**
     * 置办时间
     */
    private Date createTime;

    /**
     * 置办人
     */
    private String person;

    /**
     * 备注
     */
    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
