package com.caiqian.listener;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerListener {
    public void consumMessage(){
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;

        MessageConsumer consumer = null;

        try{
            factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.1.105:61616");
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("test-listener");

            consumer = session.createConsumer(destination);

            consumer.setMessageListener(new MessageListener(){      //队列中的消息变化会自动触发监听器代码，接受消息并处理

                @Override
                public void onMessage(Message message) {
                    try{
                        message.acknowledge();
                        ObjectMessage om = (ObjectMessage)message;
                        Object data = om.getObject();
                        System.out.println(data);
                    }catch(JMSException e){
                        e.printStackTrace();
                    }
                }
            });
            //阻塞当前代码。保证llistener代码未结束
            System.in.read();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(consumer != null){
                try {
                    consumer.close();
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
        ConsumerListener listener = new ConsumerListener();
        listener.consumMessage();
    }
}
