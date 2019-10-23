/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.roses.order.core.db;

import cn.stylefeng.roses.core.db.DbInitializer;
import cn.stylefeng.roses.message.api.order.model.GoodsOrder;
import org.springframework.stereotype.Component;

/**
 * 订单表初始化
 *
 * @author fengshuonan
 * @date 2018-07-30-上午9:29
 */
@Component
public class GoodsOrderInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `goods_order` (\n" +
                "  `id` bigint(20) NOT NULL,\n" +
                "  `goods_name` varchar(255) NOT NULL COMMENT '商品名称',\n" +
                "  `count` int(11) NOT NULL COMMENT '数量',\n" +
                "  `sum` decimal(10,0) DEFAULT NULL COMMENT '总金额',\n" +
                "  `create_time` datetime NOT NULL COMMENT '创建时间',\n" +
                "  `user_id` bigint(20) NOT NULL COMMENT '下单人id',\n" +
                "  `status` int(11) DEFAULT NULL COMMENT '订单状态：1.未完成    2.已完成',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';";
    }

    @Override
    public String getTableName() {
        return "goods_order";
    }

    @Override
    public Class<?> getEntityClass() {
        return GoodsOrder.class;
    }
}
