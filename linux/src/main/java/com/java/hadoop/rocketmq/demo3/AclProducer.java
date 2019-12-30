package com.java.hadoop.rocketmq.demo3;//package com.example.thymeleafdemo.config;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AclProducer {
    @Autowired
    RocketMqCfgProperties rocketMqCfgProperties;
    @PostConstruct
    public void produder() throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(rocketMqCfgProperties.getProducerGroup(), getAclRPCHook());
        producer.setNamesrvAddr(rocketMqCfgProperties.getNamesrvaddr());
        producer.start();
        for (int i = 0; i < 10; i++) {
            try {
//                Message msg = new Message(rocketMqCfgProperties.getTopic() ,"TagA" , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                Message msg = new Message(rocketMqCfgProperties.getTopic() ,"" , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg);
                System.out.printf("====ACL  发送  %s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        producer.shutdown();
    }

     RPCHook getAclRPCHook() {
        return new AclClientRPCHook(new SessionCredentials(rocketMqCfgProperties.getAccessKey(),rocketMqCfgProperties.getSecretKey()));
    }
}