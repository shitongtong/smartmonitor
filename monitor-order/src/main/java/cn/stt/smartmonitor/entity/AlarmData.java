package cn.stt.smartmonitor.entity;

import java.util.Date;

public class AlarmData {
    private String alarmId;

    private String clearId;

    private String ip;

    private String globalCode;

    private String itemName;

    private Integer resType;

    private String resTypeName;

    private Integer eqpType;

    private String eqpTypeName;

    private String faultType;

    private String crackingType;

    private Integer alarmStatus;

    private Date eventTime;

    private Date clearTime;

    private Integer alarmType;

    private Integer alarmSource;

    private Integer alarmLevel;

    private String alarmTitle;

    private Integer alarmTitleType;

    private String alarmText;

    private Integer alarmTextType;

    private String alarmImage;

    private String facilityName;

    private String facilityFrim;

    private String eqpCode;

    private String assetNumber;

    private Integer policeId;

    private String policeStation;

    private String location;

    private String mac;

    private String portCode;

    private String diagnoseResult;

    private Integer planType;

    private String parentId;

    private Integer ackStatus;

    private Date confirmTime;

    private String confirmUser;

    private Date createTime;

    private Date updateTime;

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId == null ? null : alarmId.trim();
    }

    public String getClearId() {
        return clearId;
    }

    public void setClearId(String clearId) {
        this.clearId = clearId == null ? null : clearId.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode == null ? null : globalCode.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
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

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType == null ? null : faultType.trim();
    }

    public String getCrackingType() {
        return crackingType;
    }

    public void setCrackingType(String crackingType) {
        this.crackingType = crackingType == null ? null : crackingType.trim();
    }

    public Integer getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(Integer alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Date getClearTime() {
        return clearTime;
    }

    public void setClearTime(Date clearTime) {
        this.clearTime = clearTime;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public Integer getAlarmSource() {
        return alarmSource;
    }

    public void setAlarmSource(Integer alarmSource) {
        this.alarmSource = alarmSource;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle == null ? null : alarmTitle.trim();
    }

    public Integer getAlarmTitleType() {
        return alarmTitleType;
    }

    public void setAlarmTitleType(Integer alarmTitleType) {
        this.alarmTitleType = alarmTitleType;
    }

    public String getAlarmText() {
        return alarmText;
    }

    public void setAlarmText(String alarmText) {
        this.alarmText = alarmText == null ? null : alarmText.trim();
    }

    public Integer getAlarmTextType() {
        return alarmTextType;
    }

    public void setAlarmTextType(Integer alarmTextType) {
        this.alarmTextType = alarmTextType;
    }

    public String getAlarmImage() {
        return alarmImage;
    }

    public void setAlarmImage(String alarmImage) {
        this.alarmImage = alarmImage == null ? null : alarmImage.trim();
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName == null ? null : facilityName.trim();
    }

    public String getFacilityFrim() {
        return facilityFrim;
    }

    public void setFacilityFrim(String facilityFrim) {
        this.facilityFrim = facilityFrim == null ? null : facilityFrim.trim();
    }

    public String getEqpCode() {
        return eqpCode;
    }

    public void setEqpCode(String eqpCode) {
        this.eqpCode = eqpCode == null ? null : eqpCode.trim();
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber == null ? null : assetNumber.trim();
    }

    public Integer getPoliceId() {
        return policeId;
    }

    public void setPoliceId(Integer policeId) {
        this.policeId = policeId;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation == null ? null : policeStation.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode == null ? null : portCode.trim();
    }

    public String getDiagnoseResult() {
        return diagnoseResult;
    }

    public void setDiagnoseResult(String diagnoseResult) {
        this.diagnoseResult = diagnoseResult == null ? null : diagnoseResult.trim();
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getAckStatus() {
        return ackStatus;
    }

    public void setAckStatus(Integer ackStatus) {
        this.ackStatus = ackStatus;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser == null ? null : confirmUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}