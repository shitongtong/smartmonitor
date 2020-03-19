package cn.stt.smartmonitor.enums;

/**
 * @ClassName AlarmOrderEnum
 * @Description TODO
 * @Author shitt7
 * @Date 2019/4/22 20:41
 * @Version 1.0
 */
public class AlarmOrderEnum {

    public enum SendStatus {
        /**
         * 派单状态
         */
        FAIL(0, "派单失败"),
        SUCCESS(1, "已派单");

        public int key;
        public String value;

        SendStatus(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getEnumValueByKey(int key) {
            for (AlarmOrderEnum.SendStatus sendStatus : AlarmOrderEnum.SendStatus.values()) {
                if (key == sendStatus.key) {
                    return sendStatus.value;
                }
            }
            return null;
        }
    }

    public enum OrderStatus {
        /**
         * 工单状态
         */
        REPAIR(0, "报修"),
        DISPATCH(1, "派单"),
        PICKUP(2, "接单"),
        COMPLETE(3, "处理完成"),
        EVALUATE(4, "已评价");

        public int key;
        public String value;

        OrderStatus(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getEnumValueByKey(int key) {
            for (AlarmOrderEnum.OrderStatus orderStatus : AlarmOrderEnum.OrderStatus.values()) {
                if (key == orderStatus.key) {
                    return orderStatus.value;
                }
            }
            return null;
        }
    }
}
