package com.java.hadoop.kafa.JRX;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.Random;

public class KafkaProducerDemo {
    private  final Producer<String,String> kafkaProducer;

//    public final static String TOPIC="test_kafka_partition";
    public final static String TOPIC="test_kafka_partion";

    private KafkaProducerDemo(){
        kafkaProducer=createKafkaProducer() ;
    }
    private Producer<String,String> createKafkaProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.102.22:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);
        return kafkaProducer;
    }

    void produce(){
        Random random = new Random(1);
        for(int i=1;i<100;i++){
            try {
                int ran1 = random.nextInt(5);
                String data="hello kafka message:"+ran1;
                kafkaProducer.send(new ProducerRecord<String, String>(TOPIC, ran1+"", data), new Callback() {
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        System.out.println("消息发送成功");
                        System.out.println(recordMetadata);

                    }
                });
                System.out.println(data);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public  static  void main(String[] args){
        new KafkaProducerDemo().produce();
    }
}