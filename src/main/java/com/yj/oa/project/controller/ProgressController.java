package com.yj.oa.project.controller;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Progress;
import com.yj.oa.project.po.Project;
import com.yj.oa.project.service.progress.IProgressService;
import com.yj.oa.project.service.project.IProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/progress")
public class ProgressController extends BaseController {

    private String prefix = "system/progress/";

    @Autowired
    private IProgressService service;

    @Autowired
    private IProjectService projectService;

    /**
     * @描述 页面跳转
     * @date 2018/9/16 10:59
     */
    @RequestMapping("/tolist")
    public String tolist(Model model) {
        List<Project> projects = projectService.selectByStatus(1);
        model.addAttribute("projects", projects);
        return prefix + "progress";
    }


    /**
     * @描述 ajax请求
     * @date 2018/9/16 10:48
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<Progress> list(Progress progress) {
        List<Progress> progressList = service.selectProgressList(progress);
        return progressList;
    }

    /**
     * @描述 列表页
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(Progress progress, Model model) {
        //开启分页
        startPage();
        List<Progress> progressList = service.selectProgressList(progress);

        return getDataTable(progressList);
    }


    /**
     * @描述 新增页面
     * @date 2018/9/16 11:37
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("progress:tolist")
    public String toAdd(Model model) {
//        List<Project> projects = projectService.selectProjectList(new Project());
//        List<Project> projectList = new ArrayList<Project>();
//        // 去除未审批通过的项目
//        for (Project project : projects) {
//            if (project.getStatus() > 1) {
//                projectList.add(project);
//            }
//        }
        List<Project> projects = projectService.selectByStatus(1);

        model.addAttribute("projects", projects);
        return prefix + "add";
    }


    /**
     * @描述 批量删除
     * @date 2018/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("progress:del")
    @Operlog(modal = "项目进度管理", descr = "删除项目")
    @ResponseBody
    public AjaxResult del(Integer[] ids) {

        if (ids.length > 1){
            return error("项目进度不支持多选删除");
        }

        Progress progress = service.selectByPrimaryKey(ids[0]);
        if (!DateUtils.isSameDay(progress.getModifyTime(),new Date())){
            return error("项目进度只允许当天修改删除");
        }

        try {
            service.deleteByPrimaryKeys(ids);
        } catch (Exception e) {
            return error(e.getMessage());
        }



        return success();
    }


    /**
     * @描述 执行保存操作
     * @date 2018/9/16 11:54
     */

    @RequestMapping("/addSave")
    @RequiresPermissions("progress:add")
    @Operlog(modal = "项目进度管理", descr = "提交项目新进度")
    @ResponseBody
    public AjaxResult addMeetingRoom(Progress progress) {


        /**
         * 更新项目进度
         */
        Integer i = progress.getProgress();
        // 求当前进度
        Progress par = new Progress();
        par.setName(progress.getName());
        List<Progress> progressList = service.selectProgressList(par);
        Integer max = 0;
        Date modifyTime = null;
        for (Progress pro :
                progressList) {
            if (pro.getProgress().intValue() > max.intValue() ){
                max = pro.getProgress();
                modifyTime = pro.getModifyTime();
            }
        }

        if (modifyTime!=null && DateUtils.isSameDay(modifyTime,new Date())){
            return error("项目进度一天只允许填写一次");
        }

        if (max.intValue() >= i.intValue()){
            return error("项目进度不能小于当前进度");
        }

        Project project = new Project();
        project.setName(progress.getName());
        project.setProgress(i);
        if(i.equals(new Integer(100))){
            project.setStatus(CsEnum.project.PROJECT_STATUS_COMP.getValue());
        }else {
            project.setStatus(CsEnum.project.PROJECT_STATUS_WORK.getValue());
        }
        projectService.updateByName(project);

        progress.setCreateTime(new Date());
        progress.setModifyTime(new Date());
        return result(service.insertSelective(progress));
    }


    /**
     * @描述 编辑修改页面
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("progress:update")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Progress progress = service.selectByPrimaryKey(id);

        model.addAttribute("progress", progress);
        return prefix + "edit";

    }

    /**
     * @描述 修改保存
     * @date 2018/9/16 16:12
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("progress:update")
    @Operlog(modal = "项目进度管理", descr = "修改项目进度")
    @ResponseBody
    public AjaxResult save(Progress progress) {

        if (!DateUtils.isSameDay(progress.getModifyTime(),new Date())){
            return error("项目进度只允许当天修改删除");
        }

        /**
         * 更新项目进度
         */
        Project project = new Project();
        project.setName(progress.getName());
        Integer i = progress.getProgress();
        // 求当前进度
        Progress par = new Progress();
        par.setName(progress.getName());
        List<Progress> progressList = service.selectProgressList(par);
        Integer max = 0;
        for (Progress pro :
                progressList) {
            if (pro.getProgress().intValue() > max.intValue() ){
                max = pro.getProgress();
            }
        }

        if (max.intValue() >= i.intValue()){
            return error("项目进度不能小于当前进度");
        }

        project.setProgress(i);
        if(i.equals(new Integer(100))){
            project.setStatus(CsEnum.project.PROJECT_STATUS_COMP.getValue());
        }else{
            project.setStatus(CsEnum.project.PROJECT_STATUS_WORK.getValue());
        }
        projectService.updateByName(project);

        progress.setModifyTime(new Date());
        return result(service.updateByPrimaryKeySelective(progress));
    }


}
