package cn.hebau.ai.vasp.platform.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import cn.hebau.ai.vasp.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import cn.hebau.ai.vasp.platform.domain.Activity;
import cn.hebau.ai.vasp.platform.mapper.MarkMapper;
import cn.hebau.ai.vasp.platform.domain.Mark;
import cn.hebau.ai.vasp.platform.service.IMarkService;

/**
 * 检测Service业务层处理
 * 
 * @author zcs
 * @date 2022-08-16
 */
@Service
public class MarkServiceImpl implements IMarkService 
{
    @Autowired
    private MarkMapper markMapper;

    /**
     * 查询检测
     * 
     * @param markId 检测主键
     * @return 检测
     */
    @Override
    public Mark selectMarkByMarkId(Long markId)
    {
        return markMapper.selectMarkByMarkId(markId);
    }

    /**
     * 查询检测列表
     * 
     * @param mark 检测
     * @return 检测
     */
    @Override
    public List<Mark> selectMarkList(Mark mark)
    {
        return markMapper.selectMarkList(mark);
    }

    /**
     * 新增检测
     * 
     * @param mark 检测
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMark(Mark mark)
    {
        int rows = markMapper.insertMark(mark);
        insertActivity(mark);
        return rows;
    }

    /**
     * 修改检测
     * 
     * @param mark 检测
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMark(Mark mark)
    {
//        markMapper.deleteActivityByMarkId(mark.getMarkId());
//        insertActivity(mark);
        return markMapper.updateMark(mark);
    }

    /**
     * 批量删除检测
     * 
     * @param markIds 需要删除的检测主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMarkByMarkIds(Long[] markIds)
    {
        markMapper.deleteActivityByMarkIds(markIds);
        return markMapper.deleteMarkByMarkIds(markIds);
    }

    /**
     * 删除检测信息
     * 
     * @param markId 检测主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMarkByMarkId(Long markId)
    {
        markMapper.deleteActivityByMarkId(markId);
        return markMapper.deleteMarkByMarkId(markId);
    }

    /**
     * 新增整改动态信息
     * 
     * @param mark 检测对象
     */
    public void insertActivity(Mark mark)
    {
        List<Activity> activityList = mark.getActivityList();
        Long markId = mark.getMarkId();
        if (StringUtils.isNotNull(activityList))
        {
            List<Activity> list = new ArrayList<Activity>();
            for (Activity activity : activityList)
            {
                activity.setMarkId(markId);
                list.add(activity);
            }
            if (list.size() > 0)
            {
                markMapper.batchActivity(list);
            }
        }
    }
}
