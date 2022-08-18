package cn.hebau.ai.vasp.platform.service;

import java.util.List;
import cn.hebau.ai.vasp.platform.domain.Activity;

/**
 * 整改动态Service接口
 * 
 * @author zcs
 * @date 2022-08-16
 */
public interface IActivityService 
{
    /**
     * 查询整改动态
     * 
     * @param activityId 整改动态主键
     * @return 整改动态
     */
    public Activity selectActivityByActivityId(Long activityId);

    public List<Activity> selectActivityByMarkId(Long markId);

    /**
     * 查询整改动态列表
     * 
     * @param activity 整改动态
     * @return 整改动态集合
     */
    public List<Activity> selectActivityList(Activity activity);

    /**
     * 新增整改动态
     * 
     * @param activity 整改动态
     * @return 结果
     */
    public int insertActivity(Activity activity);

    /**
     * 修改整改动态
     * 
     * @param activity 整改动态
     * @return 结果
     */
    public int updateActivity(Activity activity);

    /**
     * 批量删除整改动态
     * 
     * @param activityIds 需要删除的整改动态主键集合
     * @return 结果
     */
    public int deleteActivityByActivityIds(Long[] activityIds);

    /**
     * 删除整改动态信息
     * 
     * @param activityId 整改动态主键
     * @return 结果
     */
    public int deleteActivityByActivityId(Long activityId);
}
