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
package cn.stylefeng.roses.account.core.db;

import cn.stylefeng.roses.core.db.DbInitializer;
import cn.stylefeng.roses.message.api.account.model.FlowRecord;
import org.springframework.stereotype.Component;

/**
 * 流水记录表初始化
 *
 * @author fengshuonan
 * @date 2018-07-30-上午9:29
 */
@Component
public class FlowRecordInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `flow_record` (\n" +
                "  `id` bigint(20) NOT NULL,\n" +
                "  `user_id` bigint(20) NOT NULL COMMENT '用户id',\n" +
                "  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',\n" +
                "  `name` varchar(255) NOT NULL COMMENT '流水名称',\n" +
                "  `sum` decimal(10,2) NOT NULL COMMENT '总价',\n" +
                "  `create_time` datetime NOT NULL COMMENT '创建时间',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流水记录';";
    }

    @Override
    public String getTableName() {
        return "flow_record";
    }

    @Override
    public Class<?> getEntityClass() {
        return FlowRecord.class;
    }
}
