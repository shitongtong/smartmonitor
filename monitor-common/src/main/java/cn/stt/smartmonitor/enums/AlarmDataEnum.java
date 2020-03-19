package cn.stt.smartmonitor.enums;

/**
 * @ClassName AlarmEnum
 * @Description TODO
 * @Author shitt7
 * @Date 2019/1/30 10:41
 * @Version 1.0
 */
public class AlarmDataEnum {

    public enum AlarmStatus {
        /**
         * 告警状态
         */
        ACTIVITY(0, "活跃告警"),
        CLEAR(1, "清除告警");

        public int key;
        public String value;

        AlarmStatus(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getEnumValueByKey(int key) {
            for (AlarmStatus alarmStatus : AlarmStatus.values()) {
                if (key == alarmStatus.key) {
                    return alarmStatus.value;
                }
            }
            return null;
        }
    }

    public enum AlarmType {
        /**
         * 告警类型
         */
        SERVER_OS(54, "操作系统服务器告警");

        public int key;
        public String value;

        AlarmType(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getEnumValueByKey(int key) {
            for (AlarmType alarmType : AlarmType.values()) {
                if (key == alarmType.key) {
                    return alarmType.value;
                }
            }
            return null;
        }

        public static AlarmType getEnumByKey(int key) {
            for (AlarmType alarmType : AlarmType.values()) {
                if (key == alarmType.key) {
                    return alarmType;
                }
            }
            return null;
        }
    }

    public enum AlarmLevel {
        /**
         * 告警级别
         */
        ONE(0, "一级告警"),
        TWO(1, "二级告警"),
        THREE(2, "三级告警");

        public int key;
        public String value;

        AlarmLevel(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getEnumValueByKey(int key) {
            for (AlarmLevel alarmLevel : AlarmLevel.values()) {
                if (key == alarmLevel.key) {
                    return alarmLevel.value;
                }
            }
            return null;
        }

    }


}
