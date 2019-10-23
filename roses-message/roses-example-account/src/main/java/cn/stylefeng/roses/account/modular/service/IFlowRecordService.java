package cn.stylefeng.roses.account.modular.service;

import cn.stylefeng.roses.message.api.account.model.FlowRecord;
import cn.stylefeng.roses.message.api.order.GoodsFlowParam;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 流水记录 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-05
 */
public interface IFlowRecordService extends IService<FlowRecord> {

    /**
     * 记录订单流水
     *
     * @author stylefeng
     * @Date 2018/5/6 14:00
     */
    void recordFlow(GoodsFlowParam goodsFlowParam);

}
