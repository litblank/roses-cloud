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
package cn.stylefeng.roses.message.core.db;

import cn.stylefeng.roses.core.db.DbInitializer;
import cn.stylefeng.roses.message.api.model.ReliableMessage;
import org.springframework.stereotype.Component;

/**
 * 用户表的初始化
 *
 * @author fengshuonan
 * @date 2018-07-30-上午9:29
 */
@Component
public class ReliableInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `reliable_message` (\n" +
                "  `id` bigint(20) NOT NULL COMMENT '主键ID',\n" +
                "  `message_id` varchar(50) NOT NULL DEFAULT '' COMMENT '消息ID',\n" +
                "  `message_body` longtext NOT NULL COMMENT '消息内容',\n" +
                "  `message_data_type` varchar(50) DEFAULT NULL COMMENT '消息数据类型',\n" +
                "  `consumer_queue` varchar(100) NOT NULL DEFAULT '' COMMENT '消费队列',\n" +
                "  `message_send_times` smallint(6) NOT NULL DEFAULT '0' COMMENT '消息重发次数',\n" +
                "  `already_dead` char(1) NOT NULL DEFAULT '' COMMENT '是否死亡\\r\\n\\r\\nY：已死亡\\r\\nN：未死亡   \\r\\n',\n" +
                "  `status` varchar(20) NOT NULL DEFAULT '' COMMENT '状态 \\r\\n\\r\\nWAIT_VERIFY：待确认  \\r\\nSENDING：发送中',\n" +
                "  `create_time` datetime NOT NULL COMMENT '创建时间',\n" +
                "  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',\n" +
                "  `create_by` varchar(100) DEFAULT NULL COMMENT '创建者',\n" +
                "  `update_by` varchar(100) DEFAULT NULL COMMENT '修改者',\n" +
                "  `remark` varchar(200) DEFAULT NULL COMMENT '备注',\n" +
                "  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',\n" +
                "  `biz_unique_id` bigint(20) DEFAULT NULL COMMENT '业务系统唯一id',\n" +
                "  PRIMARY KEY (`id`) USING BTREE,\n" +
                "  KEY `AK_Key_2` (`message_id`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='可靠消息表';";
    }

    @Override
    public String getTableName() {
        return "reliable_message";
    }

    @Override
    public Class<?> getEntityClass() {
        return ReliableMessage.class;
    }
}
