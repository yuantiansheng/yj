package com.yj.oa.project.controller.act;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.*;
import com.yj.oa.project.service.ACT.actUtil.ActUtil;
import com.yj.oa.project.service.ACT.applyProject.IActApplyProjectService;
import com.yj.oa.project.service.ACT.applyRoom.IActApplyRoomFormService;
import com.yj.oa.project.service.ACT.hiprocInst.IActHiProcinstService;
import com.yj.oa.project.service.project.IProjectService;
import com.yj.oa.project.service.role.IRoleService;
import com.yj.oa.project.service.user.IUserService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yj.oa.framework.web.po.AjaxResult.error;

@Controller
@RequestMapping(value = "applyProject")
public class ActApplyProjectController extends BaseController {

    private String prefix = "system/applyProjectForm/";

    @Autowired
    IUserService iUserService;

    @Autowired
    IActApplyProjectService applyProjectService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    IProjectService projectService;

    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IActHiProcinstService iActHiProcinstService;


    /**
     *
     * @描述:跳转到列表页面
     *
     * @params:
     * @return：
     * @date： 2018/9/23 12:52
     */
    @RequestMapping("/tolist")
    public String tolist()
    {
        return prefix + "applyProject";
    }


    /**
     *
     * @描述 表单填写页面
     *
     * @date 2018/9/22 14:43
     */
    @RequestMapping("/toAdd/{projectName}")
    public String toAddapplyRoomForm(@PathVariable("projectName") String projectName, Model model)
    {
        Permission permission = new Permission();
        permission.setPerName("流程审批");
        List<Role> roles = roleService.selectPerRoleList(permission);
        List<User> users = new ArrayList<User>();
        for (Role role : roles) {
            //返回用户列表 选择审批人
            User user = new User();
            user.setRole_ID(role.getRoleId());
            List<User> userList = iUserService.selectByUser(user);
            for (User u:
                    userList) {
                users.add(u);
            }
        }

        model.addAttribute("projectName", projectName);
        model.addAttribute("users", users);

        return "system/project/addApplyForm";
    }

    /**
     *
     * @描述: 修改保存项目申请表单内容
     *
     * @params:
     * @return：
     * @date： 2018/9/23 13:28
     */
    @RequestMapping("/editSave")
    @Operlog(modal = "项目管理",descr = "修改项目申请表单")
    @ResponseBody
    public AjaxResult editSave(ApplyProject applyProject)
    {
        //判断当前流程审批是否完成 完成：不允许修改
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(
                applyProject.getProcInstanId()).singleResult();
        Date endTime = historicProcessInstance.getEndTime();
        if (endTime != null)
        {
            return error("该申请已审批！");
        }
        applyProjectService.updateByPrimaryKeySelective(applyProject);
        return success();
    }

    /**
     *
     * @描述: 个人历史 项目表单申请记录
     *
     * @params: actHiProcinst:历史实体
     * @return：TableDataInfo：分页表格实体
     * @date： 2018/9/23 12:36
     */
    @RequestMapping("/TableMyApplyProjectMyHiList")
    @ResponseBody
    public TableDataInfo myApplyHi(ActHiProcinst actHiProcinst)
    {
        startPage();
        //getUserId()
        actHiProcinst.setStartActId(getUserId());
        actHiProcinst.setBusinessKey(CsEnum.activiti.BUSINESS_KEY_APPLYPROJECT.getValue());
        List<ActHiProcinst> actHiProcinsts = iActHiProcinstService.selectActHiProcinstList(actHiProcinst);
        return getDataTable(actHiProcinsts);
    }



    /**
     *
     * @描述: 查看项目申请表单内容
     *
     * @params:
     * @return：
     * @date： 2018/9/23 13:28
     */
    @RequestMapping("/edit/{procInstId}")
    public String edit(@PathVariable("procInstId") String procInstId, Model model)
    {
        //通过实例Id获取任务Id 拿到任务中的表单key 查询出表单内容
        Task task = taskService.createTaskQuery().processInstanceId(procInstId).singleResult();
        String formKey = "";
        ApplyProject applyProject = new ApplyProject();

        if (task == null)
        {
            //任务已完成,在历史记录 获取表单Id 查看表单信息；
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(
                    procInstId).singleResult();
            String businessKey = historicProcessInstance.getBusinessKey();
            formKey = ActUtil.getFormKeyFromHi(businessKey);

        }
        else
        {
            formKey = task.getFormKey();
        }
        applyProject = applyProjectService.selectByPrimaryKey(formKey);
        model.addAttribute("applyProject", applyProject);
        return prefix + "editApplyProjectForm";
    }








    /**
     *
     * @描述 提交预约申请
     *
     * @date 2018/9/22 14:41
     */
    @RequestMapping("/submitApply")
    @Operlog(modal = "项目管理",descr = "提交项目申请表单")
    @ResponseBody
    public AjaxResult submitApply(ApplyProject applyProject)
    {

        //判断项目状况
        Project project = projectService.selectByName(applyProject.getProjectName());
        Integer status = project.getStatus();
        if (status == CsEnum.project.PROJECT_STATUS_FREE.getValue())
        {
            //设置申请人ID  getUserId()
            applyProject.setProposer_Id(getUserId());
            applyProject.setCreateTime(new Date());
            applyProjectService.apply(applyProject);
            return success();
        }
        else if (status == CsEnum.project.PROJECT_STATUS_APPLYING.getValue())
        {
            return error(" 项目已经启动！");
        }
        else if (status == CsEnum.project.PROJECT_STATUS_WORK.getValue())
        {
            return error(" 项目施工中！");
        }
        else
        {
            return error(" 项目已完成！");
        }
    }






    /**
     *
     * @描述: 从历史 实例进程表act_hi_proceinst 里面 通过进程实例ID删除请假记录
     *
     * @params:
     * @return:
     * @date: 2018/9/26 11:14
     */
    @RequestMapping("/del")
    @Operlog(modal = "项目管理",descr = "删除项目申请记录")
    @ResponseBody
    public AjaxResult del(String[] ids)
    {
        int i = 0;
        try
        {
            i = applyProjectService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return i>0 ?success():error();
    }
}
