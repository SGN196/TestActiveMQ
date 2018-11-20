package com.caiqian.listener;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Random;

public class ObjectProducer {
    public void sendMessage(Object obj){
        ConnectionFactory factory = null;
        Connection connection = null;
        Destination destination = null; //目的地
        Session session = null;
        MessageProducer producer = null;    //消息发送者
        Message message = null;         //消息对象
        try {
            factory = new ActiveMQConnectionFactory("guest", "guest", "tcp://192.168.1.105:61616");
            connection = factory.createConnection();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("test-listener");
            producer = session.createProducer(destination);
            connection.start();
            for (int i = 0; i < 9999999; i++) {
                Integer data =(int)(Math.random() * 100);
                message = session.createObjectMessage(i);
                producer.send(message);

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally{
            if(producer !=null){
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(session != null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        ObjectProducer producer = new ObjectProducer();
        producer.sendMessage(null);
    }
}
