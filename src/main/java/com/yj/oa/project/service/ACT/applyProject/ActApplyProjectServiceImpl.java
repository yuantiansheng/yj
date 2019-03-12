package com.yj.oa.project.service.ACT.applyProject;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.project.mapper.ActHiProcinstMapper;
import com.yj.oa.project.mapper.ActHiTaskInstMapper;
import com.yj.oa.project.mapper.ApplyProjectMapper;
import com.yj.oa.project.mapper.ProjectMapper;
import com.yj.oa.project.po.ActHiProcinst;
import com.yj.oa.project.po.ApplyProject;
import com.yj.oa.project.po.Project;
import com.yj.oa.project.service.ACT.actUtil.ActUtil;
import com.yj.oa.project.service.ACT.hiActInst.IActHiActInstService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ActApplyProjectServiceImpl implements IActApplyProjectService {

    //申请工作流的进程ID
    private final static String ProcessInstanceByKey = "apply_ID";


    /**
     * 开启任务相关的Service对象
     */
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;


    @Autowired
    ActHiProcinstMapper actHiProcinstMapper;


    @Autowired
    IActHiActInstService iActHiActInstService;

    @Autowired
    ActHiTaskInstMapper actHiTaskInstMapper;


    @Autowired
    ApplyProjectMapper applyProjectMapper;


    @Autowired
    IdentityService identityService;


    @Autowired
    ProjectMapper projectMapper;

    /**
     *
     * @param applyProject:项目申请信息 启动申请流程
     */
    @Override
    public void apply(ApplyProject applyProject) {
        //设置任务发起人 （通过此id获取发起人的流程实例记录,统计申请记录次数）
        String uId = applyProject.getProposer_Id();
        String proceId="";
        String taskId="";
        identityService.setAuthenticatedUserId(uId);


        //提交申请，完成任务。并且指定下一个任务代理人 传递变量参数到下一个节点
        Map<String, Object> map = new HashMap<>(2);
        map.put(CsEnum.activiti.AGENT.getValue(), uId);
        map.put(CsEnum.activiti.INITIATOR.getValue(), uId);

        // 预约表单信息持久化 表单的id 为 任务的formKey编号
        applyProjectMapper.insertSelective(applyProject);

        //设置流程实例的FormKey 和表单的 id关联，之后用来查看 历史记录，资源文件。。。
        String businessKey = CsEnum.activiti.BUSINESS_KEY_APPLYPROJECT.getValue() + "" + applyProject.getId();
        //开启申请流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessInstanceByKey,
                businessKey, map);
        //Service中如果直接调用调用并非调用的是代理类中的方法，不会被切进去，事务就不会生效。
        // 必须要调用代理类才会被切进去 调用带来类需要aop配置：
        // <aop:aspectj-autoproxy proxy-target-class="true" expose-proxy="true"/>
//        ((ActApplyRoomFormServiceImpl) AopContext.currentProxy()).apply(applyRoom, processInstance.getId());



        //通过实例Id获取任务Id
        proceId=processInstance.getId();
        Task task = taskService.createTaskQuery().processInstanceId(proceId).singleResult();
        taskId = task.getId();

        //提交任务
        taskService.complete(taskId, ActUtil.setNextTaskVariable(applyProject.getAgent_Id(),applyProject.getId()));


        //修改房间状态为 预约中
        Project project = new Project();
        project.setStatus(CsEnum.project.PROJECT_STATUS_APPLYING.getValue());
        project.setName(applyProject.getProjectName());
        projectMapper.updateByName(project);

        //添加实例Id
        applyProject.setProcInstanId(proceId);
        applyProjectMapper.updateByPrimaryKeySelective(applyProject);
    }

    @Override
    public int deleteByPrimaryKeys(String[] ids) throws Exception {
        //判断当前任务是否在执行中
        for (String id:ids)
        {
            ActHiProcinst actHiProcinst = actHiProcinstMapper.selectByPrimaryKey(id);
            if (actHiProcinst.getEndTime()==null)
            {
                throw new Exception("无法删除，待审批任务！");
            }

        }
        //1.删除活动历史表 act_hi_actinst 数据
        iActHiActInstService.deleteByProcInstId(ids);

        //2.删除历史任务表 act_hi_taskinst 数据
        actHiTaskInstMapper.deleteByprocInstIds(ids);

        //3.删除历史进程表 act_hi_proceInst 数据
        int i =  actHiProcinstMapper.deleteByPrimaryKeys(ids);

        //4.删除表单
        applyProjectMapper.deleteByprocInstIds(ids);

        return i;
    }

    @Override
    public ApplyProject selectByPrimaryKey(String id) {
        return applyProjectMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ApplyProject record) {
        return applyProjectMapper.updateByPrimaryKeySelective(record);
    }
}
