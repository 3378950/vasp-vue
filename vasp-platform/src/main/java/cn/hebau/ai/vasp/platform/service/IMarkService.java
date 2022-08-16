package cn.hebau.ai.vasp.platform.service;

import java.util.List;
import cn.hebau.ai.vasp.platform.domain.Mark;

/**
 * 检测Service接口
 * 
 * @author zcs
 * @date 2022-08-16
 */
public interface IMarkService 
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
     * 批量删除检测
     * 
     * @param markIds 需要删除的检测主键集合
     * @return 结果
     */
    public int deleteMarkByMarkIds(Long[] markIds);

    /**
     * 删除检测信息
     * 
     * @param markId 检测主键
     * @return 结果
     */
    public int deleteMarkByMarkId(Long markId);
}
