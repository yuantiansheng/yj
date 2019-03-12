package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ApplyProject extends BasePo {

    private String id;

    /** 发起人Id */
    private String proposer_Id;
    /** 实例ID*/
    private String procInstanId;

    /** 代理人ID */
    private String agent_Id;

    /** 申请项目名称 */
    private String projectName;

    /** 发起时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**审批完成结束时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 理由 */
    private String reason;

    /** 备注 */
    private String remark;

    /**回复内容*/
    private String reply;

    /** 状态 */
    private Integer status;


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProcInstanId()
    {
        return procInstanId;
    }

    public void setProcInstanId(String procInstanId)
    {
        this.procInstanId = procInstanId;
    }

    public String getProposer_Id()
    {
        return proposer_Id;
    }

    public void setProposer_Id(String proposer_Id)
    {
        this.proposer_Id = proposer_Id;
    }

    public String getAgent_Id()
    {
        return agent_Id;
    }

    public void setAgent_Id(String agent_Id)
    {
        this.agent_Id = agent_Id;
    }


    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReply()
    {
        return reply;
    }

    public void setReply(String reply)
    {
        this.reply = reply;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
