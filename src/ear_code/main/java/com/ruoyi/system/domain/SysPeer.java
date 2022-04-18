package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 对等成员对象 sys_peer
 * 
 * @author ruoyi
 * @date 2022-04-04
 */
public class SysPeer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 公网IP */
    @Excel(name = "公网IP")
    private String ip;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String username;

    /** 系统环境 */
    @Excel(name = "系统环境")
    private String env;

    /** 机器名称 */
    @Excel(name = "机器名称")
    private String macName;

    /** 最大任务数 */
    @Excel(name = "最大任务数")
    private Long maxTask;

    /** 是否活跃 */
    @Excel(name = "是否活跃")
    private String isActive;

    /** 最后在线时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后在线时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastActiveTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }
    public void setEnv(String env) 
    {
        this.env = env;
    }

    public String getEnv() 
    {
        return env;
    }
    public void setMacName(String macName) 
    {
        this.macName = macName;
    }

    public String getMacName() 
    {
        return macName;
    }
    public void setMaxTask(Long maxTask) 
    {
        this.maxTask = maxTask;
    }

    public Long getMaxTask() 
    {
        return maxTask;
    }
    public void setIsActive(String isActive) 
    {
        this.isActive = isActive;
    }

    public String getIsActive() 
    {
        return isActive;
    }
    public void setLastActiveTime(Date lastActiveTime) 
    {
        this.lastActiveTime = lastActiveTime;
    }

    public Date getLastActiveTime() 
    {
        return lastActiveTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ip", getIp())
            .append("username", getUsername())
            .append("env", getEnv())
            .append("macName", getMacName())
            .append("maxTask", getMaxTask())
            .append("isActive", getIsActive())
            .append("lastActiveTime", getLastActiveTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
