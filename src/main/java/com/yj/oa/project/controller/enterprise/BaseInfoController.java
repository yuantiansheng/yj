package com.yj.oa.project.controller.enterprise;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Note;
import com.yj.oa.project.po.enterprise.EnterpriseBaseInfo;
import com.yj.oa.project.po.enterprise.Equip;
import com.yj.oa.project.service.enterprise.IBaseInfoService;
import com.yj.oa.project.service.equip.IEquipService;
import com.yj.oa.project.service.meetRoom.IMeetingRoomService;
import com.yj.oa.project.service.note.INoteService;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

import static com.yj.oa.common.utils.shiro.ShiroUtils.getUserId;

@Controller
@RequestMapping(value = "/enterprise")
public class BaseInfoController extends BaseController {

    private final static String prefix = "system/enterprise";

    @Autowired
    IBaseInfoService baseInfoService;

    @Autowired
    IEquipService equipService;

    /**
     *
     * @描述: 跳转到列表页
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:13
     */
    @RequestMapping("/tobaselist")
    @RequiresPermissions("enterprise:tobaselist")
    public String toList(Model model)
    {
        return prefix + "/baselist";
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
    public TableDataInfo tableList(EnterpriseBaseInfo info)
    {
        startPage();
//        note.setCreateBy(getUserId());
        List<EnterpriseBaseInfo> baseInfoList = baseInfoService.selectEnterpriseBaseInfoList(info);
        return getDataTable(baseInfoList);

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
        return prefix + "/baseadd";
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
    @RequiresPermissions("base:add")
    @Operlog(modal = "企业档案",descr = "新增企业档案")
    @ResponseBody
    public AjaxResult addSave(EnterpriseBaseInfo info) throws Exception
    {
//        note.setCreateBy(getUserId());
//        note.setCreateTime(new Date());
        return result(baseInfoService.insertSelective(info));
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
    @RequiresPermissions("base:del")
    @Operlog(modal = "企业档案",descr = "删除档案")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        return result(baseInfoService.deleteByPrimaryKeys(ids));
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
    @RequiresPermissions("base:update")
    @Operlog(modal = "企业档案",descr = "查看企业档案")
    public String toEdit(@PathVariable("id") Integer id, Model model)
    {
        EnterpriseBaseInfo info = baseInfoService.selectByPrimaryKey(id);
        model.addAttribute("baseinfo", info);
        return prefix + "/baseedit";
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
    @RequiresPermissions("base:update")
    @Operlog(modal = "企业档案",descr = "修改企业档案")
    @ResponseBody
    public AjaxResult editSave(EnterpriseBaseInfo info)
    {
        return result(baseInfoService.updateByPrimaryKeySelective(info));
    }

}
