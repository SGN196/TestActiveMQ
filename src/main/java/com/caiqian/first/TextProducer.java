package com.caiqian.first;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TextProducer {
    public void sendTextMessage(String datas){
        ConnectionFactory factory = null;
        Connection connection = null;
        Destination destination = null; //目的地
        Session session = null;
        MessageProducer producer = null;    //消息发送者
        Message message = null;         //消息对象


        try {
            factory = new ActiveMQConnectionFactory("guest", "guest", "tcp://192.168.1.105:61616");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("test-listener");
            producer = session.createProducer(destination);
            message = session.createTextMessage(datas);
            producer.send(message);
            System.out.println("消息已发送");


        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(producer != null){
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
        TextProducer producer = new TextProducer();
        producer.sendTextMessage("测试ActiveMQ");
    }
}
