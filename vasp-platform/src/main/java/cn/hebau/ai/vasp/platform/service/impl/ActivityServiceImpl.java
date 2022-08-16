package cn.hebau.ai.vasp.platform.service.impl;

import java.util.List;
import cn.hebau.ai.vasp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hebau.ai.vasp.platform.mapper.ActivityMapper;
import cn.hebau.ai.vasp.platform.domain.Activity;
import cn.hebau.ai.vasp.platform.service.IActivityService;

/**
 * 整改动态Service业务层处理
 * 
 * @author zcs
 * @date 2022-08-16
 */
@Service
public class ActivityServiceImpl implements IActivityService 
{
    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 查询整改动态
     * 
     * @param activityId 整改动态主键
     * @return 整改动态
     */
    @Override
    public Activity selectActivityByActivityId(Long activityId)
    {
        return activityMapper.selectActivityByActivityId(activityId);
    }

    /**
     * 查询整改动态列表
     * 
     * @param activity 整改动态
     * @return 整改动态
     */
    @Override
    public List<Activity> selectActivityList(Activity activity)
    {
        return activityMapper.selectActivityList(activity);
    }

    /**
     * 新增整改动态
     * 
     * @param activity 整改动态
     * @return 结果
     */
    @Override
    public int insertActivity(Activity activity)
    {
        activity.setCreateTime(DateUtils.getNowDate());
        return activityMapper.insertActivity(activity);
    }

    /**
     * 修改整改动态
     * 
     * @param activity 整改动态
     * @return 结果
     */
    @Override
    public int updateActivity(Activity activity)
    {
        return activityMapper.updateActivity(activity);
    }

    /**
     * 批量删除整改动态
     * 
     * @param activityIds 需要删除的整改动态主键
     * @return 结果
     */
    @Override
    public int deleteActivityByActivityIds(Long[] activityIds)
    {
        return activityMapper.deleteActivityByActivityIds(activityIds);
    }

    /**
     * 删除整改动态信息
     * 
     * @param activityId 整改动态主键
     * @return 结果
     */
    @Override
    public int deleteActivityByActivityId(Long activityId)
    {
        return activityMapper.deleteActivityByActivityId(activityId);
    }
}
