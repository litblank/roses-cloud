package cn.stylefeng.roses.order.test.order;

import cn.stylefeng.roses.order.modular.service.IOrderService;
import cn.stylefeng.roses.order.test.base.BaseJunit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单测试
 *
 * @author fengshuonan
 * @date 2018-05-06 12:40
 */
public class OrderTest extends BaseJunit {

    @Autowired
    private IOrderService orderService;

    @Test
    public void makeOrder() {
        orderService.makeTestOrder();
    }

}
