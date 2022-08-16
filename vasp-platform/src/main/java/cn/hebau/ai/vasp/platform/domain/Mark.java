package cn.hebau.ai.vasp.platform.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import cn.hebau.ai.vasp.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.hebau.ai.vasp.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 检测对象 plt_mark
 * 
 * @author zcs
 * @date 2022-08-16
 */
public class Mark extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 监测点编号 */
    private Long markId;

    /** 纬度 */
    @Excel(name = "纬度")
    private BigDecimal lat;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal lng;

    /** 所属地区 */
    @Excel(name = "所属地区")
    private String region;

    /** 检测目标 */
    @Excel(name = "检测目标")
    private String target;

    /** 所在地址 */
    @Excel(name = "所在地址")
    private String address;

    /** 检测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date testTime;

    @Excel(name = "坐标点状态")
    private String animation;

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
    public void setLat(BigDecimal lat) 
    {
        this.lat = lat;
    }

    public BigDecimal getLat() 
    {
        return lat;
    }
    public void setLng(BigDecimal lng) 
    {
        this.lng = lng;
    }

    public BigDecimal getLng() 
    {
        return lng;
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


    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
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
            .append("lat", getLat())
            .append("lng", getLng())
            .append("region", getRegion())
            .append("target", getTarget())
            .append("address", getAddress())
            .append("testTime", getTestTime())
            .append("animation", getAnimation())
            .append("activityList", getActivityList())
            .toString();
    }
}
