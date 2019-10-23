package cn.stylefeng.roses.order.modular.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.message.api.enums.MessageQueueEnum;
import cn.stylefeng.roses.message.api.model.ReliableMessage;
import cn.stylefeng.roses.message.api.order.enums.OrderStatusEnum;
import cn.stylefeng.roses.message.api.order.model.GoodsOrder;
import cn.stylefeng.roses.order.core.exception.OrderExceptionEnum;
import cn.stylefeng.roses.order.modular.consumer.MessageServiceConsumer;
import cn.stylefeng.roses.order.modular.mapper.OrderMapper;
import cn.stylefeng.roses.order.modular.service.IOrderService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, GoodsOrder> implements IOrderService {

    @Autowired
    private MessageServiceConsumer messageServiceConsumer;

    @Override
    public Long makeTestOrder() {

        //创建预发送消息
        GoodsOrder goods = createGoods();

        //下单
        this.insert(goods);

        //返回订单号
        return goods.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishOrder(String orderId) {

        if (ToolUtil.isEmpty(orderId)) {
            throw new RequestEmptyException();
        }

        GoodsOrder order = this.selectById(orderId);

        if (order == null) {
            throw new ServiceException(OrderExceptionEnum.ORDER_NULL);
        }

        if (OrderStatusEnum.SUCCESS.getStatus().equals(order.getStatus())) {
            return;
        }

        //创建预发送消息
        ReliableMessage reliableMessage = createMessage(order);

        //预发送消息
        messageServiceConsumer.preSaveMessage(reliableMessage);

        //更新订单为成功状态(百分之50几率失败，模拟错误数据)（此处错误已添加到消息表的数据会被roses-message-checker轮询时删除掉）
        updateToSuccess(order);

        //确认消息
        messageServiceConsumer.confirmAndSendMessage(reliableMessage.getMessageId());
    }

    private ReliableMessage createMessage(GoodsOrder goodsOrder) {
        String messageId = IdWorker.getIdStr();
        String messageBody = JSON.toJSONString(goodsOrder);
        String queue = MessageQueueEnum.MAKE_ORDER.name();
        ReliableMessage reliableMessage = new ReliableMessage(messageId, messageBody, queue);
        reliableMessage.setBizUniqueId(goodsOrder.getId());
        return reliableMessage;
    }

    private GoodsOrder createGoods() {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setGoodsName("商品" + RandomUtil.randomNumbers(4));
        goodsOrder.setCount(Integer.valueOf(RandomUtil.randomNumbers(1)));
        goodsOrder.setCreateTime(new Date());
        goodsOrder.setSum(new BigDecimal(RandomUtil.randomDouble(10.0, 50.0)).setScale(2, RoundingMode.HALF_UP));
        goodsOrder.setId(IdWorker.getId());
        goodsOrder.setUserId(IdWorker.getId());
        goodsOrder.setStatus(OrderStatusEnum.NOT_SUCCESS.getStatus());  //未完成的订单

        return goodsOrder;
    }

    private void updateToSuccess(GoodsOrder order) {
        order.setStatus(OrderStatusEnum.SUCCESS.getStatus());
        this.updateById(order);

        int random = RandomUtil.randomInt(100);
        if (random > 50) {
            return;
        } else {
            throw new ServiceException(OrderExceptionEnum.ORDER_ERROR);
        }
    }
}
