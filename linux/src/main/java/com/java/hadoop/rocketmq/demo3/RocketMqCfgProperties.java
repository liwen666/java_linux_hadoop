package com.java.hadoop.rocketmq.demo3;//package com.example.thymeleafdemo.config;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author lw
 * @since 2019/5/26 23:40
 */
@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@ConfigurationProperties("suning.rocketmq")
//suning:
//        rocketmq:
//        producer-group: anybatch_pro_group_job_server
//        # 消费者的组名
//        conumer-group: anybatch_customer_group_exec_node
//        # NameServer地址
//        namesrvaddr: 10.0.8.16:9876
//        accessKey: BatchTaskMQ
//        secretKey:  batch123456
//        topic: ANY_BATCH_JOB_RESULT_STATUE_TOPIC

public class RocketMqCfgProperties {
    //    生成组
    private String producerGroup;
    //消费组
    private String conumerGroup;
    //地址
    private String namesrvaddr;

    private String accessKey;
    private String secretKey;
    private String topic;


}
