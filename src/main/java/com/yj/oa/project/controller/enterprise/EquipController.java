package com.yj.oa.project.controller.enterprise;

import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.page.TableDataInfo;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.enterprise.EnterpriseBaseInfo;
import com.yj.oa.project.po.enterprise.Equip;
import com.yj.oa.project.service.enterprise.IBaseInfoService;
import com.yj.oa.project.service.equip.IEquipService;
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
@RequestMapping(value = "/equip")
public class EquipController  extends BaseController {

    private final static String prefix = "system/equip";

    @Autowired
    IEquipService service;

    @Autowired
    IBaseInfoService baseInfoService;

    /**
     *
     * @描述: 跳转到列表页
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:13
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("equip:tolist")
    public String toList(Model model)
    {
        List<EnterpriseBaseInfo> baseInfoList = baseInfoService.selectEnterpriseBaseInfoList(new EnterpriseBaseInfo());
        model.addAttribute("infos",baseInfoList);
        return prefix + "/equip";
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
    public TableDataInfo tableList(Equip equip)
    {
        startPage();
//        note.setCreateBy(getUserId());
        List<Equip> equipList = service.selectEquipList(equip);
        return getDataTable(equipList);

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
        List<EnterpriseBaseInfo> baseInfoList = baseInfoService.selectEnterpriseBaseInfoList(new EnterpriseBaseInfo());
        model.addAttribute("infos",baseInfoList);
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
    @RequiresPermissions("equip:add")
    @Operlog(modal = "企业设备档案",descr = "企业设备档案录入")
    @ResponseBody
    public AjaxResult addSave(Equip equip) throws Exception
    {
        equip.setCreateTime(new Date());
        return result(service.insertSelective(equip));
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
    @RequiresPermissions("equip:del")
    @Operlog(modal = "企业设备档案",descr = "企业设备档案删除")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        return result(service.deleteByPrimaryKeys(ids));
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
    @RequiresPermissions("equip:update")
    @Operlog(modal = "企业设备档案",descr = "查看企业设备档案")
    public String toEdit(@PathVariable("id") Integer id, Model model)
    {
        List<EnterpriseBaseInfo> baseInfoList = baseInfoService.selectEnterpriseBaseInfoList(new EnterpriseBaseInfo());
        Equip equip = service.selectByPrimaryKey(id);
        model.addAttribute("infos",baseInfoList);
        model.addAttribute("equip", equip);
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
    @RequiresPermissions("equip:update")
    @Operlog(modal = "企业设备档案",descr = "修改企业设备档案")
    @ResponseBody
    public AjaxResult editSave(Equip equip)
    {
        return result(service.updateByPrimaryKeySelective(equip));
    }
}
