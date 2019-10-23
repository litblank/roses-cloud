package cn.stylefeng.roses.message.core.activemq.impl;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.message.api.model.ReliableMessage;
import cn.stylefeng.roses.message.core.activemq.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.jms.core.JmsTemplate;

/**
 * activemq实现的消息发送器
 *
 * @author fengshuonan
 * @date 2018-04-22 22:03
 */
public class ActiveMqMessageSender implements MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(ReliableMessage reliableMessage) {

        if (reliableMessage == null ||
                ToolUtil.isOneEmpty(reliableMessage.getConsumerQueue(), reliableMessage.getMessageBody())) {
            throw new RequestEmptyException();
        }

        jmsTemplate.setDefaultDestinationName(reliableMessage.getConsumerQueue());

        //设置ack确认为client方式
        jmsTemplate.setSessionAcknowledgeMode(JmsProperties.AcknowledgeMode.CLIENT.getMode());

        //发送消息
        jmsTemplate.send(session -> session.createTextMessage(reliableMessage.getMessageBody()));
    }
}
