package cn.luokaiii.common.constant;

public interface MqQueueConstant {

    /**
     * Log 队列
     */
    String LOG_QUEUE = "log";

    /**
     * 发送手机短信队列
     */
    String MOBILE_QUEUE = "mobile_queue";

    /**
     * 服务状态队列
     */
    String SERVICE_STATUS_CHANGE = "service_status_change";

    /**
     * Zipkin 队列
     */
    String ZIPKIN_NAME_QUEUE = "zipkin";
}
