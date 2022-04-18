package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysPeer;
import com.ruoyi.system.service.ISysPeerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 对等成员Controller
 * 
 * @author ruoyi
 * @date 2022-04-04
 */
@RestController
@RequestMapping("/system/peer")
public class SysPeerController extends BaseController
{
    @Autowired
    private ISysPeerService sysPeerService;

    /**
     * 查询对等成员列表
     */
    @PreAuthorize("@ss.hasPermi('system:peer:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPeer sysPeer)
    {
        startPage();
        List<SysPeer> list = sysPeerService.selectSysPeerList(sysPeer);
        return getDataTable(list);
    }

    /**
     * 导出对等成员列表
     */
    @PreAuthorize("@ss.hasPermi('system:peer:export')")
    @Log(title = "对等成员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPeer sysPeer)
    {
        List<SysPeer> list = sysPeerService.selectSysPeerList(sysPeer);
        ExcelUtil<SysPeer> util = new ExcelUtil<SysPeer>(SysPeer.class);
        util.exportExcel(response, list, "对等成员数据");
    }

    /**
     * 获取对等成员详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:peer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysPeerService.selectSysPeerById(id));
    }

    /**
     * 新增对等成员
     */
    @PreAuthorize("@ss.hasPermi('system:peer:add')")
    @Log(title = "对等成员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPeer sysPeer)
    {
        return toAjax(sysPeerService.insertSysPeer(sysPeer));
    }

    /**
     * 修改对等成员
     */
    @PreAuthorize("@ss.hasPermi('system:peer:edit')")
    @Log(title = "对等成员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPeer sysPeer)
    {
        return toAjax(sysPeerService.updateSysPeer(sysPeer));
    }

    /**
     * 删除对等成员
     */
    @PreAuthorize("@ss.hasPermi('system:peer:remove')")
    @Log(title = "对等成员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysPeerService.deleteSysPeerByIds(ids));
    }
}
