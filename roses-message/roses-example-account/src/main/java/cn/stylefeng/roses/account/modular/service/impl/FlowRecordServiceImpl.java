package cn.stylefeng.roses.account.modular.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.roses.account.modular.consumer.MessageServiceConsumer;
import cn.stylefeng.roses.account.modular.mapper.FlowRecordMapper;
import cn.stylefeng.roses.account.modular.service.IFlowRecordService;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.exception.enums.CoreExceptionEnum;
import cn.stylefeng.roses.message.api.account.model.FlowRecord;
import cn.stylefeng.roses.message.api.order.GoodsFlowParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 流水记录 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-05
 */
@Service
public class FlowRecordServiceImpl extends ServiceImpl<FlowRecordMapper, FlowRecord> implements IFlowRecordService {

    @Autowired
    private MessageServiceConsumer messageServiceConsumer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordFlow(GoodsFlowParam goodsFlowParam) {

        if (goodsFlowParam == null) {
            throw new RequestEmptyException();
        }

        if (ToolUtil.isOneEmpty(goodsFlowParam.getUserId(), goodsFlowParam.getGoodsName(), goodsFlowParam.getSum())) {
            throw new RequestEmptyException();
        }

        //幂等判断
        EntityWrapper<FlowRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("order_id", goodsFlowParam.getId());
        List<FlowRecord> flowRecords = this.selectList(wrapper);
        if (flowRecords != null && !flowRecords.isEmpty()) {
            return;
        }

        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setUserId(goodsFlowParam.getUserId());
        flowRecord.setSum(goodsFlowParam.getSum());
        flowRecord.setOrderId(goodsFlowParam.getId());
        flowRecord.setName(goodsFlowParam.getGoodsName());
        flowRecord.setCreateTime(new Date());

        this.insert(flowRecord);

        //测试可靠消息机制（结果百分之50几率成功）(如果此处有异常，会被roses-message-checker轮询处理，如果此处连续出现错误6次则可以通过查看消息表，人工干预)
        int random = RandomUtil.randomInt(100);
        if (random > 50) {
            throw new ServiceException(CoreExceptionEnum.SERVICE_ERROR);
        }

        //插入成功后要删除消息
        messageServiceConsumer.deleteMessageByBizId(flowRecord.getOrderId());
    }
}
