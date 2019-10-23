package cn.stylefeng.roses.message.checker.service.impl;

import cn.stylefeng.roses.kernel.logger.util.LogUtil;
import cn.stylefeng.roses.kernel.model.page.PageQuery;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import cn.stylefeng.roses.message.api.model.ReliableMessage;
import cn.stylefeng.roses.message.api.order.enums.OrderStatusEnum;
import cn.stylefeng.roses.message.api.order.model.GoodsOrder;
import cn.stylefeng.roses.message.checker.consumer.GoodsOrderConsumer;
import cn.stylefeng.roses.message.checker.consumer.MessageServiceConsumer;
import cn.stylefeng.roses.message.checker.service.AbstractMessageChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理状态为“待确认”但已超时的消息
 *
 * @author fengshuonan
 * @date 2018-05-08 23:07
 */
@Service
public class WaitingConfirmMessageChecker extends AbstractMessageChecker {

    @Autowired
    private MessageServiceConsumer messageServiceConsumer;

    @Autowired
    private GoodsOrderConsumer goodsOrderConsumer;

    @Override
    protected void processMessage(Map<String, ReliableMessage> messages) {
        for (Map.Entry<String, ReliableMessage> entry : messages.entrySet()) {
            ReliableMessage message = entry.getValue();
            try {

                Long orderId = message.getBizUniqueId();

                if (orderId == null) {

                    //如果订单失败，则删掉没用的消息
                    messageServiceConsumer.deleteMessageByMessageId(message.getMessageId());
                } else {
                    GoodsOrder order = goodsOrderConsumer.findOrderById(orderId);

                    //如果订单成功，则确认消息并发送
                    if (order != null && OrderStatusEnum.SUCCESS.equals(order.getStatus())) {
                        messageServiceConsumer.confirmAndSendMessage(message.getMessageId());
                    } else {

                        //如果订单失败，则删掉没用的消息
                        messageServiceConsumer.deleteMessageByMessageId(message.getMessageId());
                    }
                }

            } catch (Exception e) {
                LogUtil.error("处理待确认消息异常！messageId=" + message.getMessageId(), e);
            }
        }
    }

    @Override
    protected PageResult<ReliableMessage> getPageResult(PageQuery pageQuery) {
        return messageServiceConsumer.listPagetWaitConfimTimeOutMessages(pageQuery);
    }

}
