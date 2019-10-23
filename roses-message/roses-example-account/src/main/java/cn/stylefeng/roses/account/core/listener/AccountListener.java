package cn.stylefeng.roses.account.core.listener;

import cn.stylefeng.roses.account.modular.service.IFlowRecordService;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.message.api.exception.MessageExceptionEnum;
import cn.stylefeng.roses.message.api.order.GoodsFlowParam;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class AccountListener implements MessageListener {

    @Autowired
    private IFlowRecordService flowRecordService;

    @Override
    public void onMessage(Message message) {

        if (message instanceof TextMessage) {
            try {
                String messageBody = ((TextMessage) message).getText();

                if (ToolUtil.isEmpty(messageBody)) {
                    throw new ServiceException(MessageExceptionEnum.MESSAGE_BODY_CANT_EMPTY);
                }

                GoodsFlowParam goodsFlowParam = JSON.parseObject(messageBody, GoodsFlowParam.class);
                flowRecordService.recordFlow(goodsFlowParam);

            } catch (JMSException ex) {
                throw new ServiceException(MessageExceptionEnum.MESSAGE_QUEUE_ERROR);
            }

        } else {
            throw new ServiceException(MessageExceptionEnum.MESSAGE_TYPE_ERROR);
        }
    }

}