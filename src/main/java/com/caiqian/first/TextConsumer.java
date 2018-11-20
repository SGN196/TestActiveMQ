package com.caiqian.first;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TextConsumer {
    public String receiveTextMessage(){
        String resultCode ="";
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer messageConsumer = null;
        Message message = null;

        try {
            factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.1.105:61616");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("test-listener");

            messageConsumer = session.createConsumer(destination);
            message = messageConsumer.receive();
            resultCode = ((TextMessage)message).getText();

        } catch (JMSException e) {
            e.printStackTrace();
        }finally{
            if(messageConsumer != null){
                try {
                    messageConsumer.close();
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

        return resultCode;
    }

    public static void main(String[] args) {
        TextConsumer textConsumer = new TextConsumer();
        String messageString = textConsumer.receiveTextMessage();
        System.out.println("消息的内容是:" + messageString);
    }
}
