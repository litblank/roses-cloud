package cn.stylefeng.roses.message.modular.service.impl;

import cn.stylefeng.roses.message.api.model.ReliableMessage;
import cn.stylefeng.roses.message.modular.mapper.ReliableMessageMapper;
import cn.stylefeng.roses.message.modular.service.IReliableMessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-16
 */
@Service
public class ReliableMessageServiceImpl extends ServiceImpl<ReliableMessageMapper, ReliableMessage> implements IReliableMessageService {

}
