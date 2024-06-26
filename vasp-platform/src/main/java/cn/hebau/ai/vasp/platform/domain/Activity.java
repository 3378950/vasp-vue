package cn.hebau.ai.vasp.platform.domain;

import cn.hebau.ai.vasp.common.annotation.Excel;
import cn.hebau.ai.vasp.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 整改动态对象 plt_activity
 *
 * @author zcs
 * @date 2022-08-18
 */
public class Activity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 动态编号 */
    @Excel(name = "动态编号")
    private Long activityId;

    /** 动态名称 */
    @Excel(name = "动态名称")
    private String name;

    /** 监测点编号 */
    @Excel(name = "监测点编号")
    private Long markId;

    /** 详情 */
    @Excel(name = "详情")
    private String detail;

    /** 整改进程 */
    @Excel(name = "整改进程")
    private String process;

    private String receiverRole;

    private String receiverRegion;

    private String receiverLevel;

    private String imgs;

    public void setActivityId(Long activityId)
    {
        this.activityId = activityId;
    }

    public Long getActivityId()
    {
        return activityId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setMarkId(Long markId)
    {
        this.markId = markId;
    }

    public Long getMarkId()
    {
        return markId;
    }
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getDetail()
    {
        return detail;
    }
    public void setProcess(String process)
    {
        this.process = process;
    }

    public String getProcess()
    {
        return process;
    }

    public String getReceiverRole() {
        return receiverRole;
    }

    public void setReceiverRole(String receiverRole) {
        this.receiverRole = receiverRole;
    }

    public String getReceiverRegion() {
        return receiverRegion;
    }

    public void setReceiverRegion(String receiverRegion) {
        this.receiverRegion = receiverRegion;
    }

    public String getReceiverLevel() {
        return receiverLevel;
    }

    public void setReceiverLevel(String receiverLevel) {
        this.receiverLevel = receiverLevel;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("activityId", getActivityId())
                .append("name", getName())
                .append("markId", getMarkId())
                .append("detail", getDetail())
                .append("process", getProcess())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("receiverRole", getReceiverRole())
                .append("receiverRegion", getReceiverRegion())
                .append("receiverLevel", getReceiverLevel())
                .append("imgs", getImgs())
                .toString();
    }
}
