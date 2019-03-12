package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;

/**
 * 安全
 */
public class Safe extends BasePo {

    private Integer id;

    /**
     * 事故名称
     */
    private String name;

    /**
     * 事故级别
     */
    private Integer level;

    /**
     * 事故类型
     */
    private Integer type;

    /**
     * 事故发生日期
     */
    private Date indate;

    /**
     * 事故发生地点
     */
    private String addr;

    /**
     * 事故发生过程
     */
    private String course;

    /**
     * 事故发生原因
     */
    private String reason;

    /**
     * 事故录入时间
     */
    private Date createTime;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
