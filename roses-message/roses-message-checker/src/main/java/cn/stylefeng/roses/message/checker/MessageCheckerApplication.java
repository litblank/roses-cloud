package cn.stylefeng.roses.message.checker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 消息恢复子系统,消息状态确认子系统
 *
 * @author stylefeng
 * @Date 2018/1/22 21:27
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "cn.stylefeng.roses.message.checker.consumer")
@EnableDiscoveryClient
@EnableScheduling
public class MessageCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageCheckerApplication.class, args);
    }

}
