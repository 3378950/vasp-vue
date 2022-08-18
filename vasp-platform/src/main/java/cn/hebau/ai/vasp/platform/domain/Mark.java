package cn.hebau.ai.vasp.platform.domain;

import java.util.List;
import java.util.Date;

import cn.hebau.ai.vasp.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.hebau.ai.vasp.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 监测点对象 plt_mark
 *
 * @author zcs
 * @date 2022-08-18
 */
public class Mark extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 监测点编号 */
    @Excel(name = "监测点编号")
    private Long markId;

    /** 经度 */
    @Excel(name = "经度")
    private Double lng;

    /** 纬度 */
    @Excel(name = "纬度")
    private Double lat;

    /** 所属地区 */
    @Excel(name = "所属地区")
    private String region;

    /** 检测目标 */
    @Excel(name = "检测目标")
    private String target;

    /** 监测点所在地址 */
    @Excel(name = "监测点所在地址")
    private String address;

    /** 检测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date testTime;

    /** 标注点动态 */
    @Excel(name = "标注点动态")
    private String animation;

    /** 整改是否完成 */
    @Excel(name = "整改是否完成")
    private boolean finished;

    /** 整改动态信息 */
    private List<Activity> activityList;

    public void setMarkId(Long markId)
    {
        this.markId = markId;
    }

    public Long getMarkId()
    {
        return markId;
    }
    public void setLng(Double lng)
    {
        this.lng = lng;
    }

    public Double getLng()
    {
        return lng;
    }
    public void setLat(Double lat)
    {
        this.lat = lat;
    }

    public Double getLat()
    {
        return lat;
    }
    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getRegion()
    {
        return region;
    }
    public void setTarget(String target)
    {
        this.target = target;
    }

    public String getTarget()
    {
        return target;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setTestTime(Date testTime)
    {
        this.testTime = testTime;
    }

    public Date getTestTime()
    {
        return testTime;
    }
    public void setAnimation(String animation)
    {
        this.animation = animation;
    }

    public String getAnimation()
    {
        return animation;
    }
    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    public boolean getFinished()
    {
        return finished;
    }

    public List<Activity> getActivityList()
    {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList)
    {
        this.activityList = activityList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("markId", getMarkId())
                .append("lng", getLng())
                .append("lat", getLat())
                .append("region", getRegion())
                .append("target", getTarget())
                .append("address", getAddress())
                .append("testTime", getTestTime())
                .append("animation", getAnimation())
                .append("finished", getFinished())
                .append("activityList", getActivityList())
                .toString();
    }
}
