package cn.hebau.ai.vasp.web.controller.platform;

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
import cn.hebau.ai.vasp.common.annotation.Log;
import cn.hebau.ai.vasp.common.core.controller.BaseController;
import cn.hebau.ai.vasp.common.core.domain.AjaxResult;
import cn.hebau.ai.vasp.common.enums.BusinessType;
import cn.hebau.ai.vasp.platform.domain.Activity;
import cn.hebau.ai.vasp.platform.service.IActivityService;
import cn.hebau.ai.vasp.common.utils.poi.ExcelUtil;
import cn.hebau.ai.vasp.common.core.page.TableDataInfo;

/**
 * 整改动态Controller
 * 
 * @author zcs
 * @date 2022-08-16
 */
@RestController
@RequestMapping("/platform/activity")
public class ActivityController extends BaseController
{
    @Autowired
    private IActivityService activityService;

    /**
     * 查询整改动态列表
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(Activity activity)
    {
        startPage();
        List<Activity> list = activityService.selectActivityList(activity);
        return getDataTable(list);
    }

    /**
     * 导出整改动态列表
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:export')")
    @Log(title = "整改动态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Activity activity)
    {
        List<Activity> list = activityService.selectActivityList(activity);
        ExcelUtil<Activity> util = new ExcelUtil<Activity>(Activity.class);
        util.exportExcel(response, list, "整改动态数据");
    }

    /**
     * 获取整改动态详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:query')")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId)
    {
        return AjaxResult.success(activityService.selectActivityByActivityId(activityId));
    }

    /**
     * 新增整改动态
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:add')")
    @Log(title = "整改动态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Activity activity)
    {
        return toAjax(activityService.insertActivity(activity));
    }

    /**
     * 修改整改动态
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:edit')")
    @Log(title = "整改动态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Activity activity)
    {
        return toAjax(activityService.updateActivity(activity));
    }

    /**
     * 删除整改动态
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:remove')")
    @Log(title = "整改动态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds)
    {
        return toAjax(activityService.deleteActivityByActivityIds(activityIds));
    }
}
