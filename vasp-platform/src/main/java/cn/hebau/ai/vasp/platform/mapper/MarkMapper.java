package cn.hebau.ai.vasp.platform.mapper;

import java.util.List;
import cn.hebau.ai.vasp.platform.domain.Mark;
import cn.hebau.ai.vasp.platform.domain.Activity;

/**
 * 检测Mapper接口
 * 
 * @author zcs
 * @date 2022-08-16
 */
public interface MarkMapper 
{
    /**
     * 查询检测
     * 
     * @param markId 检测主键
     * @return 检测
     */
    public Mark selectMarkByMarkId(Long markId);

    /**
     * 查询检测列表
     * 
     * @param mark 检测
     * @return 检测集合
     */
    public List<Mark> selectMarkList(Mark mark);

    /**
     * 新增检测
     * 
     * @param mark 检测
     * @return 结果
     */
    public int insertMark(Mark mark);

    /**
     * 修改检测
     * 
     * @param mark 检测
     * @return 结果
     */
    public int updateMark(Mark mark);

    /**
     * 删除检测
     * 
     * @param markId 检测主键
     * @return 结果
     */
    public int deleteMarkByMarkId(Long markId);

    /**
     * 批量删除检测
     * 
     * @param markIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMarkByMarkIds(Long[] markIds);

    /**
     * 批量删除整改动态
     * 
     * @param markIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActivityByMarkIds(Long[] markIds);
    
    /**
     * 批量新增整改动态
     * 
     * @param activityList 整改动态列表
     * @return 结果
     */
    public int batchActivity(List<Activity> activityList);
    

    /**
     * 通过检测主键删除整改动态信息
     * 
     * @param markId 检测ID
     * @return 结果
     */
    public int deleteActivityByMarkId(Long markId);
}
