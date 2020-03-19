package cn.stt.smartmonitor.entity;

import java.util.Date;

public class AlarmRules {
    private Integer id;

    private String ruleName;

    private Integer resType;

    private String resTypeName;

    private Integer eqpType;

    private String eqpTypeName;

    private Integer performanceId;

    private String performanceName;

    private String itemName;

    private Integer thresholdStatus;

    private Integer lowerThreshold;

    private Integer threshold;

    private Integer alarmLevel;

    private Integer sendStatus;

    private Integer useStatus;

    private Date startTime;

    private Date endTime;

    private Date recentTime;

    private String description;

    private Integer alarmCount;

    private Integer clearCount;

    private String remark;

    private Integer sort;

    private Integer status;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getResTypeName() {
        return resTypeName;
    }

    public void setResTypeName(String resTypeName) {
        this.resTypeName = resTypeName == null ? null : resTypeName.trim();
    }

    public Integer getEqpType() {
        return eqpType;
    }

    public void setEqpType(Integer eqpType) {
        this.eqpType = eqpType;
    }

    public String getEqpTypeName() {
        return eqpTypeName;
    }

    public void setEqpTypeName(String eqpTypeName) {
        this.eqpTypeName = eqpTypeName == null ? null : eqpTypeName.trim();
    }

    public Integer getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(Integer performanceId) {
        this.performanceId = performanceId;
    }

    public String getPerformanceName() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName == null ? null : performanceName.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public Integer getThresholdStatus() {
        return thresholdStatus;
    }

    public void setThresholdStatus(Integer thresholdStatus) {
        this.thresholdStatus = thresholdStatus;
    }

    public Integer getLowerThreshold() {
        return lowerThreshold;
    }

    public void setLowerThreshold(Integer lowerThreshold) {
        this.lowerThreshold = lowerThreshold;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(Date recentTime) {
        this.recentTime = recentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(Integer alarmCount) {
        this.alarmCount = alarmCount;
    }

    public Integer getClearCount() {
        return clearCount;
    }

    public void setClearCount(Integer clearCount) {
        this.clearCount = clearCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}