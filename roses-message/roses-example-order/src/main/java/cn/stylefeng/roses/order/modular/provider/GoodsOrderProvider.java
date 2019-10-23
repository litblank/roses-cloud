package cn.stylefeng.roses.order.modular.provider;


import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.message.api.order.GoodsOrderApi;
import cn.stylefeng.roses.message.api.order.model.GoodsOrder;
import cn.stylefeng.roses.order.modular.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 消息服务的接口
 *
 * @author stylefeng
 * @Date 2018/4/16 21:47
 */
@RestController
public class GoodsOrderProvider implements GoodsOrderApi {

    @Autowired
    private IOrderService orderService;

    @Override
    public GoodsOrder findOrderById(@RequestParam("orderId") Long orderId) {

        if (ToolUtil.isEmpty(orderId)) {
            return null;
        } else {
            return orderService.selectById(orderId);
        }
    }
}