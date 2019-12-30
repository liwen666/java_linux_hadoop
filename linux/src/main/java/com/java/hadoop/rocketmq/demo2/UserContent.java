package com.java.hadoop.rocketmq.demo2;
 
import lombok.*;
import lombok.experimental.Accessors;
 
/**
* @Author 18011618
* @Date 19:28 2018/7/17
* @Function 发送消息体
*/
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@Getter
@Setter
public class UserContent {
    private String username;
    private String pwd;
 
}