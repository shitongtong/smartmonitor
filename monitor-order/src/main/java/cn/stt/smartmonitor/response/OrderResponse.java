package cn.stt.smartmonitor.response;

import lombok.Data;

/**
 * @ClassName OrderResponse
 * @Description TODO
 * @Author shitt7
 * @Date 2019/4/23 15:14
 * @Version 1.0
 */
@Data
public class OrderResponse {
    private String code;
    private String desc;
    private OrderDataResponse data;

    @Data
    public class OrderDataResponse {
        private String orderId;
        private String alarmCd;
        private String orderNumber;
    }
}


