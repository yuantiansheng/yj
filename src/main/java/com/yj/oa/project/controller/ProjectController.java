package com.yj.oa.project.controller;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Project;
import com.yj.oa.project.service.project.IProjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private String prefix = "system/project/";

    @Autowired
    private IProjectService projectService;

    /**
     * @描述 页面跳转
     * @date 2018/9/16 10:59
     */
    @RequestMapping("/tolist")
    public String tolist() {
        return prefix + "project";
    }


    /**
     * @描述 ajax请求
     * @date 2018/9/16 10:48
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<Project> list(Project project) {
        List<Project> projectList = projectService.selectProjectList(project);
        return projectList;
    }

    /**
     * @描述 列表页
     * @date 2018/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(Project project) {
        //开启分页
        startPage();
        List<Project> projectList = projectService.selectProjectList(project);
        return getDataTable(projectList);
    }


    /**
     * @描述 新增页面
     * @date 2018/9/16 11:37
     */
    @RequestMapping("/toAdd")
    @RequiresPermissions("project:tolist")
    public String toAdd() {
        return prefix + "add";
    }


    /**
     * @描述 批量删除
     * @date 2018/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("project:del")
    @Operlog(modal = "项目管理", descr = "删除项目")
    @ResponseBody
    public AjaxResult del(Integer[] ids) {
        for (int id : ids) {

            Project project = projectService.selectByPrimaryKey(id);
            if (project.getStatus() > 0) {
                return error("项目已提交无法删除!");
            }
        }
        try {
            projectService.deleteByPrimaryKeys(ids);
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
    @RequiresPermissions("project:add")
    @Operlog(modal = "项目管理", descr = "创建新项目")
    @ResponseBody
    public AjaxResult addMeetingRoom(Project project) {
        project.setCreateTime(new Date());
        /**
         * 新增项目默认状态为0 自由态
         */
        project.setStatus(0);
        return result(projectService.insertSelective(project));
    }


    /**
     * @描述 编辑修改页面
     * @date 2018/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("project:update")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Project project = projectService.selectByPrimaryKey(id);
        model.addAttribute("project", project);
        return prefix + "edit";

    }

    /**
     * @描述 修改保存
     * @date 2018/9/16 16:12
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("project:update")
    @Operlog(modal = "项目管理", descr = "修改项目")
    @ResponseBody
    public AjaxResult save(Project project) {

        if (project.getStatus() > 0) {
            return error("项目已提交无法修改!");
        }

        return result(projectService.updateByPrimaryKeySelective(project));
    }


    /**
     * 校验部门名称
     */
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public String checkMeetingRoomNameUnique(Project project) {
        String uniqueFlag = "0";
        if (project != null) {
            uniqueFlag = projectService.checkNameUnique(project);
        }
        return uniqueFlag;
    }


}
