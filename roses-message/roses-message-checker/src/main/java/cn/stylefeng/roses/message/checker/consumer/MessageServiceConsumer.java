package cn.stylefeng.roses.message.checker.consumer;

import cn.stylefeng.roses.message.api.MessageServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 消息服务消费者
 *
 * @author fengshuonan
 * @date 2018-05-05 22:39
 */
@FeignClient("roses-message-service")
public interface MessageServiceConsumer extends MessageServiceApi {
}
