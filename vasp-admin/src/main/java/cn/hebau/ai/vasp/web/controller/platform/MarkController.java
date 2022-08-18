package cn.hebau.ai.vasp.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hebau.ai.vasp.platform.domain.Activity;
import cn.hebau.ai.vasp.platform.service.IActivityService;
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
import cn.hebau.ai.vasp.platform.domain.Mark;
import cn.hebau.ai.vasp.platform.service.IMarkService;
import cn.hebau.ai.vasp.common.utils.poi.ExcelUtil;
import cn.hebau.ai.vasp.common.core.page.TableDataInfo;

/**
 * 检测Controller
 * 
 * @author zcs
 * @date 2022-08-16
 */
@RestController
@RequestMapping("/platform/mark")
public class MarkController extends BaseController
{
    @Autowired
    private IMarkService markService;

    @Autowired
    private IActivityService activityService;

    /**
     * 查询检测列表
     */
//    @PreAuthorize("@ss.hasPermi('platform:mark:list')")
    @GetMapping("/list")
    public TableDataInfo list(Mark mark)
    {
        startPage();
        List<Mark> markList = markService.selectMarkList(mark);
        for (Mark m : markList) {
            List<Activity> activityList = activityService.selectActivityByMarkId(m.getMarkId());
            m.setActivityList(activityList);
        }
        return getDataTable(markList);
    }

    /**
     * 导出检测列表
     */
    @PreAuthorize("@ss.hasPermi('platform:mark:export')")
    @Log(title = "检测", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Mark mark)
    {
        List<Mark> list = markService.selectMarkList(mark);
        ExcelUtil<Mark> util = new ExcelUtil<Mark>(Mark.class);
        util.exportExcel(response, list, "检测数据");
    }

    /**
     * 获取检测详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:mark:query')")
    @GetMapping(value = "/{markId}")
    public AjaxResult getInfo(@PathVariable("markId") Long markId)
    {
        return AjaxResult.success(markService.selectMarkByMarkId(markId));
    }

    /**
     * 新增检测
     */
    @PreAuthorize("@ss.hasPermi('platform:mark:add')")
    @Log(title = "检测", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Mark mark)
    {
        return toAjax(markService.insertMark(mark));
    }

    /**
     * 修改检测
     */
    @PreAuthorize("@ss.hasPermi('platform:mark:edit')")
    @Log(title = "检测", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Mark mark)
    {
        return toAjax(markService.updateMark(mark));
    }

    /**
     * 删除检测
     */
    @PreAuthorize("@ss.hasPermi('platform:mark:remove')")
    @Log(title = "检测", businessType = BusinessType.DELETE)
	@DeleteMapping("/{markIds}")
    public AjaxResult remove(@PathVariable Long[] markIds)
    {
        return toAjax(markService.deleteMarkByMarkIds(markIds));
    }
}
