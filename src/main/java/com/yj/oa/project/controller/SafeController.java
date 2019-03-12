package com.yj.oa.project.controller;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Safe;
import com.yj.oa.project.service.enterprise.IBaseInfoService;
import com.yj.oa.project.service.safe.ISafeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/safe")
public class SafeController extends BaseController {
    private final static String prefix = "system/safe";

    @Autowired
    ISafeService safeService;

    /**
     *
     * @描述: 跳转到列表页
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:13s
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("safe:tolist")
    public String toList(Model model)
    {
        return prefix + "/safe";
    }



    /**
     *
     * @描述: 返回表格数据
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:15
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo tableList(Safe safe)
    {
        startPage();
//        note.setCreateBy(getUserId());
        List<Safe> safeList = safeService.selectSafeList(safe);
        return getDataTable(safeList);

    }


    /**
     *
     * @描述: 添加页面
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:15
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model)
    {
        return prefix + "/add";
    }

    /**
     *
     * @描述: 添加保存
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:16
     */
    @RequestMapping("/addSave")
    @RequiresPermissions("safe:add")
    @Operlog(modal = "安全管理",descr = "事故记录录入")
    @ResponseBody
    public AjaxResult addSave(Safe safe) throws Exception
    {
//        note.setCreateBy(getUserId());
        safe.setCreateTime(new Date());
        return result(safeService.insertSelective(safe));
    }

    /**
     *
     * @描述: 删除
     *
     * @params:
     * @return:
     * @date: 2018/9/27 22:02
     */
    @RequestMapping("/del")
    @RequiresPermissions("safe:del")
    @Operlog(modal = "安全管理",descr = "事故记录删除")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        return result(safeService.deleteByPrimaryKeys(ids));
    }


    /**
     *
     * @描述: 编辑页面
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:17
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("safe:update")
    @Operlog(modal = "安全管理",descr = "查看事故记录")
    public String toEdit(@PathVariable("id") Integer id, Model model)
    {
        Safe safe = safeService.selectByPrimaryKey(id);
        model.addAttribute("safe", safe);
        return prefix + "/edit";
    }


    /**
     *
     * @描述: 修改保存
     *
     * @params:
     * @return:
     * @date: 2018/9/27 21:01
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("safe:update")
    @Operlog(modal = "安全管理",descr = "修改事故记录")
    @ResponseBody
    public AjaxResult editSave(Safe safe)
    {
        return result(safeService.updateByPrimaryKeySelective(safe));
    }

}
