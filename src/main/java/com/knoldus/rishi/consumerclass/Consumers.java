package com.knoldus.rishi.consumerclass;

import com.knoldus.rishi.inputmodel.InputUser;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumers {

    public static void main(String[] args) {
        ConsumerListen c = new ConsumerListen();
        Thread thread = new Thread(c);
        thread.start();
    }
    public static void consumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "com.knoldus.rishi.deserializerpkg.DeserializerUser");
        properties.put("group.id", "test-group");
        KafkaConsumer<String, InputUser> kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("user");
        kafkaConsumer.subscribe(topics);
        try{
            // Message1
            while (true){
                ConsumerRecords<String, InputUser> records = kafkaConsumer.poll(10000);
                for (ConsumerRecord<String, InputUser> record: records){
                    System.out.println(record.value());

                    BufferedWriter buffer = new BufferedWriter(new FileWriter("Usermessage.txt", true));
                    buffer.write(record.value()+"\n"); // this code will write the user records in Usermessage.txt file.
                    buffer.close();
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            kafkaConsumer.close();
        }
    }
}

class ConsumerListen implements Runnable {


    @Override
    public void run() {
        Consumers.consumer();
    }
}