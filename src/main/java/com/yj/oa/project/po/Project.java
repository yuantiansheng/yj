package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;

/**
 * 项目基本信息
 */
public class Project extends BasePo {

    private Integer id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 施工单位
     */
    private String unit;

    /**
     * 施工单位联系人
     */
    private String contact;

    /**
     * 施工单位联系电话
     */
    private String mobile;

    /**
     * 施工地点
     */
    private String addr;

    /**
     * 投资金额
     */
    private String investment;

    /**
     * 项目状态 0：自由，1：已启动 2：施工中，3：完成
     */
    private Integer status;

    /**
     * 项目创建时间
     */
    private Date createTime;

    /**
     * 项目启动时间
     */
    private Date startTime;

    /**
     * 项目进度
     */
    private Integer progress;

    /**
     * 备注
     */
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getInvestment() {
        return investment;
    }

    public void setInvestment(String investment) {
        this.investment = investment;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
